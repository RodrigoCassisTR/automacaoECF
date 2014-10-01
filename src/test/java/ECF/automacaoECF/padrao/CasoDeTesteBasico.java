package ECF.automacaoECF.padrao;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

	

public class CasoDeTesteBasico {

	protected Properties properties;
	protected WebDriver driver;
	protected String navegador;

	@Before
	public void setup() throws IOException {
		carregarParametros();
		carregarPropriedades();
		selecionarNavegador();
	}

	private void carregarParametros() {
		navegador = new RecebeParametros().navegador;
	}

	private void selecionarNavegador() {
		if (navegador.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized", "forced-maximize-mode",
					"disable-threaded-compositing", "disable-gpu",
					"disable-custom-jumplist", "disable-desktop-notifications",
					"disable-device-orientation", "disable-extensions",
					"disable-webgl", "no-default-browser-check");

			DesiredCapabilities capabilities = new DesiredCapabilities(
					DesiredCapabilities.chrome());
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver(capabilities);
		}
		if (navegador.equals("firefox")) {

			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		if (navegador.equals("ie")) {
			File fileDriver = new File("./drivers/iexploredriver64.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("ensureCleanSession", true);
			System.setProperty("webdriver.ie.driver", fileDriver.getAbsolutePath());
			driver = new InternetExplorerDriver(ieCapabilities);

		}
		if (navegador.equals("html")) {
			driver = new HtmlUnitDriver(true);
		}
		if (navegador.equals("phantomjs")) {
			DesiredCapabilities ds = DesiredCapabilities.phantomjs();
			File file = new File("./drivers/phantomjs.exe");
			System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
			ds.setJavascriptEnabled(true);
			// ds.setCapability("takeScreenshot", true);
			driver = new PhantomJSDriver(ds);

		}
	}

	private void carregarPropriedades() throws FileNotFoundException, IOException {
		System.out.println("Localizando arquivo .properties");
		String filename = "./src/test/resources/" + this.getClass().getSimpleName() + ".properties";

		this.properties = new Properties();
		FileInputStream file = new FileInputStream(filename);
		properties.load(file);
	}
}
