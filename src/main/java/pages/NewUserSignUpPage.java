package pages;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import commonConstant.CommonConstant;
import driver.DriverManager;
import pageConstant.NewUserSignUpPageConstant;
import utlity.SeleniumCommonUtils;

public class NewUserSignUpPage {
	private static String NewUserSignUp_Text = "New User Signup!";
	private static String success = "ACCOUNT CREATED!";
	private static String success1 = "Congratulations! Your new account has been successfully created!";
	SoftAssert soft;
	WebDriver driver ;
	  @FindBy(xpath = NewUserSignUpPageConstant.NEW_USER_SIGNUP_TEXT)
	    private WebElement newUserSignUpHeading;

	    @FindAll({
	        @FindBy(xpath = NewUserSignUpPageConstant.USER_NAME),
	        @FindBy(xpath = "//input[@placeholder='Name']")
	    })
	    private WebElement userNameInput;

	    @FindAll({
	        @FindBy(xpath = NewUserSignUpPageConstant.EMAIL_ADDRESS),
	        @FindBy(xpath = "//input[@data-qa='signup-email']")
	    })
	    private WebElement emailAddressInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.SINGUP_BUTTON)
	    private WebElement signUpButton;

	    @FindBy(css = NewUserSignUpPageConstant.MR_RADIO_BUTTON)
	    private WebElement mrRadioButton;

	    @FindBy(css = NewUserSignUpPageConstant.MRS_RADIO_BUTTON)
	    private WebElement mrsRadioButton;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_PASSWORD)
	    private WebElement userPasswordInput;

	    @FindBy(css = NewUserSignUpPageConstant.DATE)
	    private WebElement dateDropdown;

	    @FindBy(css = NewUserSignUpPageConstant.MONTH)
	    private WebElement monthDropdown;

	    @FindBy(css = NewUserSignUpPageConstant.YEARS)
	    private WebElement yearDropdown;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_FIRST_NAME)
	    private WebElement firstNameInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_LAST_NAME)
	    private WebElement lastNameInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_COMPANY)
	    private WebElement companyInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_ADDRESS)
	    private WebElement addressInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_COUNTRY)
	    private WebElement countryDropdown;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_STATE)
	    private WebElement stateInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_CITY)
	    private WebElement cityInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_ZIP_CODE)
	    private WebElement zipCodeInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.USER_MOBLIE_NUMBER)
	    private WebElement mobileNumberInput;

	    @FindBy(xpath = NewUserSignUpPageConstant.CREATE_ACOUNT_BUTTON)
	    private WebElement createAccountButton;

	    @FindBy(css = "#" + NewUserSignUpPageConstant.NEWS_LETTER_CHECK_BOX)
	    private WebElement newsletterCheckbox;

	    @FindBy(css = NewUserSignUpPageConstant.OFFER_CHECK_BOX)
	    private WebElement offerCheckbox;

	    @FindBy(xpath = NewUserSignUpPageConstant.ACCOUNT_CREATD_TEXT)
	    private WebElement accountCreatedTitle;

	    @FindBy(xpath = NewUserSignUpPageConstant.ACCOUNT_CREATED_SUCCESS_MESSAGE)
	    private WebElement accountCreatedMessage;

	    @FindBy(xpath = NewUserSignUpPageConstant.CONTINUE)
	    private WebElement continueButton;

	public NewUserSignUpPage() {
		driver=DriverManager.getDriver();
		if (this.driver == null) {
			throw new IllegalStateException("driver is not initialized");
		} else {
			PageFactory.initElements(driver, this);
			soft =new SoftAssert();
		}
	}

	public void signupWithNameEmail(String name, String email) {
		SeleniumCommonUtils.waitForVisibility(newUserSignUpHeading, 10);
		soft.assertEquals(SeleniumCommonUtils.getText(newUserSignUpHeading), NewUserSignUp_Text,
				"Navigate to other page");
		SeleniumCommonUtils.typeText(userNameInput, name);
		SeleniumCommonUtils.typeText(emailAddressInput, email);
		SeleniumCommonUtils.clickElement(signUpButton);
	}

	public void verifyNameEmailVisible(String name, String email) {
		String usernamevalue = SeleniumCommonUtils.getAttributeValue(userNameInput, "value");
		String emailFieldvalue = SeleniumCommonUtils.getAttributeValue(emailAddressInput, "value");
		soft.assertEquals(usernamevalue, name);
		soft.assertEquals(emailFieldvalue, email);
	}

	public void enteruserDetials(HashMap<String, String> userdata) {
		SeleniumCommonUtils.clickElement(mrRadioButton);
		SeleniumCommonUtils.typeText(userPasswordInput, (String) userdata.get(CommonConstant.USER_PASSWORD));
		selectDob((String) userdata.get(CommonConstant.DAY), (String) userdata.get(CommonConstant.MONTH), (String) userdata.get(CommonConstant.YEAR));
		//SeleniumCommonUtils.clickElement(newsletterCheckbox);
		//SeleniumCommonUtils.clickElement(offerCheckbox);
		SeleniumCommonUtils.typeText(firstNameInput, (String) userdata.get(CommonConstant.USER_FIRST_NAME));
		SeleniumCommonUtils.typeText(lastNameInput, (String) userdata.get(CommonConstant.USER_LAST_NAME));
		SeleniumCommonUtils.typeText(companyInput, (String) userdata.get(CommonConstant.USER_COMPANY));
		SeleniumCommonUtils.typeText(addressInput, (String) userdata.get(CommonConstant.USER_ADDRESS));
		SeleniumCommonUtils.selectDropdownByValue(countryDropdown, (String) userdata.get(CommonConstant.USER_COUNTRY));
		SeleniumCommonUtils.typeText(stateInput, (String) userdata.get(CommonConstant.USER_STATE));
		SeleniumCommonUtils.typeText(cityInput, (String) userdata.get(CommonConstant.USER_CITY));
		SeleniumCommonUtils.typeText(zipCodeInput, (String) userdata.get(CommonConstant.USER_ZIP_CODE));
		SeleniumCommonUtils.typeText(mobileNumberInput, (String) userdata.get(CommonConstant.USER_MOBILE));
		SeleniumCommonUtils.waitForVisibility(createAccountButton, 5);
		SeleniumCommonUtils.clickUsingJS(createAccountButton);
	}

	public void validateAccountCreated() {
		String successmsg = SeleniumCommonUtils.getText(accountCreatedTitle);
		String successmsg1 = SeleniumCommonUtils.getText(accountCreatedMessage);
		soft.assertEquals(successmsg, success);
		soft.assertEquals(successmsg1, success1);
		SeleniumCommonUtils.clickUsingJS(continueButton);
		soft.assertAll();
	}

	public void selectDob(String date, String month, String year) {
		SeleniumCommonUtils.selectDropdownByValue(dateDropdown, date);
		SeleniumCommonUtils.selectDropdownByValue(monthDropdown, month);
		SeleniumCommonUtils.selectDropdownByValue(yearDropdown, year);
	}
}