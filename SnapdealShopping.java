package Week4.Day2.Assignments.Mandatory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SnapdealShopping {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup(); //verify the version, download,setup	
		//Handling the browser notification
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");			
		ChromeDriver driver=new ChromeDriver(options);		//launch the browser -chrome
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//Open browser and login
		driver.get("https://www.snapdeal.com/");		 //load the url
		driver.manage().window().maximize(); //maximize the browser

		//mouse over on brands
		WebElement menFash = driver.findElement(By.xpath("(//span[contains(text(),'Men')])[1]"));  //obj for the mens fashion
		Actions builder = new Actions(driver);
		//mouse hover on mens fashion
		builder.moveToElement(menFash).perform();
		driver.findElement(By.xpath("//span[text()='Sports Shoes']")).click();  //Click on sport shoes
		driver.findElement(By.xpath("//div[text()='Training Shoes']/..")).click(); //Click on training shoes	
		Thread.sleep(1000);
		driver.findElement(By.xpath("//i[contains(@class,'expand-arrow sort-arrow')]")).click() ; //Select the sort by dropdown arrow
		//Thread.sleep(1000);
		driver.findElement(By.xpath("//ul[@class='sort-value']/li[2]")).click() ; //Select the option-sort by low to high
		Thread.sleep(1000);

		//Check if the items are sorted correctly		
		List<WebElement> findElementsPrice = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		System.out.println("No. of shoes in the result: " +findElementsPrice.size());
		System.out.println("The prices of shoes displayed after sort: ");
		boolean boolFlag=true;
		String strText3="";
		for(int i=0;i<findElementsPrice.size();i++) {
			if(i+1<findElementsPrice.size()) {
				int Text=Integer.parseInt(findElementsPrice.get(i).getText().replace("," ,"").replace("Rs. ","").trim());
				int Text2=Integer.parseInt(findElementsPrice.get(i+1).getText().replace("," ,"").replace("Rs. ","").trim());
				strText3=strText3+" " +Text+" ";
				System.out.println(Text);
				System.out.println(Text2);
				
				if(Text<=Text2) {
					boolFlag=true;					
				}
				else {
					boolFlag=false;
					break;
				}
			}

		}
		if(boolFlag==true) {
			System.out.println("Passed!The shoes are sorted as per price-low to high");
			System.out.print(strText3);
		}
		else if(boolFlag==false){
			System.out.println("Failed!The shoes are not sorted as per price-low to high");
		}

		//Select the price range & color filter and verify if the filters are selected
		driver.findElement(By.xpath("//input[@name='fromVal']")).clear();
		driver.findElement(By.xpath("//input[@name='fromVal']")).sendKeys("600"); //enter the lower range value
		driver.findElement(By.xpath("//input[@name='toVal']")).clear();
		driver.findElement(By.xpath("//input[@name='toVal']")).sendKeys("3000"); //enter the upper range value
		driver.findElement(By.xpath("//div[contains(text(),'GO')]")).click(); //Click GO button
		Thread.sleep(5000);
		WebElement chkColor=driver.findElement(By.xpath("//input[@value='White']"));
		//wait.until(ExpectedConditions.visibilityOf(chkColor));
		driver.findElement(By.xpath("//input[@value='White']/following-sibling::label")).click();  //Click the White checkbox
		//price filter
		Thread.sleep(1000);
		WebElement ele = driver.findElement(By.xpath("(//a[@data-key='Price|Price'])[1]"));
		wait.until(ExpectedConditions.visibilityOf((ele)));
		if(driver.findElement(By.xpath("(//a[@data-key='Price|Price'])[1]")).getText().contains("600")&&driver.findElement(By.xpath("(//a[@data-key='Price|Price'])[1]")).getText().contains("2199")) {
			System.out.println("Passed!The price filter has been applied");
		}
		else {
			System.out.println("Failed!The price filter has not been applied");
		}
		if(driver.findElement(By.xpath("(//a[@data-key='Color_s|Color'])[1]")).getText().contains("White")) {
			System.out.println("Passed!The color filter has been applied");
		}
		//color filter
		else {
			System.out.println("Failed!The color filter has not been applied");
		}

		//mouse hover on the first shoe
		WebElement shoeImg= driver.findElement(By.xpath("(//img[@class='product-image wooble'])[1]"));
		builder.moveToElement(shoeImg).perform();
		//Click the quick view button of the 1st shoe in search results
		driver.findElement(By.xpath("(//div[@class='clearfix row-disc']//div)[1]")).click();

		//Print the price and the discount percentage
		System.out.println("-----------------------------------------------------------");
		String strShoeName=driver.findElement(By.xpath("//div[@class='quickViewHead']")).getText();
		System.out.println("The selected shoe is: "+strShoeName);
		String strPrice=driver.findElement(By.xpath("//span[@class='payBlkBig']")).getText();
		System.out.println("The price of the 1st shoe in the fitered search results is: "+strPrice);
		String strDiscount=driver.findElement(By.xpath("//span[@class='percent-desc hidden']")).getText();
		if(strDiscount.equals("")){
			System.out.println("The discount %applied on the shoe is: 0%");
		}
		else {
			System.out.println("The discount %applied on the shoe is: "+strDiscount);
		}

		//taking screenshot - LOCAL MEMORY FILE (of the selected shoe and price)
		File source=driver.getScreenshotAs(OutputType.FILE);//limitation:take snapshot of visible part of screen only
		File dest=new File("shoe.png"); //this is the path, saved under this project. rightclick-select-open with system editor
		FileUtils.copyFile(source, dest);	
		driver.close();
	}
}
