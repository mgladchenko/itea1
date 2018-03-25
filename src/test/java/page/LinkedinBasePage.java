package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class LinkedinBasePage {
	WebDriver driver;

	public LinkedinBasePage (WebDriver driver){
		this.driver = driver;
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	public void waitUntilElementIsClickable (WebElement webElement){
		waitUntilElementIsClickable(webElement, 10);
	}

	public void waitUntilElementIsClickable (WebElement webElement, int timeOutInSeconds){
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void waitUntilElementIsVisible (WebElement webElement, int timeOutInSeconds){
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	//public abstract boolean isLoaded();

}
