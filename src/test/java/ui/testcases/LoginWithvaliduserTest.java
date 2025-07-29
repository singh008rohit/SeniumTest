package ui.testcases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.BaseTest;
import commonConstant.CommonConstant;
import enums.AuthorType;
import enums.CategoryType;
import listeners.SeleniumListener;
import pages.HomePage;
import pages.NewUserSignUpPage;
import reportManager.ExtentManager;
import test.data.MapTestData;
@Listeners({SeleniumListener.class})
public class LoginWithvaliduserTest extends BaseTest{
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
			category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	@Test(enabled =true,groups = {"SANITY","SMOKE","REGRESSION"})
	
	void loginWithvalidUserTest() {
		
		 ExtentManager.getExtentTest().log(Status.INFO, "--- Stating test login with valid user ---");
		 final  String email = MapTestData.setUserData().get(CommonConstant.USER_EMAIL);
		   HomePage homePage= new HomePage(getDriver());
		   NewUserSignUpPage newuserSingUpPage= new NewUserSignUpPage(getDriver());

		homePage.validatenavigatedToHomePage();
	      homePage.validatenavigatedToHomePage();
	      ExtentManager.getExtentTest().log(Status.INFO, "Validated navigated to home page");
	      homePage.clickOnSignUpButton();
	      homePage.validateNavigatedToSignupPage();
	      ExtentManager.getExtentTest().log(Status.INFO, "Validated navigated to home page");
	      newuserSingUpPage.signupWithNameEmail(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
	      newuserSingUpPage.verifyNameEmailVisible(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
	   //   newuserSingUpPage.enteruserDetials(MapTestData.setUserData());
	   //   newuserSingUpPage.validateAccountCreated();
	     
	    //  homePage.loginWithUser(MapTestData.setUserData().get(CommonConstant.USER_NAME));
	    ///  newuserSingUpPage.logout();
		//homePage.clickOnSignUpButton();
	//	newuserSingUpPage.loginwithvalidUser(email, MapTestData.setUserData().get(CommonConstant.USER_PASSWORD));
		// homePage.loginWithUser(MapTestData.setUserData().get(CommonConstant.USER_NAME));
		//  homePage.deleteAccount();
	   //   homePage.verifyAccountdeleted();
	      ExtentManager.getExtentTest().log(Status.INFO, "--- Completed test login with valid user ---");
	}
	
	//testac@gmail.com

}
