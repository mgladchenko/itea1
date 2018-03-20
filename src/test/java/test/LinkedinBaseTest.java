package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import page.LinkedinLandingPage;

public class LinkedinBaseTest {
	WebDriver driver;
	LinkedinLandingPage landingPage;

	@Parameters({ "browserType" })
	@BeforeMethod
	public void beforeTest(@Optional("firefox") String browserType){
		if (browserType.toLowerCase().equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		if (browserType.toLowerCase().equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else {
			System.out.println("Unsupported browser");
		}

		driver.get("https://www.linkedin.com/");
		landingPage = new LinkedinLandingPage(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}
}
