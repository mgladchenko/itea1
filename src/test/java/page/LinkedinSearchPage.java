package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSearchPage extends LinkedinBasePage{

	@FindBy(xpath = "//li[contains(@class,'search-result__occluded-item')]")
	private List<WebElement> resultsWebElementList;

	@FindBy(xpath = "//div[@role='main']")
	private WebElement resultsContainer;

	public LinkedinSearchPage(WebDriver driver) {
		super(driver);
	}

	public List<String> getResults() {
		waitUntilElementIsClickable(resultsContainer);
		List<String> resultsStringList = new ArrayList();
		for (WebElement result : resultsWebElementList) {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", result);
			String cardTitle = result.getText();
			resultsStringList.add(cardTitle);
			//System.out.println("XXXX");
			//System.out.println(cardTitle);

		}

		return resultsStringList;
	}
}
