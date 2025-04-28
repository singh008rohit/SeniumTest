   package base;

import pages.HomePage;
import pages.LoginPage;
import pages.NewUserSignUpPage;
import test.data.MapTestData;

public class PageObjectManager {
   private HomePage homePage;
   private LoginPage loginPage;
   private NewUserSignUpPage newUserSignUpPage;
   private MapTestData mapTestData;

   public HomePage getHomePage() {
      if (this.homePage == null) {
         this.homePage = new HomePage();
      }

      return this.homePage;
   }

   public LoginPage getLoginPage() {
      if (this.loginPage == null) {
         this.loginPage = new LoginPage();
      }

      return this.loginPage;
   }

   public NewUserSignUpPage getNewUserSignUpPage() {
      if (this.newUserSignUpPage == null) {
         this.newUserSignUpPage = new NewUserSignUpPage();
      }

      return this.newUserSignUpPage;
   }

   public MapTestData getMapTestData() {
      if (this.mapTestData == null) {
         this.mapTestData = new MapTestData();
      }

      return this.mapTestData;
   }
}
    