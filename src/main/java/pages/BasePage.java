package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import base.PageObjectManager;
import commonConstant.ExtentReportConstant;
import reportManager.ExtentManager;
import utlity.ConfigLoader;

public class BasePage {
	
	protected WebDriver driver;
public	PageObjectManager pageManager;
	
	
public	BasePage(WebDriver driver){
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		//pageManager	= new PageObjectManager(driver);

		load("");
	}
	
	 public void load(String endPoint) {
			// driver.get("https://askomdch.com/" + endPoint);
			driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
			ExtentManager.getExtentTest().info(ExtentReportConstant.ICON_Navigate_Right + "  Navigating to : <b>" + ConfigLoader.getInstance().getBaseUrl()
					+ endPoint + "</b>");
		}

}
