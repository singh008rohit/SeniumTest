    package ui.testcases;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import commonConstant.CommonConstant;
import listeners.SeleniumListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reportManager.ExtentReportManager;
import test.data.MapTestData;

@Listeners({SeleniumListener.class})
public class RegisterUserTest extends BaseTest {
   @Test(enabled = true,description = "Test Register a new user and at the end delete it")
      
   
   public void registeruserTest() {
	   final  String email = MapTestData.setUserData().get(CommonConstant.USER_EMAIL);
      homePage.validatenavigatedToHomePage();
      ExtentReportManager.getTest().log(Status.INFO, "Validated navigated to home page");
      homePage.clickOnSignUpButton();
      homePage.validateNavigatedToSignupPage();
      ExtentReportManager.getTest().log(Status.INFO, "Validated navigated to home page");
      newuserSingUpPage.signupWithNameEmail(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
      newuserSingUpPage.verifyNameEmailVisible(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
      newuserSingUpPage.enteruserDetials(MapTestData.setUserData());
      newuserSingUpPage.validateAccountCreated();
      homePage.loginWithUser(MapTestData.setUserData().get(CommonConstant.USER_NAME));
      homePage.deleteAccount();
      homePage.verifyAccountdeleted();
   }
}