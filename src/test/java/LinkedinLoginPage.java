import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LinkedinLoginPage {
	WebDriver driver;

	public LinkedinLoginPage (WebDriver driver){
		this.driver = driver;
	}

	private WebElement emailField;
	private WebElement passwordField;
	private WebElement signInButton;

	private void initElements(){
		emailField = driver.findElement(By.id("login-email"));
		passwordField = driver.findElement(By.id("login-password"));
		signInButton = driver.findElement(By.id("login-submit"));
	}

	public void loginAs(String email, String password){
		initElements();
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		signInButton.click();
	}


}
