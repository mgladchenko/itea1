package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedinLandingPage extends LinkedinBasePage{

	@FindBy(id = "login-email")
	private WebElement emailField;

	@FindBy(id = "login-password")
	private WebElement passwordField;

	@FindBy(id = "login-submit")
	private WebElement signInButton;

	@FindBy(xpath = "//a[@class='link-forgot-password']")
	private WebElement forgotPasswordLink;

	public LinkedinLandingPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public LinkedinRequestPasswordResetPage forgotPasswordLinkClick() {
		forgotPasswordLink.click();
		return new LinkedinRequestPasswordResetPage(driver);
	}

	public <T> T loginAs(String email, String password){
		waitUntilElementIsClickable(emailField, 5);
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		signInButton.click();
		if (getPageUrl().contains("/feed")) {
			return (T) new LinkedinHomePage(driver);
		}
		if (getPageUrl().contains("/login-submit")) {
			return (T) new LinkedinLoginPage(driver);
		}
		else {
			return (T) this;
		}
	}


}
