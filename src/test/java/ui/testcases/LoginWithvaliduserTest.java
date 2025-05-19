package ui.testcases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import commonConstant.CommonConstant;
import listeners.SeleniumListener;
import reportManager.ExtentReportManager;
import test.data.MapTestData;
@Listeners({SeleniumListener.class})
public class LoginWithvaliduserTest extends BaseTest{
	
	
	@Test(enabled =true)
	
	void loginWithvalidUserTest() {
		
		ExtentReportManager.getTest().log(Status.INFO, "--- Stating test login with valid user ---");
		 final  String email = MapTestData.setUserData().get(CommonConstant.USER_EMAIL);
		homePage.validatenavigatedToHomePage();
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
	      newuserSingUpPage.logout();
		homePage.clickOnSignUpButton();
		newuserSingUpPage.loginwithvalidUser(email, MapTestData.setUserData().get(CommonConstant.USER_PASSWORD));
		 homePage.loginWithUser(MapTestData.setUserData().get(CommonConstant.USER_NAME));
		  homePage.deleteAccount();
	      homePage.verifyAccountdeleted();
	  	ExtentReportManager.getTest().log(Status.INFO, "--- Completed test login with valid user ---");
	}
	
	//testac@gmail.com

}
