package mis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class validateBrokenLink {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ChromeOptions option= new ChromeOptions();
		
		
		WebDriver driver= new ChromeDriver(option);
		
		driver.manage().window().maximize();
		driver.get("https://www.flipkart.com/");
		
	List <WebElement> links=	driver.findElements(By.xpath("//a[@href]"));
	
	System.out.println(links.size());
	String url=null;
	
	for(WebElement link :links) {
		
	url=	link.getDomAttribute("href");
		//System.out.println(url);
		if(url.startsWith("http://")||url.startsWith("https://")) {
	getBrokenLink(url);
		}

	}
	
	driver.close();

}
	 static void getBrokenLink(String url) throws IOException {
		 URL link= new URL(url);
		 HttpURLConnection connect=	     (HttpURLConnection)link.openConnection();
		 
	int n=	 connect.getResponseCode();
	
	if(n>=400)
		System.out.println("Link is broken and getting status code "+n);
	else
		
		System.out.println("Link is not broken and getting status code "+n); 
	 }
	}
