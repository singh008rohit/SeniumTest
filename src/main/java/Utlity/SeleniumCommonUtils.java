package utlity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.security.SecureRandom;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import commonConstant.CommonConstant;
import driver.DriverManager;
import loggerUtil.LoggerUtils;
import reportManager.ExtentReportManager;

public class SeleniumCommonUtils {
	  private static final SecureRandom random	 = new SecureRandom();
    private SeleniumCommonUtils() {
    	
    	
    }

    public static void clickElement(WebElement element) {
        try {
            element.click();
            LoggerUtils.info("Clicked on element: " + element);
            ExtentReportManager.getTest().log(Status.INFO, "Clicked on element: " + element);
        } catch (Exception e) {
            LoggerUtils.error("Failed to click element: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to click element: " + element);
            throw e;
        }
    }

    public static void typeText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            LoggerUtils.info("Entered text: " + text);
            ExtentReportManager.getTest().log(Status.INFO, "Entered text: " + text);
        } catch (Exception e) {
            LoggerUtils.error("Failed to type text: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to type text into element.");
            throw e;
        }
    }

    public static String getText(WebElement element) {
        try {
            String text = element.getText();
            LoggerUtils.info("Fetched text: " + text);
            ExtentReportManager.getTest().log(Status.INFO, "Fetched text: " + text);
            return text;
        } catch (Exception e) {
            LoggerUtils.error("Failed to get text: " + e.getMessage());
            throw e;
        }
    }

    public static boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void waitForVisibility(WebElement element, int timeoutSeconds) {
        try {
            WebDriver driver = DriverManager.getDriver();
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(driver1 -> element.isDisplayed());
        } catch (TimeoutException e) {
            LoggerUtils.error("Timeout waiting for element visibility: " + e.getMessage());
            throw e;
        }
    }

    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = "./screenshots/" + testName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);

        try {
            FileUtils.copyFile(srcFile, destFile);
            LoggerUtils.info("Screenshot saved at: " + screenshotPath);
            ExtentReportManager.getTest().log(Status.INFO, "Screenshot captured");
        } catch (IOException e) {
            LoggerUtils.error("Failed to save screenshot: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to save screenshot");
        }
        return screenshotPath;
    }
    public static void selectDropdownByVisibleText(WebElement element, String visibleText) {
        try {
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(visibleText);
            LoggerUtils.info("Selected value from dropdown by visible text: " + visibleText);
            ExtentReportManager.getTest().log(Status.INFO, "Selected value: " + visibleText);
        } catch (Exception e) {
            LoggerUtils.error("Failed to select from dropdown: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to select value from dropdown");
            throw e;
        }
    }

    public static void selectDropdownByValue(WebElement element, String value) {
        try {
            Select dropdown = new Select(element);
            dropdown.selectByValue(value);
            LoggerUtils.info("Selected value from dropdown by value: " + value);
            ExtentReportManager.getTest().log(Status.INFO, "Selected value by value: " + value);
        } catch (Exception e) {
            LoggerUtils.error("Failed to select from dropdown by value: " + e.getMessage());
            throw e;
        }
    }

    public static void selectDropdownByIndex(WebElement element, int index) {
        try {
            Select dropdown = new Select(element);
            dropdown.selectByIndex(index);
            LoggerUtils.info("Selected value from dropdown by index: " + index);
            ExtentReportManager.getTest().log(Status.INFO, "Selected value by index: " + index);
        } catch (Exception e) {
            LoggerUtils.error("Failed to select from dropdown by index: " + e.getMessage());
            throw e;
        }
    }
    // Returns true if the element exists, false otherwise
    public static boolean isElementExist(WebDriver driver, By locator) {
        List<?> elements = driver.findElements(locator);
        return !elements.isEmpty(); // true if found, false if not
    }

    // Returns true if the element does NOT exist, false otherwise
    public static boolean isElementNotExist(WebDriver driver, By locator) {
        List<?> elements = driver.findElements(locator);
        return elements.isEmpty(); // true if not found, false if exists
    }
    public static String getAttributeValue(WebElement element, String attributeName) {
        try {
            String attributeValue = element.getDomAttribute(attributeName);
            LoggerUtils.info("Fetched attribute [" + attributeName + "] with value: " + attributeValue);
            ExtentReportManager.getTest().log(Status.INFO, "Fetched attribute [" + attributeName + "]: " + attributeValue);
            return attributeValue;
        } catch (Exception e) {
            LoggerUtils.error("Failed to fetch attribute [" + attributeName + "]: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to fetch attribute [" + attributeName + "]");
            throw e;
        }
    }
    //  JavaScript Executor Methods Below 

    public static void clickUsingJS(WebElement element) {
        WebDriver driver = DriverManager.getDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            LoggerUtils.info("Clicked element using JavaScript.");
            ExtentReportManager.getTest().log(Status.INFO, "Clicked element using JavaScript.");
        } catch (Exception e) {
            LoggerUtils.error("Failed to click element using JavaScript: " + e.getMessage());
            throw e;
        }
    }

    public static void typeTextUsingJS(WebElement element, String text) {
        WebDriver driver = DriverManager.getDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + text + "';", element);
            LoggerUtils.info("Entered text using JavaScript: " + text);
            ExtentReportManager.getTest().log(Status.INFO, "Entered text using JavaScript.");
        } catch (Exception e) {
            LoggerUtils.error("Failed to enter text using JavaScript: " + e.getMessage());
            throw e;
        }
    }

    public static void scrollIntoView(WebElement element) {
        WebDriver driver = DriverManager.getDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            LoggerUtils.info("Scrolled element into view.");
            ExtentReportManager.getTest().log(Status.INFO, "Scrolled element into view.");
        } catch (Exception e) {
            LoggerUtils.error("Failed to scroll into view: " + e.getMessage());
            throw e;
        }
    }

    public static String getInnerTextUsingJS(WebElement element) {
        WebDriver driver = DriverManager.getDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String innerText = (String) js.executeScript("return arguments[0].innerText;", element);
            LoggerUtils.info("Fetched innerText using JavaScript: " + innerText);
            return innerText;
        } catch (Exception e) {
            LoggerUtils.error("Failed to fetch innerText using JavaScript: " + e.getMessage());
            throw e;
        }
    }
 
  

    public static String generateRandamString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CommonConstant.CHARACTERS_LOWERCASE.charAt(random.nextInt(CommonConstant.CHARACTERS_LOWERCASE.length())));
        }
        return sb.toString();
    }
}