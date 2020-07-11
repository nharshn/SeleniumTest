package flight.baseClass;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.DataProvider;

public class BaseClass {
	private WebDriver driver;
	private static Logger logger;
	
	public WebDriver getDriver(String browser, String URL) {
		switch (browser) {
		case "chrome": 
			System.setProperty("webdriver.chrome.driver", "F:\\Downloads\\Tools\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(URL);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "F:\\Downloads\\Tools\\IEDriverServer_x64_3.12.0\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.get(URL);
			break;
		default:
			break;
		}
		return driver;
	}
	
	public Logger getLogger(Class className) {
		logger = Logger.getLogger(className);
		PropertyConfigurator.configure(".\\src\\test\\resources\\flight\\log4j.properties");
		return logger;
	}
	
	@DataProvider
	public Object[][] dataSource(){
		Object[][] data = new Object[1][2];
		
		data[0][0] = "nharshn";
		data[0][1] = "Jul@2020";
		
		return data;
	}
	public String capture(WebDriver driver) {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File(".//ExtentReports//Screenshots//"+System.currentTimeMillis()+".png");
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		String destFilePath = destFile.getAbsolutePath();
		return destFilePath;
	}
	
}
