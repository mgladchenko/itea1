package page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.GMailService;

public class LinkedinPasswordResetSubmitPage extends LinkedinBasePage{

	@FindBy(xpath = "//a[@class='status-link btn-resend-link']")
	private WebElement resentButtonLink;


	public LinkedinPasswordResetSubmitPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isLoaded() {
		boolean isLoaded;
		try {
			isLoaded = resentButtonLink.isDisplayed();
		}
		catch (NoSuchElementException e){
			isLoaded = false;
		}
		return isLoaded;
	}

	public LinkedinChooseNewPasswordPage navigateToResetPasswordLink(String resetPasswordLink) {
		driver.get(resetPasswordLink);
		return new LinkedinChooseNewPasswordPage(driver);
	}

	public String getResetPasswordLinkFromEmail(String messageToPartial) {
		String messageSubjectPartial = "here's the link to reset your password";
		String messageFromPartial = "security-noreply@linkedin.com";
		GMailService GMailService = new GMailService();
		String message = GMailService.waitForNewMessage(messageSubjectPartial, messageToPartial, messageFromPartial, 60);

		String resetPasswordLink = StringUtils.substringBetween(message, "browser:", "This link").trim();
        return resetPasswordLink;
	}
}
