package apitest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import dataProvider.TestDataProvider;
import enums.AuthorType;
import enums.CategoryType;
import reportManager.ExtentManager;

public class ValidateDataProviderTest extends APITestBase{
	
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE})

	
	@Test(alwaysRun=false ,dataProvider="testDataFromCSV",dataProviderClass=TestDataProvider.class,enabled =true,groups = {"SANITY","SMOKE","REGRESSION"})
	
	void loginToApplication(HashMap<String,String> data){
		ExtentManager.getExtentTest().log(Status.INFO, "Login with user name "+data.get("username"));
		ExtentManager.getExtentTest().log(Status.INFO, "Login with password "+data.get("password"));
		System.out.println(data.get("username"));
		System.out.println(data.get("password"));

		
		
	}
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.REGRESSION })

	
	@Test(enabled=false,dataProvider="testDataFromJson",dataProviderClass=TestDataProvider.class,groups = {"SANITY","SMOKE","REGRESSION"})
	
	void readDataFromJson(HashMap<String,String> mapData) {
		
		System.out.println(mapData.get("username"));
		System.out.println(mapData.get("password"));
		ExtentManager.getExtentTest().log(Status.INFO, "Login with user name "+mapData.get("username"));
		ExtentManager.getExtentTest().log(Status.INFO, "Login with password "+mapData.get("password"));

		
	}
		
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.REGRESSION })

	
	@Test(dataProvider="getDataForLocalization",dataProviderClass=TestDataProvider.class,groups = {"SANITY","SMOKE","REGRESSION"})
	
	void DataForLocalization(HashMap<String,String> mapData1) {
		
		 Map<String, String> validations =(Map<String, String>)mapData1;

		            
		
		System.out.println(mapData1.get("language"));
		System.out.println(validations);
		ExtentManager.getExtentTest().log(Status.INFO, "Login with user name "+mapData1.get("language"));
		ExtentManager.getExtentTest().log(Status.INFO, "Login with password "+validations.getClass());
		
		System.out.println("class name === "+validations.getClass());

		
	}
		
		
	

}
