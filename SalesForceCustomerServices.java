package Week4.Day2.Assignments.Mandatory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

public class SalesForceCustomerServices {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup(); //verify the version, download,setup	
		//Handling the browser notification
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");			
		ChromeDriver driver=new ChromeDriver(options);		//launch the browser -chrome
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//Open browser and login
		driver.get("https://login.salesforce.com/");		 //load the url
		driver.manage().window().maximize(); //maximize the browser

		//Login to salesforce
		driver.findElement(By.id("username")).sendKeys("ramkumar.ramaiah@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Password$123");
		driver.findElement(By.id("Login")).click();

		//Click learn more in the Mobile Publisher page
		driver.findElement(By.xpath("//span[text()='Learn More']")).click();
		System.out.println("Clicked Learn More button in the Mobile Publisher section");

		//Switching to the 2nd window
		Set<String> allHandles = driver.getWindowHandles();		
		//Create a list to access by id, cant do this in set as it doesnt have get method
		List<String> lstWindowHandles = new ArrayList<String>(allHandles);
		String strSecondHandle=lstWindowHandles.get(1); //gets the 2nd handle		
		driver.switchTo().window(strSecondHandle);		//moving control to 2nd window
		System.out.println("Switching to the 2nd window");

		//Clicking the Confirm button in the 2nd window
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
		System.out.println("Clicked the confirm button");

		//Create object for shadow root
		Shadow dom=new Shadow(driver);
		Thread.sleep(10000);
		
		//Choose the main menu 'Product'
		dom.findElementByXPath("//span[text()='Products']").click();
		System.out.println("Clicked the Prodcuts Menu");
		
		//Choose the submenu 'Service'
		WebElement service= dom.findElementByXPath("//span[text()='Service']");
		Actions builder = new Actions(driver);
		builder.moveToElement(service).perform() ; //mouse hover on the menu item 'Service
		System.out.println("Mouse hover on the submenu 'Service'");
		
		//choose submenu Customer service
		WebElement custServ=dom.findElementByXPath("//a[text()='Customer Service']");  //Click on Customer Service
		custServ.click();//click customer service certification link
		System.out.println("Choosing the submenu 'Customer Service'");
		wait.until(ExpectedConditions.titleContains("Service Tools"));
		
		//Verify the browser title
		if(driver.getTitle().equals("Customer Service Tools from Service Cloud - Salesforce.com")) {
			System.out.println("Passed!The title of the Customer Service page is correct");
		}
		else {
			System.out.println("Passed!The title of the Customer Service page is not correct");
		}
		
		//print List of customer services
		List<WebElement> lstServices = driver.findElements(By.xpath("(//div[contains(@class,'body1  no-indentation')])/../preceding-sibling::div"));
		System.out.println("---------The list of customer services are:---------- ");
		for(int i=0;i<=lstServices.size()-1;i++) {
			System.out.println(i+1+"."+lstServices.get(i).getText());	
		}

	}

}
