  package stepDefination;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RegisterUser extends BaseTest {
   @Given("Launch browser")
   public void launch_browser() {
   }

   @Given("navigate to url")
   public void navigate_to_url() {
   }

   @Then("Validate home page is visible")
   public void validate_home_page_is_visible() {
      this.pageManager.getHomePage().validatenavigatedToHomePage();
   }

   @Then("Click on signup login button")
   public void click_on_signup_login_button() {
      this.pageManager.getHomePage().clickOnSignUpButton();
   }

   @Then("Validate new user signup page is visible")
   public void validate_new_user_signup_page_is_visible() {
      this.pageManager.getHomePage().validateNavigatedToSignupPage();
   }
}