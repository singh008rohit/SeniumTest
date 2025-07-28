package pages;

import com.aventstack.extentreports.Status;
import driver.DriverManager;
import pageConstant.HomePageConstant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.SeleniumCommonUtils;

public class HomePage extends BasePage {
	private static final String homePageTitle = "Automation Exercise";
	private static final String signupPageTitle = "Automation Exercise - Signup / Login";
	private static final String deletemsg1 = "ACCOUNT DELETED!";
	private static final String deletemsg2 = "Your account has been permanently deleted!";
	private SoftAssert soft;

	@FindBy(xpath = HomePageConstant.SIGNUP)
	WebElement signup;

	@FindBy(xpath = HomePageConstant.LOGIN_USER)
	WebElement loginUser;

	@FindBy(xpath = HomePageConstant.DELETE_ACCOUNT)
	WebElement deleteAccountLink;

	@FindBy(xpath = HomePageConstant.ACCOUNT_DELETE_MSG)
	WebElement deleteAccountMsg;

	@FindBy(xpath = HomePageConstant.ACCOUNT_DELETE_MSG_1)
	WebElement deleteAccountMsg1;

	@FindBy(xpath = HomePageConstant.CONTINUE)
	WebElement continueBtn;


	public HomePage(WebDriver driver) {
		
		super(driver);
			
			soft= new SoftAssert();
		
	}

	public void validatenavigatedToHomePage() {
		soft.assertEquals(driver.getTitle(), homePageTitle, "Home page title is incorrect");
	}

	public void clickOnSignUpButton() {
		SeleniumCommonUtils.waitForVisibility(signup, 10);
		SeleniumCommonUtils.clickElement(signup);
	}

	public void validateNavigatedToSignupPage() {
		soft.assertEquals(driver.getTitle(), signupPageTitle,"signup page title is incorrect");
				
	}

	public void loginWithUser(String name) {
		SeleniumCommonUtils.waitForVisibility(loginUser, 10);
		String userName = SeleniumCommonUtils.getText(loginUser);
		soft.assertEquals(userName, name);
		soft.assertTrue(
				SeleniumCommonUtils.isElementExist(driver, By.xpath(HomePageConstant.DELETE_ACCOUNT)));
		 ExtentManager.getExtentTest().log(Status.INFO, "Login with :" + userName);
	}

	public void deleteAccount() {
		soft.assertTrue(
				SeleniumCommonUtils.isElementExist(driver, By.xpath(HomePageConstant.DELETE_ACCOUNT)));
		SeleniumCommonUtils.clickElement(deleteAccountLink);
		String msg1 = SeleniumCommonUtils.getText(deleteAccountMsg);
		String msg2 = SeleniumCommonUtils.getText(deleteAccountMsg1);
		Assert.assertEquals(msg1, deletemsg1);
		Assert.assertEquals(msg2, deletemsg2);
		SeleniumCommonUtils.clickElement(continueBtn);
	}

	public void verifyAccountdeleted() {
		soft.assertTrue(SeleniumCommonUtils.isElementNotExist(driver,
				By.xpath(HomePageConstant.DELETE_ACCOUNT)));
		 ExtentManager.getExtentTest().log(Status.PASS, "Account is deleted");
		soft.assertAll();
	}
	
}