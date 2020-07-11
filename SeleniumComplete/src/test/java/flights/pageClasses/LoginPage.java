package flights.pageClasses;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;
	FlightFinderPage flightFinderObj;
	
	By userNameObj = By.name("userName");
	By passwordObj = By.name("password");
	By submitObj = By.name("login");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public FlightFinderPage login(String userName,String password) {
		driver.findElement(userNameObj).sendKeys(userName);
		driver.findElement(passwordObj).sendKeys(password);
		driver.findElement(submitObj).click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		flightFinderObj = new FlightFinderPage(driver);
		
		return flightFinderObj;
	}
}
