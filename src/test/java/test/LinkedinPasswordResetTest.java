package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.LinkedinPasswordResetSubmitPage;
import page.LinkedinRequestPasswordResetPage;
import utils.GMailService;

public class LinkedinPasswordResetTest extends LinkedinBaseTest{

	String userEmail = "mykola.gladchenko@gmail.com";

	@Test
	public void successfulPasswordReset() {
		LinkedinRequestPasswordResetPage requestPasswordResetPage = landingPage.forgotPasswordLinkClick();
		Assert.assertTrue(requestPasswordResetPage.isLoaded(), "requestPasswordResetPage is not loaded");

		LinkedinPasswordResetSubmitPage passwordResetSubmitPage = requestPasswordResetPage.submitEmail(userEmail);
		//Manually selected "email" option and "Submit" button. Need to automate.
		//Assert.assertTrue(passwordResetSubmitPage.isLoaded(), "passwordResetSubmitPage is not loaded");

		//read email
		String messageSubjectPartial = "here's the link to reset your password";
		String messageToPartial = "mykola.gladchenko@gmail.com";
		String messageFromPartial = "security-noreply@linkedin.com";

		GMailService GMailService = new GMailService();
		String message = GMailService.waitForNewMessage(messageSubjectPartial, messageToPartial, messageFromPartial, 60);
		System.out.println("Content: " + message);

		//proceed with next steps here

	}
}
