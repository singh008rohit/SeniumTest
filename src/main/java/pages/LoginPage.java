package pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utlity.SeleniumCommonUtils;
import driver.DriverManager;
import pageConstant.LoginPageConstant;

public class LoginPage {
	
	private WebDriver driver;
	private static String message=  "Invalid username or password.";
	@FindBy(xpath=LoginPageConstant.LOGIN_LINK)
	WebElement loginLink;
	
	@FindBy(css=LoginPageConstant.USERNAME)
	WebElement username;
	
	@FindBy(css = LoginPageConstant.SIGNIN_BUTTON)
	WebElement signIN;
	
	
	@FindBy(css= LoginPageConstant.PASSWORD)
	WebElement passwordWebelement;
	
	@FindBy(xpath= LoginPageConstant.ERROR_MESSAGE)
	
WebElement errorMessage;
	
	public LoginPage() {
		
		this.driver=DriverManager.getDriver();
		PageFactory.initElements(driver, this);
		
	}
	
	public void loginToApplication(String userName , String password) {
		
		SeleniumCommonUtils.clickElement(loginLink);
		
	SeleniumCommonUtils.typeText(username, userName);
	SeleniumCommonUtils.typeText(passwordWebelement, password);
	SeleniumCommonUtils.clickElement(signIN);
	Assert.assertEquals(SeleniumCommonUtils.getText(errorMessage), message,"able to login");
	
		
	}
}
