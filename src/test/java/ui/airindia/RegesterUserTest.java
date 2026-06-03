package ui.airindia;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.BaseTest;
import enums.AuthorType;
import enums.CategoryType;
import pages.AirindiaFSRPage;
import reportManager.ExtentManager;

public class RegesterUserTest extends BaseTest{
	
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
			category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	@Test(enabled =true,groups = {"SANITY","SMOKE","REGRESSION"})
	
	void searchFlight() {
		 ExtentManager.getExtentTest().log(Status.INFO, "--- Stating test login with valid user ---");
		 AirindiaFSRPage fsrpage  = new AirindiaFSRPage(getDriver());
		 
		 fsrpage.validateTitle();
		 
		
		
		
	}
	

}
