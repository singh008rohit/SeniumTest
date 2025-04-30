   package pageConstant;

public class HomePageConstant {
	
	 private HomePageConstant() {
	   }
   public static final String SIGNUP = "//a[normalize-space()='Signup / Login']";
   public static final String NEW_USER_SIGNUP = "//div[@class='signup-form']/h2[text()='New User Signup!']";
   public static final String LOGIN_USER = "//a/i[@class='fa fa-user']/following-sibling::b";
   public static final String DELETE_ACCOUNT = "//a[normalize-space()='Delete Account']";
   public static final String ACCOUNT_DELETE_MSG = "//b[text()='Account Deleted!']";
   public static final String ACCOUNT_DELETE_MSG_1 = "//p[text()='Your account has been permanently deleted!']";
   public static final String CONTINUE = "//a[text()='Continue']";

  
}