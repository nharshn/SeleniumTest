package flight.testClasses;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import flight.baseClass.BaseClass;
import flights.pageClasses.FlightFinderPage;
import flights.pageClasses.LoginPage;
import flights.pageClasses.SelectFlightPage;

public class BookFlightTest extends BaseClass {
	private WebDriver driver;
	LoginPage loginObj;
	FlightFinderPage flightFinderObj;
	SelectFlightPage selectFlightObj;
	Logger logger;
	ExtentReports reports;
	ExtentTest extentTest;

	@BeforeClass
	@Parameters({ "Browser", "URL" })
	public void setup(@Optional("chrome") String browser, String URL) {
		driver = getDriver(browser, URL);
		logger = getLogger(this.getClass());
		String extentReportsPath = ".//ExtentReports//ExtentReports.html";
		reports = new ExtentReports(extentReportsPath);
		logger.info("Application launched successfully");
	}

	@Test(dataProvider = "dataSource")
	public void flightBooking(String userName, String password) {
		loginObj = new LoginPage(driver);
		flightFinderObj = loginObj.login(userName, password);
		extentTest = reports.startTest("flightBooking");
		logger.info("Entered Username and Password");

		try {
			if (flightFinderObj.verifyLogin()) {
				logger.info("Login Successful");
				extentTest.log(LogStatus.PASS, extentTest.addScreenCapture(capture(driver)) + "Successful Login");
			}
		} catch (Exception e) {
			logger.info("Login failure");
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(capture(driver)) + "Login Failure");
		}

		try {
			selectFlightObj = flightFinderObj.fillFlightDetails();
			logger.info("Flight details filled successfully");
			extentTest.log(LogStatus.PASS,
					extentTest.addScreenCapture(capture(driver)) + "Flight details filled successfully");
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		reports.endTest(extentTest);
		reports.flush();
	}
}
