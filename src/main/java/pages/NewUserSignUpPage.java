    package pages;

import commonConstant.CommonConstant;
import driver.DriverManager;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utlity.SeleniumCommonUtils;

public class NewUserSignUpPage {
   private static String NewUserSignUp_Text = "New User Signup!";
   private static String success = "Account Created!";
   private static String success1 = "Congratulations! Your new account has been successfully created!";
   WebDriver driver = DriverManager.getDriver();
   @FindBy(
      xpath = "//div[@class='signup-form']/h2[text()='New User Signup!']"
   )
   WebElement newUserSignUpPage;
   @FindBy(
      xpath = "//input[@placeholder='Name']"
   )
   WebElement userName;
   @FindBy(
      xpath = "//div[@class='signup-form']//input[@placeholder='Email Address']"
   )
   WebElement email_adress;
   @FindBy(
      xpath = "//div[@class='signup-form']//button[@type='submit']"
   )
   WebElement signUpButton;
   @FindBy(
      css = "#uniform-id_gender1"
   )
   WebElement mrRadioButton;
   @FindBy(
      css = "#uniform-id_gender2"
   )
   WebElement mriRadioButton;
   @FindBy(
      xpath = "//input[@id='password']"
   )
   WebElement userPassword;
   @FindBy(
      css = "#days"
   )
   WebElement selectDate;
   @FindBy(
      css = "#months"
   )
   WebElement selectMonth;
   @FindBy(
      css = "#years"
   )
   WebElement selectYear;
   @FindBy(
      xpath = "//input[@id='first_name']"
   )
   WebElement firstName;
   @FindBy(
      xpath = "//input[@id='last_name']"
   )
   WebElement lastName;
   @FindBy(
      xpath = "//input[@id='company']"
   )
   WebElement userCompany;
   @FindBy(
      xpath = "//input[@id='address1']"
   )
   WebElement userAddress;
   @FindBy(
      xpath = "//select[@id='country']"
   )
   WebElement userCountry;
   @FindBy(
      xpath = "//input[@id='state']"
   )
   WebElement userState;
   @FindBy(
      xpath = "//input[@id='city']"
   )
   WebElement userCity;
   @FindBy(
      xpath = "//input[@id='zipcode']"
   )
   WebElement userZip;
   @FindBy(
      xpath = "//input[@id='mobile_number']"
   )
   WebElement userMobile;
   @FindBy(
      xpath = "//button[normalize-space()='Create Account']"
   )
   WebElement createAccount;
   @FindBy(
      css = "newsletter"
   )
   WebElement newsLetter;
   @FindBy(
      css = "#optin"
   )
   WebElement accountOffer;
   @FindBy(
      xpath = "//b[text()='Account Created!']"
   )
   WebElement accountCreatedText;
   @FindBy(
      xpath = "//p[text()='Congratulations! Your new account has been successfully created!']"
   )
   WebElement successMessage;

   public NewUserSignUpPage() {
      if (this.driver == null) {
         throw new IllegalStateException("driver is not initialized");
      } else {
         PageFactory.initElements(this.driver, this);
      }
   }

   public void signupWithNameEmail(String name, String email) {
      SeleniumCommonUtils.waitForVisibility(this.newUserSignUpPage, 10);
      Assert.assertEquals(SeleniumCommonUtils.getText(this.newUserSignUpPage), NewUserSignUp_Text, "Navigate to other page");
      SeleniumCommonUtils.typeText(this.userName, name);
      SeleniumCommonUtils.typeText(this.email_adress, email);
      SeleniumCommonUtils.clickElement(this.signUpButton);
   }

   public void verifyNameEmailVisible(String name, String email) {
      String usernamevalue = SeleniumCommonUtils.getAttributeValue(this.userName, "value");
      String emailFieldvalue = SeleniumCommonUtils.getAttributeValue(this.email_adress, "value");
      Assert.assertEquals(usernamevalue, name);
      Assert.assertEquals(emailFieldvalue, email);
   }

   public void enteruserDetials(HashMap<String, String> userdata) {
      SeleniumCommonUtils.clickElement(this.mrRadioButton);
      SeleniumCommonUtils.typeText(this.userPassword, (String)userdata.get(CommonConstant.USER_PASSWORD));
      this.selectDob((String)userdata.get("day"), (String)userdata.get("month"), (String)userdata.get("year"));
      SeleniumCommonUtils.clickElement(this.newsLetter);
      SeleniumCommonUtils.clickElement(this.accountOffer);
      SeleniumCommonUtils.typeText(this.firstName, (String)userdata.get(CommonConstant.USER_FIRST_NAME));
      SeleniumCommonUtils.typeText(this.lastName, (String)userdata.get(CommonConstant.USER_LAST_NAME));
      SeleniumCommonUtils.typeText(this.userCompany, (String)userdata.get(CommonConstant.USER_COMPANY));
      SeleniumCommonUtils.typeText(this.userAddress, (String)userdata.get(CommonConstant.USER_ADDRESS));
      SeleniumCommonUtils.selectDropdownByValue(this.userCountry, (String)userdata.get(CommonConstant.USER_COUNTRY));
      SeleniumCommonUtils.typeText(this.userState, (String)userdata.get(CommonConstant.USER_STATE));
      SeleniumCommonUtils.typeText(this.userCity, (String)userdata.get(CommonConstant.USER_CITY));
      SeleniumCommonUtils.typeText(this.userZip, (String)userdata.get(CommonConstant.USER_ZIP_CODE));
      SeleniumCommonUtils.typeText(this.userMobile, (String)userdata.get(CommonConstant.USER_MOBILE));
      SeleniumCommonUtils.clickElement(this.createAccount);
   }

   public void validateAccountCreated() {
      String successmsg = SeleniumCommonUtils.getText(this.accountCreatedText);
      String successmsg1 = SeleniumCommonUtils.getText(this.successMessage);
      Assert.assertEquals(successmsg, success);
      Assert.assertEquals(successmsg1, success1);
   }

   public void selectDob(String date, String month, String year) {
      SeleniumCommonUtils.selectDropdownByValue(this.selectDate, date);
      SeleniumCommonUtils.selectDropdownByValue(this.selectMonth, month);
      SeleniumCommonUtils.selectDropdownByValue(this.selectYear, year);
   }
}