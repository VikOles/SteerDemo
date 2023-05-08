package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavigationBar {
    @FindBy(xpath = "//a[descendant::div[text()='Appointments']]/..")
    private WebElement appointments;

    @FindBy(xpath = "//div[text()='Scheduler']")
    private WebElement scheduler;

    @FindBy(xpath = "//div[text()='Calendar']")
    private WebElement calendar;

    @FindBy(xpath = "//div[text()='Calendar']")
    private WebElement divCalendar;

    WebDriverWait wait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(10));

    public void openAppointments() {
        wait.until(ExpectedConditions.visibilityOf(appointments));
        appointments.click();
    }

    public void openScheduler() {
        wait.until(ExpectedConditions.visibilityOf(scheduler));
        scheduler.click();
    }

    public void openCalendar() {
        wait.until(ExpectedConditions.visibilityOf(calendar));
        calendar.click();
    }
}
