package mis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidateBrokenLinkAmazon {
	
	
public static void getLink(String url) throws IOException {



     // Skip javascript links
     if (url.startsWith("javascript")) {
         return;
     }
	
	URL link= new URL(url);
	
	HttpURLConnection connection=  (HttpURLConnection)link.openConnection();
	
	
	connection.setRequestMethod("GET");
	int a=connection.getResponseCode();
	
	if(a>=400)
		System.out.println("Link is broken error code - "+a);
	else
		System.out.println("link is not broken");
	
		
		
	}


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ChromeOptions option= new ChromeOptions();
		
		WebDriver driver= new ChromeDriver(option);
		
		
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		
		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		
 List<WebElement> links=	driver.findElements(By.xpath("//a[@href]"));
 
	//wait.until(ExpectedConditions.visibilityOfAllElements(links));
	
	
		
		//System.out.println("======================="+links.size());
	
	for(WebElement el:links) {
		
	String url=	el.getDomProperty("href");
	
	getLink(url);
	
		
		
	}
	
	driver.quit();
	
	

	}

}
