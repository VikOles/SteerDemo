package pages;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.SafariDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;

import static utils.Constants.browserName;

public class Browser {
    private static WebDriver driver;

    private static void setDriver() {
        switch (browserName) {
            case "Chrome":
                ChromeDriverManager.getInstance().setup();
                Browser.driver = new ChromeDriver();
                Reporter.log("[INFO] Chrome driver created",1);
                break;
            case "Safari":
                SafariDriverManager.getInstance().setup();
                Browser.driver = new SafariDriver();
                Reporter.log("[INFO] Safari driver created",1);
                break;
            default:
                Reporter.log("[INFO] Unsupported browser",1);
        }

    }

    public static WebDriver getDriver() {
        if (Browser.driver == null) {
            setDriver();
        }
        return Browser.driver;
    }

    public static void quit() {
        Browser.driver.manage().deleteAllCookies();
        try {
            Browser.driver.quit();
        } catch (UnreachableBrowserException eQuit) {
            Thread.currentThread().interrupt();
            Browser.driver.quit();
        }
        Browser.driver = null;
    }
}
