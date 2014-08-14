package ECF.automacaoECF.padrao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VerificacoesDeTela {
	org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeTela.class.getName());
	public String tempoMedio = new RecebeParametros().tempoMedio;
	int tempoMedioAceitavel = Integer.parseInt(tempoMedio);
	FuncionalidadesUteis utilidade = new FuncionalidadesUteis();

	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String xpathModulo = new RecebeParametros().xpathModulo;

	public void falha(String mensagem, WebDriver driver, String nomeTeste) throws IOException {

		Random numeroAleatorio = new Random();
		String nomeDoScreenshot = "erro_" + nomeTeste.trim().toLowerCase() + "_" + numeroAleatorio.hashCode();
		File scrsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrsht, new File("./screenshot/" + nomeDoScreenshot + ".png"));
		fail(mensagem + " Screenshot gravado no diretório ./screenshot/ com o nome " + nomeDoScreenshot + ".png");

		encerraNavegador(driver);

	}
	public void aguardaCarregamento(String caminho, String xpathcarregaregistro, String nomeTeste, int tentativas, WebDriver driver) throws InterruptedException, IOException {
		long inicio = System.currentTimeMillis();

		Thread.sleep(1000);

		for (int second = 0;; second++) {
			logger.info("Aguardando o carregamento da tela " + caminho + " || Tentativa " + (second + 1) + " de " + tentativas);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathcarregaregistro, driver, nomeTeste);
			try {

				if (driver.findElement(By.xpath(xpathcarregaregistro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		long duracaoCarregamento = System.currentTimeMillis() - inicio;
		logger.info("Tela " + caminho + " acessada com sucesso!!");

		if (duracaoCarregamento <= tempoMedioAceitavel) {
			logger.info("#OK# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		} else {
			logger.info("#ALERTA# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		}

	}

	public void verificaElementos(int tentativas, String nomeTeste, WebDriver driver, String xpathAbaPesquisa, String xpathAbaResultados, String xpathAbaCadastro) throws InterruptedException, IOException {

		for (int second = 0;; second++) {
			logger.info("Localizando elementos da tela...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathAbaPesquisa + " || " + xpathAbaResultados + " || " + " || " + xpathAbaCadastro, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathAbaPesquisa)).isDisplayed() && driver.findElement(By.xpath(xpathAbaResultados)).isDisplayed() && driver.findElement(By.xpath(xpathAbaCadastro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Elementos localizados com sucesso!");

	}

	public void verificaPresencaCampos(String tela, String nomeTeste, WebDriver driver, int tentativas, String[] campos) throws InterruptedException, IOException {

		logger.info("Verificando campos da tela de " + tela + "...");

		int i = 0;
		while (i < campos.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campos da tela de " + tela + "..." + campos[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + campos[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(campos[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}
	}

	public void verificaLabels(WebDriver driver, int tentativas, String[] xapthlabelsPesquisa, String[] labelsPesquisa) {

		logger.info("Verificando labels...");

		int i = 0;
		while (i < labelsPesquisa.length) {

			if (driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText().contentEquals(labelsPesquisa[i])) {

				logger.info("#OK# Esperado: " + labelsPesquisa[i] + ", Obtido: " + driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelsPesquisa[i] + ", Obtido: " + driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText());
			}

			i++;
		}
	}

	public void verificaBotoes(WebDriver driver, String nomeTeste, int tentativas, String[] botoesPesquisa) throws InterruptedException, IOException {

		logger.info("Verificando botoes esperados na tela...");
		int i = 0;
		while (i < botoesPesquisa.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando botoes esperados na tela... " + botoesPesquisa[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + botoesPesquisa[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(botoesPesquisa[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

	}

	public void verificaColunas(WebDriver driver, String caminho, String nomeTeste, int tentativas, String[] xpathColunas, String[] labelcolunas) throws InterruptedException, IOException {

		logger.info("Verificando a presenca das colunas...");
		int i = 0;
		while (i < xpathColunas.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando a presenca das colunas... " + xpathColunas[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathColunas[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathColunas[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

		logger.info("Verificando Label das colunas...");
		int j = 0;
		while (j < xpathColunas.length) {
			if (driver.findElement(By.xpath(xpathColunas[j])).getText().contentEquals(labelcolunas[j])) {
				logger.info("#OK# Esperado: " + labelcolunas[j] + ", Obtido: " + driver.findElement(By.xpath(xpathColunas[j])).getText());
			} else {
				logger.info("#ALERTA# Esperado: " + labelcolunas[j] + ", Obtido: " + driver.findElement(By.xpath(xpathColunas[j])).getText());
			}
			j++;
		}
	}

	public void efetuaLoginComSucesso(WebDriver driver, int tentativas, String usuario, String senha, String nomeTeste) throws InterruptedException, IOException {

		logger.info("----------------------------------------------------");
		logger.info("ACESSANDO A APLICACAO");
		logger.info("----------------------------------------------------");

		driver.findElement(By.id("j_username")).clear();
		logger.info("Digitando usuario: " + usuario);
		driver.findElement(By.id("j_username")).sendKeys(usuario);
		driver.findElement(By.id("j_password")).clear();
		logger.info("Digitando senha: " + senha);
		driver.findElement(By.id("j_password")).sendKeys(senha);
		logger.info("Clicando no botão 'Entrar'");
		driver.findElement(By.id("taxit_btn_login")).click();

		for (int second = 0;; second++) {
			logger.info("Aguardando o acesso a aplicacao...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado taxit_solution_logo", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("taxit_solution_logo")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Aplicacao acessada com sucesso!");

	}

	public void acessaModuloECF(WebDriver driver, int tentativas, String xpathModulo, String nomeTeste) {
		boolean moduloPresente = false;
		try {

			org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeTela.class.getName());
			for (int i = 0; i == (tentativas + 1); i++) {

				for (int second = 0; second < tentativas; second++) {
					logger.info(second);
					if (second >= tentativas) {
					}

					try {
						moduloPresente = verificaSeEstaNaTela(driver, xpathModulo);
						if (moduloPresente == true)
							logger.info(moduloPresente);
						break;

					} catch (Exception e) {
						Thread.sleep(1000);
					}

				}

				efetuaLogout(driver, tentativas);
				efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);

			}

			logger.info("Acessando o módulo ONESOURCE ECF...");
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			aguardaCarregamento("SELEÇÃO DE MÓDULOS", xpathModulo, nomeTeste, tentativas, driver);

			aguardaElemntoFicarClicavel(driver, tentativas, xpathModulo);

			driver.findElement(By.xpath(xpathModulo)).click();
			aguardaCarregamento("HOME", "/html/body/div[3]/div[2]/div/ul/li/a", nomeTeste, tentativas, driver);
		} catch (Exception e) {
			logger.info(e.toString());
			logger.info(e.getStackTrace().toString());

		}
	}

	public void aguardaElemntoFicarClicavel(WebDriver driver, int tentativas, String xpathElemento) {
		WebDriverWait wait = new WebDriverWait(driver, tentativas);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElemento)));

	}
	private boolean verificaSeEstaNaTela(WebDriver driver, String xpathModulo) {

		if (driver.findElement(By.xpath(xpathModulo)).isDisplayed() == true) {

			logger.info("true");
			return true;

		} else {
			logger.info("false");
			return false;

		}
	}
	public void acessaSistema(WebDriver driver, int tentativas, String url, String usuario, String senha, String navegador, String nomeTeste) throws InterruptedException, IOException {

		logger.info("----------------------------------------------------");
		logger.info("URL: " + url);
		logger.info("USUARIO: " + usuario);
		logger.info("SENHA: " + senha);
		logger.info("NAVEGADOR: " + navegador);
		logger.info("TESTE: " + nomeTeste);
		logger.info("----------------------------------------------------");

		logger.info("Acessando aplicação no endereco " + url);

		driver.get(url);
		driver.manage().window().maximize();

		for (int second = 0;; second++) {
			logger.info("Aguardando tela de login ser carregada | Tentativa " + (second + 1) + " de " + (tentativas + 1));

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado j_username", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("j_username")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);

		}
		logger.info("Tela carregada com sucesso!");

	}
	public void acessaTela(WebDriver driver, int tentativas, String xpathmenu1, String xpathmenu2, String xpathtela, String nomeTeste) throws Throwable {

		for (int second = 0;; second++) {
			logger.info("Localizando Menu Principal... ");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado " + xpathmenu1, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathmenu1)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Menu Principal localizado com sucesso!");

		logger.info("Acessando a tela... " + nomeTeste);
		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(xpathmenu1));
		builder.moveToElement(MenuN1).build().perform();
		driver.findElement(By.xpath(xpathmenu2)).click();
		driver.findElement(By.xpath(xpathtela)).click();

	}

	public void acessaTela(WebDriver driver, int tentativas, String xpathmenu1, String xpathtela) {
		logger.info("Acessando a tela...");

		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(xpathmenu1));
		builder.moveToElement(MenuN1).build().perform();

		driver.findElement(By.xpath(xpathtela)).click();

	}

	public void encerraNavegador(WebDriver driver) {

		driver.close();
		driver.quit();
	}

	public void verificaPresencaDosBotoes(String caminho, String nomeTeste, WebDriver driver, int tentativas, String[] xpathBotoesRegistro) throws InterruptedException, IOException {
		logger.info("Verificando botoes esperados na tela...");
		int i = 0;
		while (i < xpathBotoesRegistro.length) {
			for (int second = 0;; second++) {

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathBotoesRegistro[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(xpathBotoesRegistro[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);

				}

			}
			i++;

		}

	}

	public void verificaTitulos(String caminho, String nomeTeste, WebDriver driver, int tentativas, String[] xpathTitulos) throws InterruptedException, IOException {
		logger.info("Verificando titulos na tela...");
		int i = 0;
		while (i < xpathTitulos.length) {
			for (int second = 0;; second++) {
				if (second >= tentativas)
					falha("timeout, elemento não localizado " + xpathTitulos[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathTitulos[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

	}

	public void verificaLabelAba(WebDriver driver, int tentativas, String labelAba, String idAba, String nomeTeste) throws InterruptedException, IOException {
		for (int second = 0;; second++) {
			logger.info("Verificando label da aba...");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado ", driver, nomeTeste);
			try {
				if (driver.findElement(By.id(idAba)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
			if (driver.findElement(By.id(idAba)).getText().contentEquals(labelAba)) {

				logger.info("#OK# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());
			}
		}
	}
	public void efetuaLoginComErro(WebDriver driver, String nomeTeste, int tentativas, String url, String usuario, String senha) throws InterruptedException, IOException {
		logger.info("----------------------------------------------------");
		logger.info("ACESSANDO A APLICACAO COM USUARIO E SENHA INVÁLIDOS");
		logger.info("----------------------------------------------------");

		driver.findElement(By.id("j_username")).clear();
		logger.info("Digitando usuario: " + usuario);
		driver.findElement(By.id("j_username")).sendKeys(usuario);
		driver.findElement(By.id("j_password")).clear();
		logger.info("Digitando senha: " + senha);
		driver.findElement(By.id("j_password")).sendKeys("ERRADA");
		logger.info("Clicando no botão 'Entrar'");
		driver.findElement(By.id("taxit_btn_login")).click();

		for (int second = 0;; second++) {
			logger.info("Localizando mensagem de erro 'Usuário e/ou senha inválido(s).'...");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado ", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("outputTextBadCredentials")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Mensagem de erro localizado com sucesso! - outputTextBadCredentials");

	}

	public void remover(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; ++i) {
				remover(files[i]);
			}
		}
		f.delete();
	}

	public void informaTeste(int i, String caminho, String nomeTeste) {

		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
		dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");

		if (i == 0) {
			logger.info("#########################################################################");
			logger.info(nomeTeste + " - TESTE INICIADO EM " + dateFormatter.format(now).toUpperCase());
			logger.info("#########################################################################");
		} else if (i == 1) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE PESQUISA, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 2) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE RESULTADOS, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 3) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE REGISTRO, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 4) {
			logger.info("----------------------------------------------------");
			logger.info("INTEGRAÇÃO: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else {
			logger.info("????????????????????????????????????????????????????");
			logger.info("INVÁLIDO");
			logger.info("????????????????????????????????????????????????????");
		}

	}

	public void verificaElementos(int tentativas, String nomeTeste, WebDriver driver, String xpathAbaPesquisa, String xpathAbaResultados) throws InterruptedException, IOException {
		for (int second = 0;; second++) {
			logger.info("Localizando elementos da tela...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathAbaPesquisa + " || " + xpathAbaResultados, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathAbaPesquisa)).isDisplayed() && driver.findElement(By.xpath(xpathAbaResultados)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

	}

	public void acessaAba(WebDriver driver, int tentativas, String xpathRegistroAba2, String nomeTeste, String xpathCaregaRegistro) throws IOException, InterruptedException {

		Actions builder = new Actions(driver);
		WebElement teste = driver.findElement(By.xpath(xpathCaregaRegistro));
		builder.moveToElement(teste).build().perform();

		for (int second = 0;; second++) {
			logger.info("Localizando Aba2... " + xpathRegistroAba2);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathRegistroAba2, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathRegistroAba2)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(xpathRegistroAba2)).click();
	}

	public void moveMouseSobre(WebDriver driver, String caminho, String nomeTeste, int tentativas, String string) throws Throwable {
		for (int second = 0;; second++) {
			logger.info("Localizando Elemento... " + string);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + string, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(string)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(string));
		builder.moveToElement(MenuN1).build().perform();

	}

	public void pesquisaRegistro(WebDriver driver, String xpathPesquisaCaixaCampo2, String valorPesquisarPesquisaCaixaCampo2, String idBotaoExecutarConsulta) throws InterruptedException {

		driver.findElement(By.xpath(xpathPesquisaCaixaCampo2)).clear();
		logger.info("Digitando valor a ser pesquisado: " + valorPesquisarPesquisaCaixaCampo2);
		driver.findElement(By.xpath(xpathPesquisaCaixaCampo2)).sendKeys(valorPesquisarPesquisaCaixaCampo2);
		logger.info("Clicando no botão pesquisar: " + idBotaoExecutarConsulta);
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();

	}

	public void verificaRegistroIntegrado(WebDriver driver, int tentativas, String nomeTeste, String xpathReferencia, String resultadoReferencia) throws IOException, InterruptedException {
		for (int second = 0;; second++) {
			logger.info("Localizando Registro... " + resultadoReferencia);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathReferencia, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathReferencia)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Registro " + resultadoReferencia + " localizado com Sucesso");

	}

	public void acessaAbaPorId(WebDriver driver, int tentativas, String idAbaRegistro, String nomeTeste) throws IOException, InterruptedException {

		Actions builder = new Actions(driver);
		WebElement teste = driver.findElement(By.id(idAbaRegistro));
		builder.moveToElement(teste).build().perform();

		for (int second = 0;; second++) {
			logger.info("Localizando Aba ... " + idAbaRegistro);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + idAbaRegistro, driver, nomeTeste);
			try {
				if (driver.findElement(By.id(idAbaRegistro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		driver.findElement(By.id(idAbaRegistro)).click();

	}

	public void informaTerminoDoTeste(String nomeTeste, String categoria, long duracaoTeste) {
		logger.info("#########################################################################");
		logger.info("TESTE " + nomeTeste + " ENCERRADO COM SUCESSO EM " + utilidade.formataDuracao(duracaoTeste));
		logger.info("#########################################################################");

	}

	public void acessaTelaPorClick(WebDriver driver, int tentativas, String xpathMenu1, String xpathMenu2, String xpathMenu3, String xpathMenu4, String xpathTela, String nomeTeste, String labelMenu1, String labelMenu2, String labelMenu3, String labelMenu4, String labelTela, int qtdeMenuInt) throws InterruptedException {
		logger.info("Acessando a Tela...");

		if (qtdeMenuInt == 1) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}

			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 2) {
			try {
				aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			try {
				aguardaCarregamento(nomeTeste, xpathMenu2, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}

			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 3) {
			try {
				aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu2, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu3, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu3)).getText().contentEquals(labelMenu3)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu3)).click();
			}

			try {
				aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			Thread.sleep(500);
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 4) {
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu2, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			Thread.sleep(500);
			try {
				aguardaCarregamento(nomeTeste, xpathMenu3, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu3)).getText().contentEquals(labelMenu3)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu3)).click();
			}

			Thread.sleep(500);
			try {
				aguardaCarregamento(nomeTeste, xpathMenu4, nomeTeste, tentativas, driver);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu4)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu4)).getText().contentEquals(labelMenu4)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu4)).click();
			}

			Thread.sleep(500);
			try {
				aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 0) {

			Thread.sleep(500);
			//aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			Thread.sleep(500);

			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

		}

	}
	public void acessaAbaPorXpath(WebDriver driver, int tentativas, String xpathCarregaRegistro, String nomeTeste) throws InterruptedException, IOException {
		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);

		driver.findElement(By.xpath(xpathCarregaRegistro)).click();

		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[1]/a"));
		action.moveToElement(we).build().perform();

	}

	public void acessaModulo(WebDriver driver, int tentativas, String xpathModulo) throws InterruptedException, IOException {

		for (int second = 0;; second++) {
			logger.info("Localizando o link do módulo...");

			if (second >= tentativas)
				//falha("Timeout, módulo não localizado" + xpathModulo, driver, "Acesso ao Módulo");
				efetuaLogoutLogin(driver, tentativas);

			try {

				if (driver.findElement(By.xpath(xpathModulo)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

	}

	private void efetuaLogoutLogin(WebDriver driver, int tentativas) throws InterruptedException, IOException {
		efetuaLogout(driver, tentativas);

		efetuaLoginComSucesso(driver, tentativas, usuario, senha, "Login");

	}

	private void efetuaLogout(WebDriver driver, int tentativas) throws InterruptedException, IOException {
		logger.info("Efetuando Logout...");
		aguardaCarregamento("Logout", "//*[@id='taxit_logoff']/a", "Logout", tentativas, driver);
		driver.findElement(By.xpath("//*[@id='taxit_logoff']/a")).click();
		aguardaCarregamento("Logout", "//*[@id='j_username']", "Logout", tentativas, driver);
		logger.info("Logout efetuado com sucesso!");

	}
	public boolean verificaSeApresentaMensagemDeErro(WebDriver driver, String nomeTeste, int tentativas, String caminho) throws InterruptedException, IOException {

		Thread.sleep(500);
		boolean apareceuErro;
		String xpathErro = "/html/body/div[13]/div";
		apareceuErro = verificaSeElementoEstaPresente(driver, xpathErro);
		if (apareceuErro == true) {
			logger.info("Foi Apresentada mensagem de erro não prevista!");
			falha("Foi Apresentada mensagem de erro não prevista!", driver, nomeTeste);
			return (apareceuErro);
		}
		return apareceuErro;

	}
	private boolean verificaSeElementoEstaPresente(WebDriver driver, String xpathErro) {
		if (driver.findElements(By.xpath(xpathErro)).size() != 0) {
			logger.info("#ALERTA# Foi aprsentada mensagem de erro!");
			return true;
		} else {
			logger.info("#OK# Nehuma mensagem de erro localizada!");
			return false;
		}

	}
	public void verificaRegistroIntegrado(WebDriver driver, String url, String usuario, String senha, String navegador, String nomeTeste, int tentativas, String nomeIntegracao, String[] informacoesRegistro, String qtdeMenu, String labelMenu1, String xpathMenu1, String labelMenu2, String xpathMenu2, String labelMenu3, String xpathMenu3, String labelMenu4, String xpathMenu4, String labelTela, String xpathTela, String xPathCarregaPesquisa, String estabelecimento, String[] camposTelaPesquisa, String idBotaoExecutarConsulta, String xpathCarregaResultadoPesquisa, String xpathCarregaRegistro, String[] camposTelaCadastro, String[] informacoesTelaCadastro) throws Throwable {

		int qtdeMenuInt = Integer.parseInt(qtdeMenu);
		logger.info("Acessando o sistema para verificar o registro integrado...");

		if (nomeIntegracao.contentEquals("ledgeraccount")) {
			informaTeste(4, nomeTeste, nomeTeste);
			acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
			efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
			acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
			aguardaCarregamento("Home", "//*[@id='ui-id-1']/span", nomeTeste, tentativas, driver);
			acessaTelaPorClick2(driver, qtdeMenuInt, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenuInt, tentativas);
			aguardaCarregamento(nomeTeste, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

			logger.info("Fazendo a pesquisa do documento integrado...");
			logger.info("Preenchendo campo 'CNPJ' com " + estabelecimento);
			driver.findElement(By.xpath(camposTelaPesquisa[0])).clear();
			driver.findElement(By.xpath(camposTelaPesquisa[0])).sendKeys(estabelecimento);
			logger.info("Preenchendo campo 'Código Conta Contábil' com " + informacoesRegistro[1]);
			driver.findElement(By.xpath(camposTelaPesquisa[1])).clear();
			driver.findElement(By.xpath(camposTelaPesquisa[1])).sendKeys(informacoesRegistro[1]);
			logger.info("Clicando no botão 'Executar Consulta");
			driver.findElement(By.id(idBotaoExecutarConsulta)).click();
			aguardaCarregamento(nomeTeste, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);

			//Coluna Resultado de Pesquisa 'Código Conta Contábil'
			logger.info("Verificando se o registro é apresentado na tela de resultados");
			if (driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[4]/div/div[1]/div/form/div[4]/div[2]/div[1]/span/div/div[1]/table/tbody/tr/td[3]")).isDisplayed() == false) {
				falha("Registro não foi visualizado na tela de resultado de pesquisa", driver, nomeTeste);
			}

			logger.info("Registro localizado na tyela de resultados da pesquisa");
			logger.info("Clicando em 'Consultar'");
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			driver.findElement(By.id("TAXIT:trTbrCad2002LedactBean:tab1:PageTemplateToolbarSearchCrudConsult")).click();
			aguardaCarregamento(nomeTeste, xpathCarregaRegistro, nomeTeste, tentativas, driver);

			logger.info("Verificando as informacoes do registro integrado...");

			comparaValoresDosCampos(driver, tentativas, camposTelaCadastro, informacoesTelaCadastro);

		}

	}
	public void comparaValoresDosCampos(WebDriver driver, int tentativas, String[] campos, String[] informacoes) {

		logger.info("Comparando os dados integrados com os presentes na tela...");

		int i = 0;
		while (i < campos.length) {

			if (driver.findElement(By.id(campos[i])).getText().contentEquals(informacoes[i])) {

				logger.info("#OK# Esperado: " + informacoes[i] + ", Obtido: " + driver.findElement(By.id(campos[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + informacoes[i] + ", Obtido: " + driver.findElement(By.id(campos[i])).getText());
			}

			i++;
		}

	}
	public void acessaTelaPorClick2(WebDriver driver, int qtdeMenuInt, String xpathMenu1, String xpathMenu2, String xpathMenu3, String xpathMenu4, String xpathTela, String nomeTeste, String labelMenu1, String labelMenu2, String labelMenu3, String labelMenu4, String labelTela, int qtdeMenuInt2, int tentativas) throws InterruptedException, IOException {
		if (qtdeMenuInt == 0) {

			aguardaCarregamentoPorLinkText(nomeTeste, labelTela, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelTela)).click();
			Thread.sleep(1000);

		} else if (qtdeMenuInt == 1) {
			driver.findElement(By.linkText(labelMenu1)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelTela, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelTela)).click();
			Thread.sleep(1000);

		} else if (qtdeMenuInt == 2) {
			driver.findElement(By.linkText(labelMenu1)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu2, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu2)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelTela, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelTela)).click();
			Thread.sleep(1000);

		} else if (qtdeMenuInt == 3) {
			driver.findElement(By.linkText(labelMenu1)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu2, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu2)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu3, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu3)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelTela, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelTela)).click();
			Thread.sleep(1000);

		} else if (qtdeMenuInt == 4) {
			driver.findElement(By.linkText(labelMenu1)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu2, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu2)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu3, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu3)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelMenu4, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelMenu4)).click();
			aguardaCarregamentoPorLinkText(nomeTeste, labelTela, nomeTeste, tentativas, driver);
			driver.findElement(By.linkText(labelTela)).click();
			Thread.sleep(1000);

		} else if (qtdeMenuInt == 30) {

			try {
				aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu2, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			try {
				aguardaCarregamento(nomeTeste, xpathMenu3, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				logger.info(e);
			}
			if (!driver.findElement(By.xpath(xpathMenu3)).getText().contentEquals(labelMenu3)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathMenu3)).click();
			}

			try {
				aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			} catch (InterruptedException e) {

				logger.info(e);
			} catch (IOException e) {

				logger.info(e);
			}
			Thread.sleep(500);
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				try {
					falha("Tela não está localizada no local correto", driver, nomeTeste);
				} catch (IOException e) {

					logger.info(e);
				}
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		}

	}
	public void aguardaCarregamentoPorLinkText(String nomeTeste, String linkReferencia, String nomeTeste2, int tentativas, WebDriver driver) throws IOException, InterruptedException {

		long inicio = System.currentTimeMillis();

		Thread.sleep(1000);

		for (int second = 0;; second++) {
			logger.info("Aguardando o carregamento da tela " + nomeTeste + " || Tentativa " + (second + 1) + " de " + tentativas);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + linkReferencia, driver, nomeTeste);
			try {

				if (driver.findElement(By.linkText(linkReferencia)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		long duracaoCarregamento = System.currentTimeMillis() - inicio;
		logger.info("Tela " + nomeTeste + " acessada com sucesso!!");

		if (duracaoCarregamento <= tempoMedioAceitavel) {
			logger.info("#OK# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		} else {
			logger.info("#ALERTA# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		}
	}
	public void aguardaProcessamentoDesaparecer(WebDriver driver, int tentativas, String nomeTeste) throws IOException, InterruptedException {

		for (int second = 0;; second++) {
			logger.info("Aguardando tela de loading desaparecer || Tentativa " + (second + 1) + " de " + tentativas);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + "//*[@id='taxit_loading']", driver, nomeTeste);
			try {

				if (!driver.findElement(By.xpath("//*[@id='taxit_loading']")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		logger.info("Tela " + nomeTeste + " acessada com sucesso!!");

	}

	public void pesquisaRegistroIntegrado(WebDriver driver, int tentativas, int qtdePesquisa, String[] camposPesquisa, String[] valoresPesquisa, String idBotaoExecutarConsulta) throws InterruptedException, IOException {
		aguardaCarregamentoPorId(driver, tentativas, "Tela Pesquisa", camposPesquisa[0]);

		for (int i = 0; i < qtdePesquisa; i++) {

			logger.info("Preenchendo campos de pesquisa |" + camposPesquisa[i] + " : " + valoresPesquisa[i]);
			driver.findElement(By.id(camposPesquisa[i])).sendKeys(valoresPesquisa[i]);

		}
		logger.info("Clicando no botão 'Executar Consulta'");
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();

	}

	private void aguardaCarregamentoPorId(WebDriver driver, int tentativas, String nomeTeste, String idReferencia) throws IOException, InterruptedException {
		long inicio = System.currentTimeMillis();

		Thread.sleep(1000);

		for (int second = 0;; second++) {
			logger.info("Aguardando o carregamento da tela " + nomeTeste + " || Tentativa " + (second + 1) + " de " + tentativas);

			if (second >= tentativas)
				try {
					falha("Timeout, elemento nao localizado " + idReferencia, driver, nomeTeste);
				} catch (Exception e1) {

				}
			try {

				if (driver.findElement(By.id(idReferencia)).isDisplayed())
					break;
			} catch (Exception e) {

			}
			Thread.sleep(1000);
		}
		long duracaoCarregamento = System.currentTimeMillis() - inicio;
		logger.info("Tela " + nomeTeste + " acessada com sucesso!!");

		if (duracaoCarregamento <= tempoMedioAceitavel) {
			logger.info("#OK# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		} else {
			logger.info("#ALERTA# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		}

	}
	public void verificaResultadoDaPesquisa(WebDriver driver, int tentativas, int qtdeResultados, String[] colunasResultados, String[] valoresResultados, String[] idBotoesResultados) throws IOException, InterruptedException {
		aguardaCarregamentoPorId(driver, tentativas, "Resultados da Pesquisa", idBotoesResultados[0]);
		aguardaCarregamento("Consulta de Registro", colunasResultados[0], "Consulta de Registro", tentativas, driver);

		for (int i = 0; i < qtdeResultados; i++) {

			logger.info("Comparando | " + driver.findElement(By.xpath(colunasResultados[i])).getText() + " : " + valoresResultados[i]);
			if (driver.findElement(By.xpath(colunasResultados[i])).getText().startsWith(valoresResultados[i])) {
			} else {
				falha("Não foi retornado resultado esperado: " + "Esperado: " + valoresResultados[i] + ", Obtido: " + driver.findElement(By.xpath(colunasResultados[i])).getText(), driver, "Resultados da Pesquisa");
			}

		}
		logger.info("Clicando no botão 'Consultar'");
		driver.findElement(By.id(idBotoesResultados[0])).click();

	}
	public void verificaTelaCadastro(WebDriver driver, int tentativas, int qtdeCadastro, String[] camposCadastro, String[] informacoesCadastro) throws IOException, InterruptedException {
		aguardaCarregamentoPorId(driver, tentativas, "Tela de Cadastro", camposCadastro[0]);

		for (int i = 0; i < qtdeCadastro; i++) {

			logger.info("Comparando | Obtido: " + driver.findElement(By.id(camposCadastro[i])).getAttribute("value") + " : Esperado: " + informacoesCadastro[i]);
			if (driver.findElement(By.id(camposCadastro[i])).getAttribute("value").contentEquals(informacoesCadastro[i])) {
			} else {
				falha("Não foi retornado resultado esperado: " + "Esperado: " + informacoesCadastro[i] + ", Obtido: " + driver.findElement(By.id(camposCadastro[i])).getAttribute("value"), driver, "Tela de Cadastro");
			}

		}
		logger.info("Elementos localizados na tela com sucesso!");

	}
	public boolean verificaExistenciaDoRegistro(WebDriver driver, int tentativas, String nomeTeste, int qtdePesquisa, String[] camposPesquisa, String[] valoresPesquisa, String idBotaoExecutarConsulta, int qtdeResultados, String[] colunasResultados, String[] valoresResultados) throws IOException, InterruptedException {
		logger.info("----------------------------------------------------");
		logger.info("VERIFICANDO SE O REGISTRO EXISTE...");
		logger.info("----------------------------------------------------");

		aguardaCarregamentoPorId(driver, tentativas, "Tela Pesquisa", camposPesquisa[0]);

		for (int i = 0; i < qtdePesquisa; i++) {

			logger.info("Preenchendo campos de pesquisa |" + camposPesquisa[i] + " : " + valoresPesquisa[i]);
			driver.findElement(By.id(camposPesquisa[i])).sendKeys(valoresPesquisa[i]);

		}
		logger.info("Clicando no botão 'Executar Consulta'");
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();

		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
		aguardaCarregamento(nomeTeste, "//*[@id='ui-id-1']", nomeTeste, tentativas, driver);

		Boolean iselementpresent = driver.findElements(By.xpath(colunasResultados[0])).size() != 0;
		if (iselementpresent == true) {

			logger.info("----------------------------------------------------");
			logger.info("Registro localizado! Iniciando o processo de Exclusão...");
			logger.info("----------------------------------------------------");
			return true;
		} else {
			logger.info("----------------------------------------------------");
			logger.info("Registro não localizado! Iniciando o processo de integração do registro...");
			logger.info("----------------------------------------------------");
			return false;

		}

	}

	public boolean isElementPresentByXpath(WebDriver driver, String xpath) {
		boolean present;
		try {
			driver.findElement(By.xpath(xpath));
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
		return present;
	}
	public void reotrnaSistema(WebDriver driver, int tentativas, String url, String usuario2, String senha2, String navegador, String nomeTeste) throws IOException, InterruptedException {
		logger.info("----------------------------------------------------");
		logger.info("URL: " + url);
		logger.info("USUARIO: " + usuario);
		logger.info("SENHA: " + senha);
		logger.info("NAVEGADOR: " + navegador);
		logger.info("TESTE: " + nomeTeste);
		logger.info("----------------------------------------------------");

		logger.info("Acessando aplicação no endereco " + url);

		driver.get(url);

		for (int second = 0;; second++) {
			logger.info("Aguardando carregamento da tela | Tentativa " + (second) + " de " + (tentativas));

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado j_username", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("taxit_solution_logo")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);

		}
		logger.info("Tela carregada com sucesso!");

	}
	public void VerificaExclusaoDoRegistro(WebDriver driver, int tentativas, String nomeTeste, int qtdePesquisa, String[] camposPesquisa, String[] valoresPesquisa, String idBotaoExecutarConsulta, int qtdeResultados, String[] colunasResultados, String[] valoresResultados) throws IOException, InterruptedException {
		logger.info("----------------------------------------------------");
		logger.info("VERIFICANDO SE O REGISTRO EXISTE...");
		logger.info("----------------------------------------------------");

		aguardaCarregamentoPorId(driver, tentativas, "Tela Pesquisa", camposPesquisa[0]);

		for (int i = 0; i < qtdePesquisa; i++) {

			logger.info("Preenchendo campos de pesquisa |" + camposPesquisa[i] + " : " + valoresPesquisa[i]);
			driver.findElement(By.id(camposPesquisa[i])).sendKeys(valoresPesquisa[i]);

		}
		logger.info("Clicando no botão 'Executar Consulta'");
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();

		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
		aguardaCarregamento(nomeTeste, "//*[@id='ui-id-1']", nomeTeste, tentativas, driver);

		Boolean iselementpresent = driver.findElements(By.xpath(colunasResultados[0])).size() != 0;
		if (iselementpresent == true) {

			falha("O Registro não foi excluído", driver, nomeTeste);

		} else {
			logger.info("----------------------------------------------------");
			logger.info("Excluido com sucesso!");
			logger.info("----------------------------------------------------");

		}

	}
	public void verificaCamposTelaDePesquisa(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposPesquisa, String[] labelsTelaPesquisa, String[] idLabelsTelaPesquisa, String[] idCamposTelaPesquisa) throws IOException, InterruptedException {
		if (verificaCamposPesquisa == true) {
			verificaPresencaCamposDePesquisaPorId(nomeTeste, driver, tentativas, idLabelsTelaPesquisa, idCamposTelaPesquisa);
			validaLabels(driver, nomeTeste, tentativas, labelsTelaPesquisa, idLabelsTelaPesquisa);
		} else {
			//Não valida nada!
		}

	}
	public void validaLabels(WebDriver driver, String nomeTeste, int tentativas, String[] labelsTelaPesquisa, String[] idLabelsTelaPesquisa) {
		logger.info("Verificando labels...");

		int i = 0;
		while (i < labelsTelaPesquisa.length) {

			if (driver.findElement(By.id(idLabelsTelaPesquisa[i])).getText().contentEquals(labelsTelaPesquisa[i])) {

				logger.info("#OK# Esperado: " + labelsTelaPesquisa[i] + ", Obtido: " + driver.findElement(By.id(idLabelsTelaPesquisa[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelsTelaPesquisa[i] + ", Obtido: " + driver.findElement(By.id(idLabelsTelaPesquisa[i])).getText());
			}

			i++;
		}

	}
	public void verificaPresencaCamposDePesquisaPorId(String nomeTeste, WebDriver driver, int tentativas, String[] idLabelsTelaPesquisa, String[] idCamposTelaPesquisa) throws IOException, InterruptedException {
		logger.info("Verificando a presença dos campos da tela de " + nomeTeste + "...");

		int i = 0;
		while (i < idLabelsTelaPesquisa.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campo " + idLabelsTelaPesquisa[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + idLabelsTelaPesquisa[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(idLabelsTelaPesquisa[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

		int u = 0;
		while (i < idLabelsTelaPesquisa.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campo " + idCamposTelaPesquisa[u]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + idCamposTelaPesquisa[u], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(idCamposTelaPesquisa[u])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			u++;

		}

	}
	public void verificaCamposTelaDeResultados(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposResultados, String[] xpathColunasResultados, String[] labelsColunasResultados) throws IOException, InterruptedException {
		if (verificaCamposResultados == true) {
			verificaCamposResultadosPorXpath(driver, nomeTeste, tentativas, caminho, xpathColunasResultados, labelsColunasResultados);
			validaLabelsTelaResultados(driver, nomeTeste, tentativas, caminho, xpathColunasResultados, labelsColunasResultados);
		} else {
			//Não valida nada!
		}
	}
	private void validaLabelsTelaResultados(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] xpathColunasResultados, String[] labelsColunasResultados) {
		logger.info("Verificando labels...");

		int i = 0;
		while (i < xpathColunasResultados.length) {

			if (driver.findElement(By.xpath(xpathColunasResultados[i])).getText().contentEquals(labelsColunasResultados[i])) {

				logger.info("#OK# Esperado: " + labelsColunasResultados[i] + ", Obtido: " + driver.findElement(By.xpath(xpathColunasResultados[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelsColunasResultados[i] + ", Obtido: " + driver.findElement(By.xpath(xpathColunasResultados[i])).getText());
			}

			i++;
		}
	}
	private void verificaCamposResultadosPorXpath(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] xpathColunasResultados, String[] labelsColunasResultados) throws IOException, InterruptedException {

		int i = 0;
		while (i < xpathColunasResultados.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campo " + xpathColunasResultados[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathColunasResultados[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathColunasResultados[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}
	}
	public void verificaCamposTelaDeCadastro(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposCadastro, String[] labelsCadastro, String[] idLabelsCadastro, String[] idCaixasCadastro) throws IOException, InterruptedException {
		if (verificaCamposCadastro == true) {
			verificaPresencaCamposDeCadastroPorId(nomeTeste, driver, tentativas, idLabelsCadastro, idCaixasCadastro);
			comparaLabelsPorId(driver, nomeTeste, tentativas, caminho, idLabelsCadastro, labelsCadastro);
		} else {
			//Não valida nada!
		}

	}
	private void comparaLabelsPorId(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] idLabelsCadastro, String[] labelsCadastro) {
		logger.info("Verificando labels...");

		int i = 0;
		while (i < idLabelsCadastro.length) {

			if (driver.findElement(By.id(idLabelsCadastro[i])).getText().contentEquals(labelsCadastro[i])) {

				logger.info("#OK# Esperado: " + labelsCadastro[i] + ", Obtido: " + driver.findElement(By.id(idLabelsCadastro[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelsCadastro[i] + ", Obtido: " + driver.findElement(By.id(idLabelsCadastro[i])).getText());
			}

			i++;
		}

	}
	private void verificaPresencaCamposDeCadastroPorId(String nomeTeste, WebDriver driver, int tentativas, String[] idLabelsCadastro, String[] idCaixasCadastro) throws IOException, InterruptedException {
		logger.info("Verificando a presença dos campos da tela de " + nomeTeste + "...");

		int i = 0;
		while (i < idLabelsCadastro.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campo " + idLabelsCadastro[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + idLabelsCadastro[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(idLabelsCadastro[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

		int u = 0;
		while (i < idLabelsCadastro.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campo " + idCaixasCadastro[u]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + idCaixasCadastro[u], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(idCaixasCadastro[u])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			u++;

		}

	}
	public void verificaTamanhoDeArray(WebDriver driver, String nomeTeste, String[] array1, String[] array2, String[] array3) throws IOException {
		if (array1.length == array2.length) {
			if (array2.length == array3.length) {
				logger.info("A quantidade de campos, labels foram informadas corretamente no .properties");
			}
		} else {
			logger.info("A quantidade de campos, labels não foram informadas corretamente no .properties");
			logger.info("Array 1: " + array1.length);
			logger.info("Array 2: " + array2.length);
			logger.info("Array 3: " + array3.length);
			falha("A quantidade de campos, labels não foram informadas corretamente no .properties", driver, nomeTeste);
		}

	}

	public void verificaTamanhoDeArray(WebDriver driver, String nomeTeste, String[] array1, String[] array2) throws IOException {
		if (array1.length == array2.length) {
			logger.info("A quantidade de campos, labels foram informadas corretamente no .properties");
		} else {
			logger.info("A quantidade de campos, labels não foram informadas corretamente no .properties");
			logger.info("Array 1: " + array1.length);
			logger.info("Array 1: " + array2.length);
			logger.info("A quantidade de campos, labels não foram informadas corretamente no .properties");
			falha("A quantidade de campos, labels não foram informadas corretamente no .properties", driver, nomeTeste);

		}

	}
	public void verificaSeExiteCamposNaoPrevistos(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposPesquisa, String[] labelsTelaPesquisa, String[] idLabelsTelaPesquisa, String[] idCamposTelaPesquisa) {
		logger.info("Verificando se existe mais campos na tela...");

		int qtdeCamposPrevistos = idCamposTelaPesquisa.length;

		List<WebElement> elementsRoot = driver.findElements(By.xpath("//*[@id='TAXIT:taxitDesktopTabInitial1']"));
		int qtdeCamposNaTela=elementsRoot.size();
		
		logger.info("Campos previstos: "+qtdeCamposPrevistos);
		logger.info("Campos encontrados: "+qtdeCamposNaTela);
		
		
	}
}
