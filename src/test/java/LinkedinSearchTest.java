import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LinkedinSearchTest {
	WebDriver driver;

	@BeforeMethod
	public void beforeTest(){
		driver = new FirefoxDriver();
		driver.get("https://www.linkedin.com/");
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}

	@Test
	public void basicSearchTest() {
		LinkedinLoginPage loginPage = new LinkedinLoginPage(driver);
		loginPage.loginAs("iteatest@i.ua", "1q2w3e_4r");



		//search
		//input[@placeholder='Search']
		//*[@type='search-icon']
		//div[contains(@class,'search-result--person')]



	}
}
