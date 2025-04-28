   package base;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import commonConstant.CommonConstant;
import io.restassured.RestAssured;
import reportManager.ExtentReportManager;

public class APITestBase {
   @BeforeClass(
      alwaysRun = true
   )
   public void setupAPI() throws IOException {
      ExtentReportManager.getSetup("API_TestSuite");
      RestAssured.baseURI = BaseTest.getValueFromPropFile(CommonConstant.BASE_URI);
   }

   @AfterSuite(
      alwaysRun = true
   )
   public void tearDownReport() {
      ExtentReportManager.flush();
   }
}