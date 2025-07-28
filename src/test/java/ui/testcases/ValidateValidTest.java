package ui.testcases;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import annotation.FrameworkAnnotation;
import base.BaseTest;
import enums.AuthorType;
import enums.CategoryType;
import pages.HomePage;
import reportManager.ExtentManager;

public class ValidateValidTest extends BaseTest {
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
			category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	
	@Test(enabled =true, description= "Validate able to login with valid user only",groups = {"SANITY","SMOKE","REGRESSION"})
	
	void validateValidUserTest() {
		//LoginPage login= new LoginPage(getDriver());
		//login.load1();
		HomePage homePage= new HomePage(getDriver());
		 ExtentManager.getExtentTest().log(Status.INFO, "--- Stating test login with valid user ---");
		homePage.validatenavigatedToHomePage();
		 ExtentManager.getExtentTest().log(Status.INFO, "Test execution completed");


}}
