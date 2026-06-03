package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import pageConstant.AirIndiaFSRPageConstant;

public class AirindiaFSRPage extends BasePage {
	
	public SoftAssert softassert;
	
	@FindBy(xpath=AirIndiaFSRPageConstant.ORIGIN)
	WebElement origin;
	
	@FindBy(xpath=AirIndiaFSRPageConstant.DEPARTED)
	WebElement departed;
	
	public AirindiaFSRPage(WebDriver driver) {
		
		super(driver);
		
		
	}
	
	public void validateTitle() {
		
		
		
		softassert.assertEquals(driver.getTitle(), AirIndiaFSRPageConstant.HOMEPAGETITLE);
	}
	
	
	public void searchFlight() {
		
		
	}
	
	
	

}
