
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TelaTipoDeFeriado {
	WebDriver driver;

	@Before
	public void setup() {

		driver = new FirefoxDriver();
	}

	@Test
	public void telaTipoDeFeriado() throws InterruptedException {
		driver.get("http://166.78.0.215:8084/taxbr-web/pages/login.jsf");

		
		
		for (int second = 0;; second++) {

			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.id("j_username")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);

		}
		
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("ADM");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("adm@2014");
		
		driver.findElement(By.id("taxit_btn_login")).click();
		
		

	}

	@After
	public void tearDown() {

	}

}
