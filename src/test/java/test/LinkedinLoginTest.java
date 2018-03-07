package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LinkedinHomePage;
import page.LinkedinLandingPage;

public class LinkedinLoginTest {
	WebDriver driver;
	LinkedinLandingPage landingPage;
	String initialPageTitle;
	String initialPageUrl;


	@BeforeMethod
	public void beforeTest(){
		driver = new FirefoxDriver();
		driver.get("https://www.linkedin.com/");
		landingPage = new LinkedinLandingPage(driver);
		initialPageTitle = landingPage.getPageTitle();
		initialPageUrl = landingPage.getPageUrl();
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}

	@Test
	public void successfulLoginTest() {
		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		LinkedinHomePage homePage = landingPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		Assert.assertTrue(homePage.isSignedIn(), "User is not signed in");

		Assert.assertNotEquals(homePage.getPageTitle(), initialPageTitle,
				"Page title did not change after login");
		Assert.assertNotEquals(homePage.getPageUrl(), initialPageUrl,
				"Page url did not change after login");
	}

	@Test
	public void negativeLoginTest() {
		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		LinkedinHomePage homePage = landingPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		Assert.assertFalse(homePage.isSignedIn(), "User is signed in");
	}


}
