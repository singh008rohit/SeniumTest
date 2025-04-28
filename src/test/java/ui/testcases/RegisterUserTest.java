    package ui.testcases;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import commonConstant.CommonConstant;
import listeners.SeleniumListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reportManager.ExtentReportManager;

@Listeners({SeleniumListener.class})
public class RegisterUserTest extends BaseTest {
   @Test(
      description = "Test Register a new user and at the end delete it"
   )
   public void loginToApplication() {
      this.homePage.validatenavigatedToHomePage();
      ExtentReportManager.getTest().log(Status.INFO, "Validated navigated to home page");
      this.homePage.clickOnSignUpButton();
      this.homePage.validateNavigatedToSignupPage();
      ExtentReportManager.getTest().log(Status.INFO, "Validated navigated to home page");
      this.newuserSingUpPage.signupWithNameEmail((String)this.mapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME), (String)this.mapTestData.setUserData().get(CommonConstant.USER_EMAIL));
      this.newuserSingUpPage.verifyNameEmailVisible((String)this.mapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME), (String)this.mapTestData.setUserData().get(CommonConstant.USER_EMAIL));
      this.newuserSingUpPage.enteruserDetials(this.mapTestData.setUserData());
      this.newuserSingUpPage.validateAccountCreated();
      this.homePage.loginWithUser((String)this.mapTestData.setUserData().get(CommonConstant.USER_EMAIL));
      this.homePage.deleteAccount();
      this.homePage.verifyAccountdeleted();
   }
}