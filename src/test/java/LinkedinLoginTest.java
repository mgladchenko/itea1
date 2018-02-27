import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LinkedinLoginTest {
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
	public void successfulLoginTest() {
		LinkedinLoginPage loginPage = new LinkedinLoginPage(driver);

		String initialPageTitle = loginPage.getPageTitle();
		String initialPageUrl = loginPage.getPageUrl();

		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		LinkedinBasePage homePage = loginPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		Assert.assertTrue(homePage.isSignedIn(), "User is not signed in");

		Assert.assertNotEquals(homePage.getPageTitle(), initialPageTitle,
				"Page title did not change after login");
		Assert.assertNotEquals(homePage.getPageUrl(), initialPageUrl,
				"Page url did not change after login");
	}

	@Test
	public void negativeLoginTest() {
		//WebElement emailField = driver.findElement(By.xpath("//*[@id='login-email']"));
		WebElement emailField = driver.findElement(By.id("login-email"));
		WebElement passwordField = driver.findElement(By.id("login-password"));
		WebElement signInButton = driver.findElement(By.id("login-submit"));

		emailField.sendKeys("test@ukr.net");
		passwordField.sendKeys("12345");
		signInButton.click();

		WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']//strong[not(text()='')]"));
		Assert.assertTrue(alertMessage.isDisplayed(), "Alert message is not displayed.");


	}


}
