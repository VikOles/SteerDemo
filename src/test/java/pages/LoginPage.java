package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import utils.property.PropertyKeys;
import utils.property.ReadPropertyFile;

import java.time.Duration;

import static utils.property.ReadPropertyFile.getIntValue;
import static utils.property.ReadPropertyFile.getPropertyValue;

public class LoginPage {
    @FindBy(id = "username")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button/span[text()='Log In']")
    private WebElement logInButton;

    WebDriverWait wait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(getIntValue(PropertyKeys.EXPLICIT_WAIT.getValue())));

    public LoginPage() throws Exception {
    }

    public void invoke(WebDriver driver) throws Exception {
        driver.get(getPropertyValue(PropertyKeys.URL.getValue()));
    }

    public void setUserName() throws Exception {
        wait.until(ExpectedConditions.visibilityOf(userNameInput));
        userNameInput.sendKeys(getPropertyValue(PropertyKeys.EMAIL.getValue()));
    }

    public void setPassword() throws Exception {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(getPropertyValue(PropertyKeys.PASSWORD.getValue()));
    }

    public void clickLogInButton() {
        Reporter.log("Click login button");
        wait.until(ExpectedConditions.visibilityOf(logInButton));
        logInButton.click();
    }
}
