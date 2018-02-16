import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.Thread.sleep;

public class BadCodeExample {

	public static void main(String args[]) throws InterruptedException {
		System.out.println("Hello World");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.google.com/");
		sleep(5000);
	}
}
