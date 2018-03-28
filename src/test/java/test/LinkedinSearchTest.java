package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.LinkedinHomePage;
import page.LinkedinSearchPage;

import java.util.List;

public class LinkedinSearchTest extends LinkedinBaseTest{

	/**
	 * Test that verifies basic Search by specific Search term
	 */
	@Test
	public void basicSearchTest(){
		String searchTerm = "hr";

		LinkedinHomePage homePage = landingPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		LinkedinSearchPage searchPage = homePage.searchByTerm(searchTerm);
		List<String> results = searchPage.getResults();

		Assert.assertEquals(results.size(), 10, "Number of results is wrong");

		for (String result : results) {
			Assert.assertTrue(result.toLowerCase().contains(searchTerm),
					"Searchterm "+searchTerm+ " not found in cart");
		}
	}
}
