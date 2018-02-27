import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class LinkedinSearchTest {
	WebDriver driver;

	@BeforeMethod
	public void beforeTest(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.linkedin.com/");
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}

	@Test
	public void basicSearchTest() throws InterruptedException {
		LinkedinLoginPage loginPage = new LinkedinLoginPage(driver);
		loginPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");

		//search
		String searchTerm = "hr";

		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@type='search-icon']")).click();
         //[contains(@class,'search-result__occluded-item')]

		sleep(3000);

		List<WebElement> results = driver.findElements(By.xpath("//li[contains(@class,'search-result__occluded-item')]"));
		//int currentResultsNumber = results.size();
		Assert.assertEquals(results.size(), 10, "Number of results is wrong");

		for (WebElement result : results) {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", result);
			String cardTitle = result.getText();
			System.out.println("XXXX");
			System.out.println(cardTitle);
			Assert.assertTrue(cardTitle.toLowerCase().contains(searchTerm),
			"Searchterm "+searchTerm+ " not found in cart");
		}



	}
}
