package page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

}
