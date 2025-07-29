  package stepDefination;


import org.openqa.selenium.WebDriver;

import base.PageObjectManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.BasePage;

public class RegisterUser extends BasePage {
   public RegisterUser(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

@Given("Launch browser")
   public void launch_browser() {
   }

   @Given("navigate to url")
   public void navigate_to_url() {
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