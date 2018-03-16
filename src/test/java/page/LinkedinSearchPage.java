package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSearchPage extends LinkedinBasePage{

	@FindBy(xpath = "//li[contains(@class,'search-result__occluded-item')]")
	private List<WebElement> resultsWebElementList;

	@FindBy(xpath = "//h3[contains(@class,'search-results__total')]")
	private WebElement resultsNumber;

	public LinkedinSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public List<String> getResults() {
		waitUntilElementIsVisible(resultsNumber, 10);
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
