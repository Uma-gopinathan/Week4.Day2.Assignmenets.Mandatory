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

public class NykaaShopping {

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
		driver.get("https://www.nykaa.com/");		 //load the url
		driver.manage().window().maximize(); //maximize the browser


		//mouse over on brands
		WebElement brand = driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brand).perform(); 
		//click on the 'L' link
		WebElement brandL = driver.findElement(By.xpath("//a[text()='L']"));
		wait.until(ExpectedConditions.visibilityOf(brandL));
		builder.moveToElement(brandL).perform();
		Thread.sleep(1000);
		//Click L'Oreal Paris
		WebElement loreal = driver.findElement(By.xpath("//div[@class='ss-content desktop-header']/div[@class='css-ov2o3v']/a[contains(text(),'real Paris')][1]"));
		wait.until(ExpectedConditions.elementToBeClickable(loreal));
		loreal.click();
		System.out.println("Navigated to Brands - LoreaL Paris page");

		//Click "Sort By" dropdown arrow
		driver.findElement(By.xpath("//span[@class='sort-name']/..")).click();   //Click sort by
		driver.findElement(By.xpath("//span[text()='customer top rated']/../following::div")).click(); //Click Customer top rated radio button

		driver.findElement(By.xpath("//span[text()='Category']/..")).click();   //Click Category
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Hair']/..")).click();    //Click Hair
		//WebElement hairObj=driver.findElement(By.xpath("//span[text()='Hair']/.."));
		//wait.until(ExpectedConditions.elementToBeClickable(hairObj));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Hair Care']/..")).click();  //Click hair care sub category		
		driver.findElement(By.xpath("//span[text()='Shampoo']/../following-sibling::div")).click();   //choose the shampoo check box option

		//Choose Shop by by concern
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Hair']/..")).click();    //Click Hair
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Shop By Concern']/..")).click();    //Click Shop by concern	
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Color Protection']/../following-sibling::div")).click();    //Click color protection
		//Verify if the filters are applied
		String filter1=driver.findElement(By.xpath("(//span[@class='filter-value'])[1]")).getText();
		String filter2=driver.findElement(By.xpath("(//span[@class='filter-value'])[2]")).getText();
		if(filter1.equals("Shampoo")) {
			System.out.println("Passed!The 'Shampoo' filter has been applied");
		}
		else {
			System.out.println("Failed!The 'Shampoo' filter has not been applied");
		}
		if(filter2.equals("Color Protection")) {
			System.out.println("Passed!The 'Color Protection' filter has been applied");
		}
		else {
			System.out.println("Failed!The 'Color Protection' filter has not been applied");
		}
		//Click on a shampoo from the search results		
		driver.findElement(By.xpath("//div[contains(text(),'Paris Dream Lengths Shampoo')]")).click(); 

		//Switching to newly opened window
		Set<String> allHandles = driver.getWindowHandles();		
		//Create a list to access by id, cant do this in set as it doesnt have get method
		List<String> lstWindowHandles = new ArrayList<String>(allHandles);
		String strSecondHandle=lstWindowHandles.get(1); //gets the 2nd handle		
		driver.switchTo().window(strSecondHandle);	

		//Select the bottle based on quantity
		Thread.sleep(1000);
		//WebElement mrp=frameObj.findElement(By.xpath("//span[text()='192.5ml']/.."));
		WebElement mrpBtn=driver.findElement(By.xpath("//span[text()='192.5ml']/.."));
		wait.until(ExpectedConditions.visibilityOf(mrpBtn));
		mrpBtn.click();

		//Print the MRP of the shampoo & grand total
		String strMRP=driver.findElement(By.xpath("//span[text()='MRP:']/following-sibling::span")).getText();
		System.out.println("The MRP of the shampoo is: "+strMRP);
		driver.findElement(By.xpath("(//span[text()='Add to Bag'])[1]")).click();  //Click Add to Bag button
		Thread.sleep(1000);
		
		//Going to cart and printing the cart total
		driver.findElement(By.xpath("//span[@class='cart-count']/..")).click();    //Click the shopping bag icon on the top right corner
		Thread.sleep(1000);
		//Switching to frame
		WebElement frameObj = driver.findElement(By.xpath("//iframe[contains(@src,'mobileCartIframe')]"));
		//System.out.println(frameObj.getText());
		driver.switchTo().frame(frameObj);
		String strTotal = driver.findElement(By.xpath("//span[text()='Grand Total']/following::div")).getText();
		System.out.println("The grand total is:  "+strTotal);

		//Click Proceed and close
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();  //Click Proceed
		System.out.println("Clicked Proceed in the cart pop-up");
		Thread.sleep(1000);
		driver.switchTo().defaultContent();  //switching to browser
		String strTotal2= driver.findElement(By.xpath("//i[@class='icon']/../span")).getText();
		if(Integer.parseInt(strTotal2.replace("₹", ""))==Integer.parseInt(strTotal.replace("₹", ""))) {
			System.out.println("Passed!The grand total in both pages are the same.");
		}
		else {
			System.out.println("Failed!The grand total in both pages are not the same.");
		}
		driver.quit();
	}

}
