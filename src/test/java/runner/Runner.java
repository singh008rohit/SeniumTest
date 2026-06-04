    package runner;




import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


//Runner.java — FIXED
@CucumberOptions(
features = {"src/main/java/feature"},
glue = {"stepDefination", "hooks"},  // remove "listeners" — StepListener is auto-loaded
                                      // via META-INF/services, not glue
tags = "${cucumber.filter.tags:not @ignore}", // system property override — 
                                               // run all unless tag is specified
plugin = {
    "pretty",
    "html:target/cucumber-reports/cucumber.html",
    "json:target/cucumber-reports/cucumber.json",
    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
},
monochrome = true,
dryRun = false
)
public class Runner extends AbstractTestNGCucumberTests {

 @Override
 @DataProvider(parallel = false) // keep false — Cucumber manages its own threading
 public Object[][] scenarios() {
     return super.scenarios();
 }
}