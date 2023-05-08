
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.AppointmentsPage;
import pages.Browser;
import pages.LoginPage;
import org.openqa.selenium.support.PageFactory;
import pages.NavigationBar;
import utils.property.PropertyKeys;

import java.time.Duration;

import static utils.Constants.browserName;
import static utils.TestData.random10000Int;
import static utils.property.ReadPropertyFile.getIntValue;


public class AppointmentsTest {

    static LoginPage loginPage;

    @Parameters({"browser"})
    @BeforeTest
    public void before(@Optional("Chrome") String browser) throws Exception {
        browserName = browser;
        Browser.getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(getIntValue(PropertyKeys.IMPLICIT_WAIT.getValue())));
        Browser.getDriver().manage().window().maximize();
    }

    @AfterTest
    public void afterClass() {
        Browser.quit();
    }

    @Test
    public void CreateAppointment() throws Exception {
        String appointmentTitle = "appointment" + random10000Int();
        int durationDays = 4;

        loginPage = PageFactory.initElements(Browser.getDriver(), LoginPage.class);
        NavigationBar navigationBar = PageFactory.initElements(Browser.getDriver(), NavigationBar.class);
        AppointmentsPage appointmentsPage = PageFactory.initElements(Browser.getDriver(), AppointmentsPage.class);

        loginPage.invoke(Browser.getDriver());
        loginPage.setUserName();
        loginPage.setPassword();
        loginPage.clickLogInButton();
        navigationBar.openAppointments();
        navigationBar.openCalendar();
        appointmentsPage.openNewAppointment();
        appointmentsPage.createAppointment(appointmentTitle, durationDays);
        boolean isCreated = appointmentsPage.checkNewAppointmentWasCreated(appointmentTitle, durationDays);
        Assert.assertTrue(isCreated, "Check appointment creation");
        Reporter.log("Appointment " + appointmentTitle + " was added to calendar");
    }
}
