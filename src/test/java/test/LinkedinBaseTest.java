package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import page.LinkedinLandingPage;

public abstract class LinkedinBaseTest {
	WebDriver driver;
	LinkedinLandingPage landingPage;

	@BeforeMethod
	public void beforeTest(){
		driver = new FirefoxDriver();
		driver.get("https://www.linkedin.com/");
		landingPage = new LinkedinLandingPage(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}
}
