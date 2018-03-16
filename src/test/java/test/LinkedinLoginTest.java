package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.LinkedinHomePage;
import page.LinkedinLoginPage;

public class LinkedinLoginTest extends LinkedinBaseTest{

	@Test
	public void successfulLoginTest() {
		String initialPageTitle = landingPage.getPageTitle();
		String initialPageUrl = landingPage.getPageUrl();
		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		LinkedinHomePage homePage = landingPage.loginAs("iteatest@i.ua", "1q2w3e_4r5t");
		Assert.assertTrue(homePage.isSignedIn(), "User is not signed in");

		Assert.assertNotEquals(homePage.getPageTitle(), initialPageTitle,
				"Page title did not change after login");
		Assert.assertNotEquals(homePage.getPageUrl(), initialPageUrl,
				"Page url did not change after login");
	}


	@DataProvider
	public Object[][] negativeTestCredentialsReturnedToLanding() {
		return new Object[][]{
				{"", ""}};
	}

	@Test(dataProvider="negativeTestCredentialsReturnedToLanding")
	public void negativeLoginTestReturnedToLanding(String email, String password) {
		String initialPageTitle = landingPage.getPageTitle();
		String initialPageUrl = landingPage.getPageUrl();
		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		landingPage = landingPage.loginAs(email, password);
		Assert.assertEquals(landingPage.getPageUrl(), initialPageUrl,
				"Different page URL is loaded");

	}

	@DataProvider
	public Object[][] negativeTestCredentialsReturnedToLogin() {
		return new Object[][]{
				{"xyz", "xyz", "Please enter a valid email address", "The password you provided must have at least 6 characters."}};
	}

	@Test(dataProvider="negativeTestCredentialsReturnedToLogin")
	public void negativeLoginTestReturnedToLogin(String email, String password, String emailMessage, String passwordMessage) {
		String initialPageTitle = landingPage.getPageTitle();
		String initialPageUrl = landingPage.getPageUrl();
		Assert.assertEquals(initialPageTitle, "LinkedIn: Log In or Sign Up",
				"Login page title is wrong");

		LinkedinLoginPage loginPage = landingPage.loginAs(email, password);
		Assert.assertNotEquals(loginPage.getPageUrl(), initialPageUrl,
				"Not redirected to login page");

		String actualEmailMessage = loginPage.getEmailMessage();
		String actualPasswordMessage = loginPage.getPasswordMessage();

		Assert.assertEquals(emailMessage, actualEmailMessage, "Actual and Expected Email messages do not match");
		Assert.assertEquals(emailMessage, actualEmailMessage, "Actual and Expected Password messages do not match");


	}


}
