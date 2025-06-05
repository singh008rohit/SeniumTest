package ui.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import commonConstant.CommonConstant;
import listeners.SeleniumListener;
import reportManager.ExtentReportManager;
import test.data.MapTestData;
@Listeners({SeleniumListener.class})

public class ValidateValidTest extends BaseTest {
	@Test(enabled =true, description= "Validate able to login with valid user only")
	
	void validateValidUserTest() {
		
		ExtentReportManager.getTest().log(Status.INFO, "--- Stating test login with valid user ---");
		 final  String email = MapTestData.setUserData().get(CommonConstant.USER_EMAIL);
		homePage.validatenavigatedToHomePage();
	      ExtentReportManager.getTest().log(Status.INFO, "Test execution completed");


}}
