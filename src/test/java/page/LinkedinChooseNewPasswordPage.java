package page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedinChooseNewPasswordPage extends LinkedinBasePage{

	@FindBy(xpath = "//input[@name='new_password']")
	private WebElement newPasswordField;

	@FindBy(xpath = "//input[@name='new_password_again']")
	private WebElement newPasswordRetypeField;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement submitButton;


	public LinkedinChooseNewPasswordPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isLoaded() {
		boolean isLoaded;
		try {
			isLoaded = newPasswordField.isDisplayed();
		}
		catch (NoSuchElementException e){
			isLoaded = false;
		}
		return isLoaded;
	}

	public LinkedinPasswordChangedSuccessPage submitNewPassword(String newPassword) {
		newPasswordField.sendKeys(newPassword);
		newPasswordRetypeField.sendKeys(newPassword);
		waitUntilElementIsClickable(submitButton).click();
		return new LinkedinPasswordChangedSuccessPage(driver);
	}

}
