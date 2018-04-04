package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class LinkedinBasePage {
	WebDriver driver;

	/**
	 * Constructor of LinkedinBasePage class which takes WebDriver instance initialized in @BeforeMethod
	 * for reuse in LinkedinBasePage class methods
	 * @param driver - WebDriver instance
	 */
	public LinkedinBasePage (WebDriver driver){
		this.driver = driver;
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Wait until WebElement is Clickable on Web page
	 * @param webElement - WebElement to Wait for
	 * @return WebElement after wait
	 */
	public WebElement waitUntilElementIsClickable (WebElement webElement){
		waitUntilElementIsClickable(webElement, 10);
		return webElement;
	}

	public WebElement waitUntilElementIsClickable (WebElement webElement, int timeOutInSeconds){
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		return webElement;
	}

	public void waitUntilElementIsVisible (WebElement webElement, int timeOutInSeconds){
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public abstract boolean isLoaded();

}
