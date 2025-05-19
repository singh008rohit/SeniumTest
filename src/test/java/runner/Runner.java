    package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
   features = {"src/main/java/feature"},
   glue = {"stepDefination", "hooks", "listeners"},
   tags = "@smoke",
   plugin = {"pretty", "html:target/cucumber-reports/cucumber.html", "json:target/cucumber-reports/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		  , "hooks.StepListener"},
   monochrome = true,
   dryRun = false
)
public class Runner extends AbstractTestNGCucumberTests {
}