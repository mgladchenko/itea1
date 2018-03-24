package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.LinkedinPasswordResetSubmitPage;
import page.LinkedinRequestPasswordResetPage;

public class LinkedinPasswordResetTest extends LinkedinBaseTest{

	String userEmail = "iteatest@i.ua";

	@Test
	public void sucessfulPasswordReset() {
		LinkedinRequestPasswordResetPage requestPasswordResetPage = landingPage.forgotPasswordLinkClick();
		Assert.assertTrue(requestPasswordResetPage.isLoaded(), "requestPasswordResetPage is not loaded");

		LinkedinPasswordResetSubmitPage passwordResetSubmitPage = requestPasswordResetPage.submitEmail(userEmail);
		Assert.assertTrue(passwordResetSubmitPage.isLoaded(), "passwordResetSubmitPage is not loaded");

				//read email
	}
}
