    package ui.testcases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.BaseTest;
import commonConstant.CommonConstant;
import enums.AuthorType;
import enums.CategoryType;
import pages.HomePage;
import reportManager.ExtentManager;
import test.data.MapTestData;


public class RegisterUserTest extends BaseTest {
	
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
			category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
   @Test(enabled = true,description = "Test Register a new user and at the end delete it",groups = {"SANITY","SMOKE","REGRESSION"})
      
   
   public void registeruserTest() {
	   final  String email = MapTestData.setUserData().get(CommonConstant.USER_EMAIL);
	   HomePage homePage= new HomePage(getDriver());
      homePage.validatenavigatedToHomePage();
      ExtentManager.getExtentTest().log(Status.INFO, "Validated navigated to home page");
    //  homePage.clickOnSignUpButton();
     // homePage.validateNavigatedToSignupPage();
      ExtentManager.getExtentTest().log(Status.INFO, "Validated navigated to home page");
    /*  newuserSingUpPage.signupWithNameEmail(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
      newuserSingUpPage.verifyNameEmailVisible(MapTestData.setUserData().get(CommonConstant.USER_FIRST_NAME),email);
      newuserSingUpPage.enteruserDetials(MapTestData.setUserData());
      newuserSingUpPage.validateAccountCreated();
      homePage.loginWithUser(MapTestData.setUserData().get(CommonConstant.USER_NAME));
      homePage.deleteAccount();
   */  // homePage.verifyAccountdeleted();
   ExtentManager.getExtentTest().log(Status.INFO, "Test execution completed");
   }
}