import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FoxAccountCreation {

	public static WebDriver driver ;
	public static void main(String[] args) {

		System.out.println("Enter the path of Gecko Driver (Sample path : /Users/sruthi/Downloads/geckodriver) : ");
		Scanner sc = new Scanner(System.in);
		String path = sc.next();
		
		//Setting Gecko driver
		System.setProperty("webdriver.gecko.driver", path);
		driver = new FirefoxDriver();
		
		//Navigating to fox.com
		 driver.get("http://www.fox.com");

		 //Clicking on Account Creation Icon
	     driver.findElement(By.xpath("/html/body/div[1]/div/header/div/div/div[2]/div/div[3]/div[2]/div/a")).click();
		 
	    //Clicking on Sign Up button
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[4]/button[1]")).click();
	        
	     //Creating Profile with Email Address 
	     
	     System.out.println("Starting Account creation with Email");
	     
	     //Enter First Name
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[4]/div[1]/input")).sendKeys("ItsSruthi");
	     
	     //Enter Last Name
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[4]/div[2]/input")).sendKeys("b");
	     
	     //Enter Email Address
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[5]/input")).sendKeys("shruthibengineer@gmail.com");
	     
	     //Enter Password
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[7]/div/input")).sendKeys("Sruthi12");

	     //Enter Birthdate
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[9]/div[2]/input")).sendKeys("01/01/1998");
	     
	     //Click on Gender drop down
	     driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[9]/div[1]/div/div[1]/div")).click();

	     //Read the drop down values for Gender 
	     WebElement genderElement = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[9]/div[1]/div/div[2]"));

	     
	     List<WebElement> allAnchors = genderElement.findElements(By.tagName("a"));

		for(WebElement anchor : allAnchors) {
		
			//Selecting Female from Drop down options
		    if(anchor.getText().equals("Female")){
	
		    	anchor.click();

		    	//Clicking on Create Profile Button
		    	driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[11]/button")).click();
		    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    	verify();
		    	createProfileWithFB();
		    	return;
		    }
		

		}
	    
		
	}
	
//Create Profile with FaceBook	
public static void createProfileWithFB()
{
	System.out.println("Starting Account creation with FaceBook");
	driver = new FirefoxDriver();
	driver.get("https://www.fox.com");
	driver.findElement(By.xpath("/html/body/div[1]/div/header/div/div/div[2]/div/div[3]/div[2]/div/a")).click();
	driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[4]/button[1]")).click();

	// Getting Window Handle
	String parentWindowurl = driver.getWindowHandle();
	//Clicking on Sign up with FaceBook
	driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/button")).click();
	Set<String> allUrls = driver.getWindowHandles();
	for(String newUrl : allUrls)
	{
		if(!newUrl.equals(parentWindowurl))
		{
			//Switching to facebook sign in popup
			driver.switchTo().window(newUrl);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			try{
		
			//Setting facebook user name	
			driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("testassignmentsruthi@gmail.com");
			
			//Setting facebook user password
			driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("Selenium");
			
			// Click on Login
			driver.findElement(By.xpath("//*[@id=\"u_0_0\"]")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			
			//Switching back to Parent Window
			driver.switchTo().window(parentWindowurl);
			
			//Entering the password again
			driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div/div[2]/div[6]/input")).sendKeys("Selenium");
			
			//Clicking on Create Profile
			driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div/div[2]/div[9]/button")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			WebElement successMsg = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div/div[2]/div[2]"));
			
			// Sign Up Successful
			if(successMsg.getText().equals("Thanks for Signing Up!"))
				System.out.println("Sign up Success");
			else
				System.out.println("Sign up failed. Please check if user is already existing"); //Check if its existing user
			}
			catch(Exception e)
			{
				System.out.println("Sign up failed. Please check if user is already existing"); //Check if its existing user
			}
			

		}
	}
}
	

public static void verify(){
	
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//Read the message after creating profile
	WebElement msg= driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[2]/div[2]"));
	String text=msg.getText();

	// Sign Up Successful
	if(text.equals("Thanks for Signing Up!"))
		System.out.println("sign up is Success");
	else if(text.equals("Email address is already registered. Try signing in instead.")) //Existing user
		System.out.println("Existing user. Please sign in ");
	else if(text.equals("Profile cannot be created at this time. Please try again later.")) //If Profile cannot be created.
		System.out.println("Profile cannot be created at this time. Please try again later");
	else
		System.out.println("sign up failed"); // Sign up failed

}

}
