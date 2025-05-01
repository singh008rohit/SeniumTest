 package pageConstant;

public class NewUserSignUpPageConstant {
	
	
	private NewUserSignUpPageConstant() {
	   }
	
   public static final String NEW_USER_SIGNUP_TEXT = "//div[@class='signup-form']/h2[text()='New User Signup!']";
   
   public static final String USER_NAME = "//input[@id='name']";
   
   public static final String EMAIL_ADDRESS = "//input[@id='email']";
   
   public static final String SINGUP_BUTTON = "//div[@class='signup-form']//button[@type='submit']";
   
   public static final String MR_RADIO_BUTTON = "#uniform-id_gender1";
   
   public static final String MRS_RADIO_BUTTON = "#uniform-id_gender2";
   
   public static final String USER_PASSWORD = "//input[@id='password']";
   
   public static final String DATE = "#days";
   
   public static final String MONTH = "#months";
   
   public static final String YEARS = "#years";
   
   public static final String USER_FIRST_NAME = "//input[@id='first_name']";
   
   public static final String USER_LAST_NAME = "//input[@id='last_name']";
   
   public static final String USER_COMPANY = "//input[@id='company']";
   
   public static final String USER_ADDRESS = "//input[@id='address1']";
   
   public static final String USER_COUNTRY = "//select[@id='country']";
   
   public static final String USER_STATE = "//input[@id='state']";
   
   public static final String USER_CITY = "//input[@id='city']";
   
   public static final String USER_ZIP_CODE = "//input[@id='zipcode']";
   
   public static final String USER_MOBLIE_NUMBER = "//input[@id='mobile_number']";
   
   public static final String CREATE_ACOUNT_BUTTON = "//button[normalize-space()='Create Account']";
   
   public static final String NEWS_LETTER_CHECK_BOX = "newsletter";
   
   public static final String OFFER_CHECK_BOX = "#optin";
   
   public static final String ACCOUNT_CREATD_TEXT = "//b[text()='Account Created!']";
   
   public static final String ACCOUNT_CREATED_SUCCESS_MESSAGE = "//p[text()='Congratulations! Your new account has been successfully created!']";
   
   public static final String CONTINUE = "//a[text()='Continue']";
   
   public static final String LOGOUT_BUTTON = "//a[normalize-space()='Logout']";
   
   public static final String USER_EMAIL = "//form[@action='/login']/input[@name='email']";
   
   public static final String USER_PASSWORD_1 = "//form[@action='/login']/input[@name='password']";
   
   public static final String LOGIN_BUTTON = "//form[@action='/login']/button[text()='Login']";

   
}