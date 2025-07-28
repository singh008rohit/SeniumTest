    package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import commonConstant.ExtentReportConstant;
import pageConstant.LoginPageConstant;
import reportManager.ExtentManager;
import utlity.ConfigLoader;
import utlity.SeleniumCommonUtils;

public class LoginPage extends BasePage {
   
   private static String message = "Invalid username or password.";
   @FindBy(xpath = LoginPageConstant.LOGIN_LINK)
   WebElement loginLink;

   @FindBy(css = LoginPageConstant.USERNAME)
   WebElement username;

   @FindBy(css = LoginPageConstant.PASSWORD)
   WebElement passwordWebelement;

   @FindBy(css = LoginPageConstant.SIGNIN_BUTTON)
   WebElement signIN;

   @FindBy(xpath = LoginPageConstant.ERROR_MESSAGE)
   WebElement errorMessage;

   public LoginPage(WebDriver driver) {
	
		super(driver);

   }
  
   
   public void loginToApplication(String userName, String password) {
      SeleniumCommonUtils.clickElement(loginLink);
      SeleniumCommonUtils.typeText(username, userName);
      SeleniumCommonUtils.typeText(passwordWebelement, password);
      SeleniumCommonUtils.clickElement(signIN);
      Assert.assertEquals(SeleniumCommonUtils.getText(errorMessage), message, "able to login");
   }
  
}