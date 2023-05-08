package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static utils.LocaleDateUtil.monthDayYearFormatter;
import static utils.LocaleDateUtil.yearMonthFormatter;

public class AppointmentsPage {
    @FindBy(xpath = "//button[descendant::span[text()='New Appointment']]")
    WebElement newAppointmentButton;

    @FindBy(xpath = "//span[text()='Create']")
    WebElement newAppCreateButton;

    @FindBy(id = "modal")
    WebElement createWindow;

    @FindBy(id = "create_anew_appointment")
    WebElement createWindowHeaderIcon;

    @FindBy(name = "title")
    WebElement createWindowTitleInput;

    @FindBy(xpath = "//div[@name='date']//input")
    WebElement createWindowDateInput;

    @FindBy(xpath = "//div[@class='undefined']//input[@class='']")
    List<WebElement> selectedTime;

    @FindBy(xpath = "//button[text()='Today']/following-sibling::button")
    WebElement nextCalendarButton;


    WebDriverWait wait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(10));
    WebDriverWait longWait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(60));

    public void openNewAppointment() {
        wait.until(ExpectedConditions.visibilityOf(newAppointmentButton));
        newAppointmentButton.click();
    }

    public void setAppTitle(String titleName) {
        wait.until(ExpectedConditions.visibilityOf(createWindow));
        createWindowTitleInput.sendKeys(titleName);
    }

    public void openCalendar() {
        createWindowDateInput.click();
    }

    public void setDateRange(int durationInDays) {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusDays(durationInDays);

        selectDate(startDateTime);
        selectDate(endDateTime);

        StringBuilder dateRange = new StringBuilder();
        dateRange
                .append(startDateTime.format(monthDayYearFormatter))
                .append(selectedTime.get(0).getAttribute("value"))
                .append(" - ")
                .append(endDateTime.format(monthDayYearFormatter))
                .append(selectedTime.get(1).getAttribute("value"));
        createWindowHeaderIcon.click();
        Browser.getDriver().findElement(By.xpath(String.format("//input[@value='%s']", dateRange))).isDisplayed();
        Reporter.log("Appointment duration is" + dateRange);
    }

    private void selectDate(LocalDateTime localDateTime) {
        String xpathDay = String.format("//div[@aria-label='month  %s']//div[text()='%d']",
                localDateTime.format(yearMonthFormatter),
                localDateTime.getDayOfMonth());
        longWait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        List<WebElement> date = Browser.getDriver().findElements(By.xpath(xpathDay));
        JavascriptExecutor executor = (JavascriptExecutor) Browser.getDriver();
        executor.executeScript("arguments[0].click();", date.get(0));

    }

    public void clickCreate() {
        wait.until(ExpectedConditions.elementToBeClickable(newAppCreateButton));
        newAppCreateButton.click();
    }

    public void createAppointment(String titleName, int durationInDays) {
        setAppTitle(titleName);
        openCalendar();
        setDateRange(durationInDays);
        clickCreate();
        Reporter.log("[INFO] Appointment " + titleName + " was successfully created");
    }

    public boolean checkNewAppointmentWasCreated(String titleOfAppointment, int durationInDays) {
        boolean isCreatedForFirstDay;
        boolean isCreatedForLastDay;

        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusDays(durationInDays);

        String appointmentXpath = String.format(".//span[text()='%s']/ancestor::div[@class='rbc-row-segment']", titleOfAppointment);
        By appointmentWeekContainer = By.xpath(String.format("//div[descendant::span[text()='%s'] and @class='rbc-row-content']", titleOfAppointment));

        List<WebElement> appointmentsWeeks = getListOfElement(appointmentWeekContainer); //TODO Add verification if appointment go to More section
        WebElement appointmentWebElement = appointmentsWeeks.get(0).findElement(By.xpath(appointmentXpath));
        WebElement dateWebElement = appointmentsWeeks.get(0)
                .findElement(By.xpath(String.format(".//div/button[text()='%d']", startDateTime.getDayOfMonth())));

        isCreatedForFirstDay = isElementsGetInByXCoordinates(appointmentWebElement, dateWebElement);
        if (!isCreatedForFirstDay) {
            Reporter.log("Appointment and Day  don't match ");
            return false;
        }
        if (startDateTime.getMonth().getValue() < endDateTime.getMonth().getValue()) {
            nextCalendarButton.click();
            appointmentsWeeks = getListOfElement(appointmentWeekContainer);
        }
        appointmentWebElement = appointmentsWeeks.get(appointmentsWeeks.size() - 1)
                .findElement(By.xpath(appointmentXpath));

        dateWebElement = appointmentsWeeks.get(appointmentsWeeks.size() - 1).
                findElement(By.xpath(String.format(".//div/button[text()='%d']", endDateTime.getDayOfMonth())));

        isCreatedForLastDay = isElementsGetInByXCoordinates(appointmentWebElement, dateWebElement);

        return isCreatedForLastDay;
    }

    private List<WebElement> getListOfElement(By locator) {
        return Browser.getDriver().findElements(locator);
    }

    private boolean isElementsGetInByXCoordinates(WebElement webElement, WebElement getInWebElement) {
        boolean isGetIn = false;
        int webElementX, webElementLastX;
        int getInWebElementX, getInWebElementLastX;
        webElementX = webElement.getLocation().getX();
        webElementLastX = webElementX + webElement.getSize().getWidth();
        getInWebElementX = getInWebElement.getLocation().getX();
        getInWebElementLastX = getInWebElementX + getInWebElement.getSize().getWidth();
        if (webElementX <= getInWebElementX && webElementLastX >= getInWebElementLastX) {
            isGetIn = true;
        }
        return isGetIn;
    }


}
