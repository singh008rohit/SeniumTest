package Utlity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import LoggerUtil.LoggerUtils;
import driver.DriverManager;
import reportManager.ExtentReportManager;

public class SeleniumCommonUtils {
	
	 static WebDriver driver = DriverManager.getDriver();
	
	
	public static void clickElement(WebElement element) {
		
		ExtentReportManager.getTest().log(Status.INFO, "Clicking on element: " + element);
		element.click();
		
	}
	
	public static void typeText(WebElement element,String text) {
		
		element.clear();
		element.sendKeys(text);
		ExtentReportManager.getTest().log(Status.INFO, "Clear editbox any type: " + text);
		
		
	}
	
	public static String getText(WebElement element) {
	
	return	element.getText();
	
		
	}
	 public static String captureScreenshot(String testName) {
	       
	        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        String screenshotPath = "./screenshots/" + testName + "_" + timestamp + ".png";

	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        File destFile = new File(screenshotPath);

	        try {
	            FileUtils.copyFile(srcFile, destFile);
	            System.out.println("Screenshot saved at: " + screenshotPath);
	        } catch (IOException e) {
	            System.out.println("Failed to save screenshot: " + e.getMessage());
	        }

	        return screenshotPath;
	    }

}
