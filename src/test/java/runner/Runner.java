    package runner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import commonConstant.CommonConstant;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import loggerUtil.LoggerUtils;


@CucumberOptions(
   features = {"src/main/java/feature"},
   glue = {"stepDefination", "hooks", "listeners"},
   tags = "@data1",
   plugin = {"pretty", "html:target/cucumber-reports/cucumber.html", "json:target/cucumber-reports/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		  , "hooks.StepListener"},
   monochrome = true,
   dryRun = false
)
public class Runner extends AbstractTestNGCucumberTests {
	
	@Override
	@DataProvider(parallel=true)
	public Object[][] scenarios(){
		return super.scenarios();
		
	}
	
	 @Parameters("browser")
	    @BeforeClass(alwaysRun = true)
	    public  void setBrowserParam(String xmlBrowser) {
	        if (xmlBrowser != null && !xmlBrowser.isEmpty()) {
	            System.setProperty("browser", xmlBrowser);
	    	    LoggerUtils.info("Browser resolved from testng.xml file: " + xmlBrowser);

	        } else {
	            System.out.println("Warning: Browser not set from testng.xml");
	        }
			CommonConstant.CUCUMBERBROWSERTYPE= xmlBrowser;
	    }
}