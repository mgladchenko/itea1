package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedinHomePage extends LinkedinBasePage{

	@FindBy(id = "profile-nav-item")
	private WebElement userIcon;

	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchField;

	@FindBy(xpath = "//*[@type='search-icon']")
	private WebElement searchIcon;


	public LinkedinHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isSignedIn() {
		waitUntilElementIsClickable(userIcon);
		return userIcon.isDisplayed();
	}

	public LinkedinSearchPage searchByTerm(String searchTerm) {
		waitUntilElementIsClickable(searchField);
		searchField.sendKeys(searchTerm);
		searchIcon.click();
		return new LinkedinSearchPage(driver);
	}
}
