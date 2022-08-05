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

public class SalesForceAdminCertification {

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
		
		//Clicking the Confirm button in the 2nd window
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
		System.out.println("Clicked the confirm button");
				
		//Create object for shadow root
		Shadow dom=new Shadow(driver);
		Thread.sleep(10000);
		//Choose the main menu 'Learning'
		dom.findElementByXPath("//span[text()='Learning']").click();	
		//Choose the submenu Learning on Trailhead
		WebElement linkTrail= dom.findElementByXPath("//span[text()='Learning on Trailhead']");
		Thread.sleep(4000);
		Actions builder = new Actions(driver);
		builder.moveToElement(linkTrail).perform() ; //mouse hover on the menu item 'Learn on trailhead'
		//Scroll to 'Salesforce certification link in the sub-menu
		WebElement cert=dom.findElementByXPath("//a[text()='Salesforce Certification']");
		builder.scrollToElement(cert).perform();
		cert.click();//click salesforce certification link
		
		//Administrator certification verification
		WebElement admin=driver.findElement(By.xpath("//a[text()='Administrator']"));
        wait.until(ExpectedConditions.visibilityOf(admin));
        admin.click(); //Click salesforce administrator link
		//verify certification administrator overview window
		wait.until(ExpectedConditions.urlContains("administrator"));
		wait.until(ExpectedConditions.titleContains("Certification"));
		System.out.println(driver.getTitle());
		if (driver.getTitle().contains("Certification - Administrator")) {
			System.out.println("Passed!Certification Administrator Overview Window is displayed");
		}
		else {
			System.out.println("Failed!Certification Administrator Overview Window is not displayed");
		}
		//verify administrator certs are displayed
		String strText=driver.findElement(By.xpath("//div[contains(@class,'studyPrepare')]")).getText();
		if(strText.contains("Study & Prepare")) {
			System.out.println("Passed!Certifications available for Administrator certifications are displayed");
		}
		else {
			System.out.println("Failed!Certifications available for Administrator certifications are not displayed");
		}    

	}

}
