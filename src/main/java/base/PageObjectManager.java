package base;

import org.openqa.selenium.WebDriver;
import driver.DriverManager;
import pages.HomePage;
import pages.LoginPage;
import pages.NewUserSignUpPage;
import test.data.MapTestData;

// removed extends BasePage — PageObjectManager is a factory, not a page
public class PageObjectManager {

    private final WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private NewUserSignUpPage newUserSignUpPage;
    private MapTestData mapTestData;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
        // no super(driver) — no navigation, no PageFactory
    }

    public HomePage getHomePage() {
        if (this.homePage == null) {
            this.homePage = new HomePage(DriverManager.getDriver());
        }
        return this.homePage;
    }

    public LoginPage getLoginPage() {
        if (this.loginPage == null) {
            this.loginPage = new LoginPage(DriverManager.getDriver());
        }
        return this.loginPage;
    }

    public NewUserSignUpPage getNewUserSignUpPage() {
        if (this.newUserSignUpPage == null) {
            this.newUserSignUpPage = new NewUserSignUpPage(DriverManager.getDriver());
        }
        return this.newUserSignUpPage;
    }

    public MapTestData getMapTestData() {
        if (this.mapTestData == null) {
            this.mapTestData = new MapTestData();
        }
        return this.mapTestData;
    }
}