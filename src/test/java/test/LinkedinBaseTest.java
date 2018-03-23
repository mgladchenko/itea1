package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import page.LinkedinLandingPage;

public class LinkedinBaseTest {
	WebDriver driver;
	LinkedinLandingPage landingPage;

	@Parameters({"browserType", "envURL"})
	@BeforeMethod
	public void beforeTest(@Optional("chrome") String browserType, @Optional("https://ua.linkedin.com/") String envURL) {

		switch (browserType.toLowerCase()){
		case "firefox" :
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "chrome" :
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		default :
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}

		driver.navigate().to(envURL);
		landingPage = new LinkedinLandingPage(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}
}
