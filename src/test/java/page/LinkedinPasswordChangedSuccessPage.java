package page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedinPasswordChangedSuccessPage extends LinkedinBasePage{

	@FindBy(xpath = "//div[@class='form-actions']/a[@href]")
	private WebElement goToHomeButton;


	public LinkedinPasswordChangedSuccessPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isLoaded() {
		boolean isLoaded;
		try {
			isLoaded = goToHomeButton.isDisplayed();
		}
		catch (NoSuchElementException e){
			isLoaded = false;
		}
		return isLoaded;
	}

}
