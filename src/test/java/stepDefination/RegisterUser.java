package stepDefination;

import base.PageObjectManager;
import driver.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

// removed extends BasePage — Cucumber manages step definition lifecycle
// extending BasePage was causing navigation on every scenario
public class RegisterUser {

    // use PageObjectManager to access pages
    private final PageObjectManager pageManager;

    public RegisterUser() {
        this.pageManager = new PageObjectManager(DriverManager.getDriver());
    }

    @Given("Launch browser")
    public void launch_browser() {
        // driver is already launched in Hooks.java @Before
    }

    @Given("navigate to url")
    public void navigate_to_url() {
        // navigation already happens in Hooks — nothing to do here
    }

    @Then("Validate home page is visible")
    public void validate_home_page_is_visible() {
        pageManager.getHomePage().validatenavigatedToHomePage();
    }

    @Then("Click on signup login button")
    public void click_on_signup_login_button() {
        pageManager.getHomePage().clickOnSignUpButton();
    }

    @Then("Validate new user signup page is visible")
    public void validate_new_user_signup_page_is_visible() {
        pageManager.getHomePage().validateNavigatedToSignupPage();
    }
}