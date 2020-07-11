package flights.pageClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class FlightFinderPage {
	private WebDriver driver;
	SelectFlightPage selectFlightObj;

	By signOff = By.xpath("//a[contains(text(),'SIGN-OFF')]");
	By rdoRoundTrip = By.xpath("//input[@name='tripType' and @value = 'roundtrip']");
	By rdoOneWay = By.xpath("//input[@name='tripType' and @value = 'oneway']");
	By drpAirline = By.xpath("//select[@name='airline']");
	By lnkContinue = By.xpath("//input[@name='findFlights' and @type='image']");

	public FlightFinderPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean verifyLogin() throws Exception {
		if (driver.findElement(signOff).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public SelectFlightPage fillFlightDetails() throws IOException {
		String tripType = null;
		String airlines = null;
		String excelFilePath = ".//src//test//resources//flight//data.xlsx";
		FileInputStream fileInputStream = new FileInputStream(excelFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {
			tripType = sheet.getRow(i).getCell(0).getStringCellValue();
			airlines = sheet.getRow(i).getCell(1).getStringCellValue();
		}

		if (tripType.equals("OneWay")) {
			driver.findElement(rdoOneWay).click();
		} else if (tripType.equals("RoundTrip")) {
			driver.findElement(rdoRoundTrip).click();
		}
		
		Select selectAirlines = new Select(driver.findElement(drpAirline));
		selectAirlines.selectByVisibleText(airlines);

		Row newRow = sheet.getRow(1);
		Cell cell = newRow.createCell(2);
		cell.setCellValue("Yes");
		FileOutputStream outputStream = new FileOutputStream(new File(excelFilePath));
		workbook.write(outputStream);
		
		driver.findElement(lnkContinue).click();
		selectFlightObj = new SelectFlightPage(driver);
		
		return selectFlightObj;
	}
}
