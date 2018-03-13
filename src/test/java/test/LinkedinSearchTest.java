package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LinkedinHomePage;
import page.LinkedinLandingPage;
import page.LinkedinSearchPage;

import java.util.List;

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
	public void basicSearchTest(){
		String searchTerm = "hr";

		LinkedinLandingPage loginPage = new LinkedinLandingPage(driver);
		LinkedinHomePage homePage = loginPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		LinkedinSearchPage searchPage = homePage.searchByTerm(searchTerm);
		List<String> results = searchPage.getResults();

		Assert.assertEquals(results.size(), 10, "Number of results is wrong");

		for (String result : results) {
			Assert.assertTrue(result.toLowerCase().contains(searchTerm),
					"Searchterm "+searchTerm+ " not found in cart");
		}
	}
}
