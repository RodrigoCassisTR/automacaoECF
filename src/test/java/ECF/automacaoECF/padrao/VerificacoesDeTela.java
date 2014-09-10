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
import org.openqa.selenium.Alert;
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

		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");
		logger.info("FALHA: " + mensagem);
		logger.info("Screenshot gravado no diretório ./screenshot/ com o nome " + nomeDoScreenshot + ".png");
		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");

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
			logger.info("TELA DE CADASTRO, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 4) {
			logger.info("----------------------------------------------------");
			logger.info("INTEGRAÇÃO: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 5) {
			logger.info("----------------------------------------------------");
			logger.info("ABA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else {
			logger.info("????????????????????????????????????????????????????");
			logger.info("INVÁLIDO");
			logger.info("????????????????????????????????????????????????????");
		}

	}

	public void informaTeste(int i, String caminho, String nomeTeste, String labelAba) {

		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
		dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");

		if (i == 0) {
			logger.info("#########################################################################");
			logger.info(nomeTeste + " - TESTE INICIADO EM " + dateFormatter.format(now).toUpperCase());
			logger.info("#########################################################################");
		} else if (i == 1) {
			logger.info("----------------------------------------------------");
			logger.info("ABA: " + labelAba);
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
	public void acessaAbaPorXpath(WebDriver driver, int tentativas, String xpathAba, String nomeTeste) throws InterruptedException, IOException {
		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);

		driver.findElement(By.xpath(xpathAba)).click();

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
			aguardaCarregamento(nomeTeste, xpathMenu1, nomeTeste, tentativas, driver);
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			driver.findElement(By.xpath(xpathMenu1)).click();

			aguardaCarregamento(nomeTeste, xpathMenu2, nomeTeste, tentativas, driver);
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			driver.findElement(By.xpath(xpathMenu2)).click();

			aguardaCarregamento(nomeTeste, xpathMenu3, nomeTeste, tentativas, driver);
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			driver.findElement(By.xpath(xpathMenu3)).click();

			aguardaCarregamento(nomeTeste, xpathTela, nomeTeste, tentativas, driver);
			logger.info(driver.findElement(By.xpath(xpathTela)).getText() + " >");
			driver.findElement(By.xpath(xpathTela)).click();

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

	}

	public void pesquisaRegistroIntegrado(WebDriver driver, int tentativas, int qtdePesquisa, String[] camposPesquisa, String[] valoresPesquisa, String idBotaoExecutarConsulta) throws InterruptedException, IOException {

		aguardaCarregamentoPorId(driver, tentativas, "Tela Pesquisa", camposPesquisa[0]);
		aguardaProcessamentoDesaparecer(driver, tentativas, "Tela Pesquisa");
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
	public boolean verificaResultadoDaPesquisa(WebDriver driver, int tentativas, int qtdeResultados, String[] colunasResultados, String[] valoresResultados, String[] idBotoesResultados, int qtdePesquisa, String[] camposPesquisa, String[] valoresPesquisa, String idBotaoExecutarConsulta, String xpathSemResultados) throws IOException, InterruptedException {
		aguardaProcessamentoDesaparecer(driver, tentativas, "Resultados da Pesquisa");
		aguardaCarregamentoPorId(driver, tentativas, "Resultados da Pesquisa", idBotoesResultados[0]);
		aguardaProcessamentoDesaparecer(driver, tentativas, "Resultados da Pesquisa");
		aguardaCarregamento("Resultado da Pesquisa", "//*[@id='ui-id-1']/span", "Resultados da Pesquisa", tentativas, driver);

		logger.info("Verificando se apresenta a mensagem: 'Não foi encontrada nenhuma linha.' com o xpath " + xpathSemResultados);

		boolean noExists = driver.findElements(By.xpath(xpathSemResultados)).size() == 0;

		if (noExists == true) {

			logger.info("Não foi apresentada mensagem de registro não localizado");

			for (int i = 0; i < qtdeResultados; i++) {
				logger.info("Iniciando comparação do resultado de pesquisa...");
				logger.info("Comparando | " + driver.findElement(By.xpath(colunasResultados[i])).getText() + " : " + valoresResultados[i]);
				if (driver.findElement(By.xpath(colunasResultados[i])).getText().contentEquals(valoresResultados[i])) {
				} else {
					logger.info("#ALERTA# Não foi retornado resultado esperado: " + "Esperado: " + valoresResultados[i] + ", Obtido: " + driver.findElement(By.xpath(colunasResultados[i])).getText());
				}
			}
			logger.info("Clicando no botão 'Consultar'");
			aguardaCarregamentoPorId(driver, tentativas, "Pesquisa", idBotoesResultados[0]);
			driver.findElement(By.id(idBotoesResultados[0])).click();

		} else {
			logger.info("Foi Apresentada a mensagem " + driver.findElement(By.xpath(xpathSemResultados)).getText());
		}
		return noExists;

	}
	@SuppressWarnings("unused")
	private void fechaAba(WebDriver driver, int tentativas) throws IOException, InterruptedException {
		logger.info("Fechando Aba...");
		aguardaCarregamentoPorId(driver, tentativas, "Processo de Integração", "ui-id-1");
		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[2]/a/span[2]")).click();
		aguardaCarregamentoPorId(driver, tentativas, "Processo de Integração", "ui-id-1");
		logger.info("Aba fechada com sucesso");

	}
	public void verificaTelaCadastro(WebDriver driver, int tentativas, int qtdeCadastro, String[] camposCadastro, String[] informacoesCadastro) throws IOException, InterruptedException {
		aguardaCarregamentoPorId(driver, tentativas, "Tela de Cadastro", camposCadastro[0]);

		for (int i = 0; i < qtdeCadastro; i++) {
			if (driver.findElement(By.id(camposCadastro[i])).isDisplayed() == true) {
				logger.info("Comparando | Obtido: " + driver.findElement(By.id(camposCadastro[i])).getAttribute("value") + " : Esperado: " + informacoesCadastro[i]);

				if (driver.findElement(By.id(camposCadastro[i])).getAttribute("value").contentEquals(informacoesCadastro[i])) {
				} else {
					logger.info("#ALERTA# O valor do campo de ID: " + camposCadastro[i] + "está com o valor " + driver.findElement(By.id(camposCadastro[i])).getAttribute("value"));
				}
			} else {
				falha("Elemento não localizado " + camposCadastro[i], driver, "Cadastro");
			}

		}

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
		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
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
	public void verificaCamposTelaDePesquisa(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposPesquisa, String[] labelsTelaPesquisa, String[] idLabelsTelaPesquisa, String[] idCamposTelaPesquisa, String idBotaoExecutarConsulta, String idBotaoLimpar) throws IOException, InterruptedException {
		String[] idBotoesPesquisa = {idBotaoExecutarConsulta, idBotaoLimpar};

		if (verificaCamposPesquisa == true) {
			verificaPresencaCamposPorId(nomeTeste, driver, tentativas, idLabelsTelaPesquisa, idCamposTelaPesquisa, idBotoesPesquisa, true, true, true);
			validaLabels(driver, nomeTeste, tentativas, labelsTelaPesquisa, idLabelsTelaPesquisa);
			verificaSeCaixasEstaoVazias(driver, nomeTeste, tentativas, caminho, idCamposTelaPesquisa);
			verificaToolTip(driver, nomeTeste, tentativas, caminho, idLabelsTelaPesquisa, labelsTelaPesquisa);
			verificaBotoesDeAcao(driver, nomeTeste, tentativas, caminho, idBotaoExecutarConsulta, idBotaoLimpar);
		} else {
			logger.info("Campos da tela de Cadastro não serão validados, verificaCamposPesquisa=false");
		}

	}
	private void verificaBotoesDeAcao(WebDriver driver, String nomeTeste, int tentativas, String caminho, String idBotaoExecutarConsulta, String idBotaoLimpar) throws IOException {

		logger.info("Verificando botoes 'Executar Pesquisa' e 'Limpar'...");
		if (driver.findElement(By.id(idBotaoExecutarConsulta)).isDisplayed() && driver.findElement(By.id(idBotaoExecutarConsulta)).isDisplayed()) {

		} else {
			falha("Timeout, elemento nao localizado " + idBotaoExecutarConsulta + " e " + idBotaoLimpar, driver, nomeTeste);
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

	public void verificaCamposTelaDeResultados(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean verificaCamposResultados, String[] xpathColunasResultados, String[] labelsColunasResultados, String[] idBotoesResultados, boolean possuiBotoesNaTelaResultados) throws IOException, InterruptedException {
		if (verificaCamposResultados == true) {
			verificaCamposResultadosPorXpath(driver, nomeTeste, tentativas, caminho, xpathColunasResultados, labelsColunasResultados);
			validaLabelsTelaResultados(driver, nomeTeste, tentativas, caminho, xpathColunasResultados, labelsColunasResultados);
			validaBotoesResultados(driver, nomeTeste, tentativas, caminho, possuiBotoesNaTelaResultados, idBotoesResultados);
		} else {
			//Não valida nada!
		}
	}
	private void validaBotoesResultados(WebDriver driver, String nomeTeste, int tentativas, String caminho, boolean possuiBotoesNaTelaResultados, String[] idBotoesResultados) throws IOException, InterruptedException {
		if (possuiBotoesNaTelaResultados == true) {
			int i = 0;
			while (i < idBotoesResultados.length) {
				for (int second = 0;; second++) {
					logger.info("Verificando botão " + idBotoesResultados[i]);

					if (second >= tentativas)
						falha("Timeout, botão nao localizado nao localizado " + idBotoesResultados[i], driver, nomeTeste);
					try {
						if (driver.findElement(By.id(idBotoesResultados[i])).isDisplayed())
							break;
					} catch (Exception e) {
						Thread.sleep(1000);
					}

				}
				i++;

			}
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

	public void verificaToolTip(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] idLabels, String[] labels) throws IOException {
		logger.info("Verificando o tooltip 'i' dos campos...");

		int i = 0;
		while (i < idLabels.length) {
			WebElement elemento = driver.findElement(By.id(idLabels[i]));
			if (isAttribtuePresent(elemento, "original-title") == true) {

				boolean toolTip = driver.findElement(By.id(idLabels[i])).getAttribute("original-title").isEmpty();

				if (toolTip == false) {
					logger.info("Tooltip 'i' do campo " + idLabels[i] + " localizado com sucesso!");
				} else {
					logger.info("#ALERTA# Tooltip Tooltip 'i' não localizado no campo ");
					//	falha("Tooltip 'i' não localizado no campo " + idLabels[i], driver, nomeTeste);
				}
			} else {

			}

			i++;

		}

	}

	private void verificaSeCaixasEstaoVazias(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] idCaixas) throws IOException {
		logger.info("Verificando se as caixas de texto estão vazias ...");
		try {

			int i = 0;
			while (i < idCaixas.length) {

				String tipoDeCaixa = driver.findElement(By.id(idCaixas[i])).getAttribute("type");

				if (tipoDeCaixa.contentEquals("text")) {
					String valueCaixa = driver.findElement(By.id(idCaixas[i])).getAttribute("value");
					if (valueCaixa.contentEquals("")) {
						logger.info("Caixa " + idCaixas[i] + " está vazia");
					} else {
						logger.info("A caixa de texto " + idCaixas[i] + " está preenchida com valor!");
						//falha("A caixa de texto " + idCaixas[i] + " está preenchida com valor!", driver, nomeTeste);

					}
				} else {

				}

				i++;
			}
		} catch (NoSuchElementException e) {
			logger.info(e.toString());
			falha("Elemento não localizado!", driver, nomeTeste);
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

	public void verificaPresencaCamposPorId(String nomeTeste, WebDriver driver, int tentativas, String[] idLabels, String[] idCaixas, String[] idBotoes, boolean possuiLabelsNaTelaCadastro, boolean possuiCaixasNaTelaCadastro, boolean possuiBotoesNaTelaCadastro) throws IOException, InterruptedException {

		logger.info("Verificando a presença dos campos da tela de " + nomeTeste + "...");

		if (possuiLabelsNaTelaCadastro == true) {
			logger.info("Verificando a presença de todas as Labels...");
			verificaExistenciaDeComponentesPorId(nomeTeste, driver, tentativas, idLabels);
		}

		if (possuiCaixasNaTelaCadastro == true) {
			logger.info("Verificando a presença de todas as caixas, checkbox e radio buttons...");
			verificaExistenciaDeComponentesPorId(nomeTeste, driver, tentativas, idCaixas);
		}

		if (possuiBotoesNaTelaCadastro == true) {
			logger.info("Verificando a presença de todos os botões...");
			verificaExistenciaDeComponentesPorId(nomeTeste, driver, tentativas, idBotoes);
		}

	}
	private void verificaExistenciaDeComponentesPorId(String nomeTeste, WebDriver driver, int tentativas, String[] idComponentes) throws IOException, InterruptedException {
		int i = 0;
		while (i < idComponentes.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando componente " + idComponentes[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + idComponentes[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(idComponentes[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;
		}
	}
	private void verificaExistenciaDeComponentesPorXpath(String nomeTeste, WebDriver driver, int tentativas, String[] xpathComponentes) throws IOException, InterruptedException {
		int i = 0;
		while (i < xpathComponentes.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando componente " + xpathComponentes[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathComponentes[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathComponentes[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

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
		int qtdeCamposNaTela = 0;

		List<WebElement> elementsRoot = driver.findElements(By.xpath("/html/body/div[3]/div[2]/div/div[4]/div/div[1]/div/form/div[4]/div[1]/div[2]/div[1]/table/tbody"));

		for (int i = 0; i < elementsRoot.size(); ++i) {
			@SuppressWarnings("unused")
			WebElement checkbox = elementsRoot.get(i).findElement(By.xpath(".//input"));
			qtdeCamposPrevistos++;
		}
		logger.info(elementsRoot.size());

		logger.info("Campos previstos: " + qtdeCamposPrevistos);
		logger.info("Campos encontrados: " + qtdeCamposNaTela);

	}
	public void efetuaLogout(WebDriver driver, int tentativas, String url, String usuario2, String senha2, String navegador, String nomeTeste) throws IOException, InterruptedException {
		logger.info("Efetuando logout da aplicação...");
		aguardaCarregamentoPorId(driver, tentativas, nomeTeste, "ui-id-1");
		driver.findElement(By.linkText("Sair")).click();
		aguardaCarregamentoPorId(driver, tentativas, nomeTeste, "j_username");
		logger.info("Logout efetuado com sucesso!");

		logger.info("Excluindo todos os Cookies...");
		driver.manage().deleteAllCookies();
		aguardaCarregamentoPorId(driver, tentativas, nomeTeste, "j_username");
		logger.info("Cookies excluidos com sucesso!");

		logger.info("Atualizando página...");
		driver.navigate().refresh();
		aguardaCarregamentoPorId(driver, tentativas, nomeTeste, "j_username");
		logger.info("Página atualizada com sucesso!");

	}
	public void verificaAbasDaTelaCadastro(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean possuiAbas, String[] xpathAbasDaTelaCadastro, String[] labelAbasDaTelaCadastro) {
		if (possuiAbas == true) {
			//Processo de validação das abas
			logger.info("Validando abas, quantidade de abas: " + labelAbasDaTelaCadastro.length);
			comparaLabelsPorXpath(driver, nomeTeste, tentativas, labelAbasDaTelaCadastro, xpathAbasDaTelaCadastro);

		} else {
			logger.info("Não existem abas para validação nesta tela!");
		}

	}
	private void comparaLabelsPorXpath(WebDriver driver, String nomeTeste, int tentativas, String[] labels, String[] xpathDaLebel) {
		logger.info("Verificando labels...");

		int i = 0;
		while (i < labels.length) {

			if (driver.findElement(By.xpath(xpathDaLebel[i])).getText().contentEquals(labels[i])) {

				logger.info("#OK# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.xpath(xpathDaLebel[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.xpath(xpathDaLebel[i])).getText());
			}

			i++;
		}

	}
	public void acessaAbas(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean possuiAbas, String[] xpathAbasDaTelaCadastro) throws InterruptedException, IOException {

		if (possuiAbas == true) {

			logger.info("Acessando Abas...");
			for (int i = 0; i < xpathAbasDaTelaCadastro.length; i++) {
				aguardaCarregamento(driver.findElement(By.xpath(xpathAbasDaTelaCadastro[i])).getText(), xpathAbasDaTelaCadastro[i], nomeTeste, tentativas, driver);
				driver.findElement(By.xpath(xpathAbasDaTelaCadastro[i])).click();
				aguardaCarregamento(driver.findElement(By.xpath(xpathAbasDaTelaCadastro[i])).getText(), xpathAbasDaTelaCadastro[i], nomeTeste, tentativas, driver);
				logger.info("Aba " + driver.findElement(By.xpath(xpathAbasDaTelaCadastro[i])).getText() + " acessada com sucesso!");

				//comparaLabelsPorId(driver, nomeTeste, tentativas, caminho, idLabelsCadastro, labelsCadastro);

			}
		} else {
			logger.info("Não existem abas para serem acessadas!");
		}
	}

	public void validaCamposDasAbas(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean possuiAbas, String xpathAbasDaTelaCadastro, String[] labelsAbaDaTelaCadastro, String[] idlabelsAbaDaTelaCadastro, String[] idCaixasAbasDaTelaCadastro, String[] idBotoesAbaDaTelaCadastro) throws InterruptedException, IOException {
		if (possuiAbas == true) {
			logger.info("----------------------------------------------------");
			logger.info("Acessando a Aba " + driver.findElement(By.xpath(xpathAbasDaTelaCadastro)).getText());
			logger.info("----------------------------------------------------");
			driver.findElement(By.xpath(xpathAbasDaTelaCadastro)).click();
			aguardaCarregamento(caminho, xpathAbasDaTelaCadastro, nomeTeste, tentativas, driver);
			logger.info("Aba " + driver.findElement(By.xpath(xpathAbasDaTelaCadastro)).getText() + " acessada com sucesso!");

			comparaLabelsPorId(driver, nomeTeste, tentativas, caminho, idlabelsAbaDaTelaCadastro, labelsAbaDaTelaCadastro);

		} else {
			logger.info("Não existem abas para serem acessadas!");
		}

	}
	public void validaAbasDaTelaCadastro(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean possuiAbas, String[] xpathAbasDaTelaCadastro, String[] labelsAba, String[] idLabelsAba, String[] idCaixasAba, String[] idBotoesAba, String[] possuiBotoesNaAba, String[] possuiTitulosNaAba) throws InterruptedException, IOException {
		if (possuiAbas == true) {
			for (int i = 0; i < xpathAbasDaTelaCadastro.length; i++) {
				String[] labelsAbaDaTelaCadastro = labelsAba[i].split(",");
				String[] idlabelsAbaDaTelaCadastro = idLabelsAba[i].split(",");
				String[] idCaixasAbasDaTelaCadastro = idCaixasAba[i].split(",");
				String[] idBotoesAbaDaTelaCadastro = idBotoesAba[i].split(",");
				validaCamposDasAbas(driver, nomeTeste, caminho, tentativas, possuiAbas, xpathAbasDaTelaCadastro[i], labelsAbaDaTelaCadastro, idlabelsAbaDaTelaCadastro, idCaixasAbasDaTelaCadastro, idBotoesAbaDaTelaCadastro);
				verificaSeCaixasEstaoVazias(driver, nomeTeste, tentativas, caminho, idCaixasAbasDaTelaCadastro);
				verificaToolTip(driver, nomeTeste, tentativas, caminho, idlabelsAbaDaTelaCadastro, labelsAbaDaTelaCadastro);
			}
		}
	}
	public void executaConsulta(WebDriver driver, String nomeTeste, int tentativas, String idBotaoExecutarConsulta) throws InterruptedException, IOException {
		try {

			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			driver.findElement(By.id(idBotaoExecutarConsulta)).click();
		} catch (Exception e) {
			falha("Não foi possivel executar a consulta", driver, nomeTeste);
		}

	}
	public void validaAbaDaTela(WebDriver driver, String nomeTeste, int tentativas, String idAba, String labelAba) throws IOException {
		logger.info("Verificando a Aba da tela...");

		if (driver.findElement(By.id(idAba)).isDisplayed() == true) {

			if (driver.findElement(By.id(idAba)).getText().contentEquals(labelAba)) {
				logger.info("#OK# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());

			}
		} else {
			falha("Aba não localizada! ", driver, nomeTeste);
		}

	}
	private boolean isAttribtuePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String result1 = element.getAttribute(attribute);
			if (result1 != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}
	public void validaTitulosDaTelaPorXpath(String nomeTeste, WebDriver driver, int tentativas, String[] labelTitulos, String[] xpathTitulos, boolean possuiTitulosNaTelaCadastro) throws IOException, InterruptedException {

		if (possuiTitulosNaTelaCadastro == true) {
			logger.info("-----");
			logger.info("Verificando a existencia dos títulos...");
			logger.info("-----");
			verificaExistenciaDeComponentesPorXpath(nomeTeste, driver, tentativas, xpathTitulos);
			logger.info("-----");
			logger.info("Validando a label dos títulos...");
			logger.info("-----");
			validaLabelsPorXpath(driver, nomeTeste, tentativas, labelTitulos, xpathTitulos);

		} else {

		}

	}

	public void validaLabelsPorXpath(WebDriver driver, String nomeTeste, int tentativas, String[] labels, String[] xpathElementos) {

		int i = 0;
		while (i < xpathElementos.length) {

			if (driver.findElement(By.xpath(xpathElementos[i])).getText().contentEquals(labels[i])) {

				logger.info("#OK# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.xpath(xpathElementos[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.xpath(xpathElementos[i])).getText());
			}

			i++;
		}

	}
	public void validaLabelsDaTelCadastro(String nomeTeste, WebDriver driver, int tentativas, String[] labelCampo, String[] idLabelCampo, String[] possuiTootip, String[] toolTipEsperado) throws IOException {

		logger.info("-----");
		logger.info("Verificando labels da tela...");
		logger.info("-----");
		validaLabelsPorId(driver, nomeTeste, tentativas, labelCampo, idLabelCampo);
		logger.info("-----");
		logger.info("Verificando tooltip das labels...");
		logger.info("-----");
		validaTootip(driver, nomeTeste, tentativas, idLabelCampo, possuiTootip, toolTipEsperado);

	}

	private void validaTootip(WebDriver driver, String nomeTeste, int tentativas, String[] idLabel, String[] possuiTootip, String[] toolTipEsperado) throws IOException {

		for (int i = 0; i < idLabel.length; i++) {
			logger.info("-----------------------");
			logger.info(idLabel[i]);
			logger.info("-----------------------");
			if (Boolean.parseBoolean(possuiTootip[i]) == true) {
				boolean toolTipVazia = driver.findElement(By.id(idLabel[i])).getAttribute("original-title").isEmpty();
				String tooltipObtido = driver.findElement(By.id(idLabel[i])).getAttribute("original-title");

				if (toolTipVazia == false) {
					if (tooltipObtido.contentEquals(toolTipEsperado[i])) {
						logger.info("#OK# Tooltip esperado: " + toolTipEsperado[i] + ", tooltip obtido: " + driver.findElement(By.id(idLabel[i])).getAttribute("original-title"));
					} else {

						logger.info("#ALERTA# Tooltip esperado: " + toolTipEsperado[i] + ", tooltip obtido: " + driver.findElement(By.id(idLabel[i])).getAttribute("original-title"));
						String obtido = driver.findElement(By.id(idLabel[i])).getAttribute("original-title");
						//falha("#ALERTA# Tooltip esperado: " + toolTipEsperado[i] + ", tooltip obtido: " + obtido, driver, nomeTeste);
					}
				} else {
					logger.info("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + idLabel[i]);
					falha("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + idLabel[i], driver, nomeTeste);
				}
			} else {
				logger.info("Elemento " + idLabel[i] + " não possui tooltip 'i' para verificar");
			}

		}

	}
	public void validaLabelsPorId(WebDriver driver, String nomeTeste, int tentativas, String[] labels, String[] idElementos) {

		int i = 0;
		while (i < idElementos.length) {

			if (driver.findElement(By.id(idElementos[i])).getText().contentEquals(labels[i])) {

				logger.info("#OK# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.id(idElementos[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labels[i] + ", Obtido: " + driver.findElement(By.id(idElementos[i])).getText());
			}

			i++;
		}
	}
	public void validaElementosInput(String nomeTeste, WebDriver driver, int tentativas, String[] idCaixaCampo, String[] possuiValue, String[] vlrEsperadoCaixaCampo) throws IOException {
		logger.info("-----");
		logger.info("Verificando elementos input, como caixas de texto, checkbox, radiobutton, etc...");
		logger.info("-----");
		for (int i = 0; i < idCaixaCampo.length; i++) {
			if (Boolean.parseBoolean(possuiValue[i]) == true) {
				String valueObtido = driver.findElement(By.id(idCaixaCampo[i])).getAttribute("value");

				if (vlrEsperadoCaixaCampo[i].contentEquals(" ")) {
					vlrEsperadoCaixaCampo[i] = "";
				}

				if (valueObtido.contentEquals(vlrEsperadoCaixaCampo[i])) {
					logger.info("#OK# Input " + idCaixaCampo[i] + " preenchido com o valor esperado, obtido: " + driver.findElement(By.id(idCaixaCampo[i])).getAttribute("value") + " valor esperado: " + vlrEsperadoCaixaCampo[i]);
				} else {

					logger.info("#ALERTA# Input " + idCaixaCampo[i] + " não preenchido com o valor esperado, obtido: " + driver.findElement(By.id(idCaixaCampo[i])).getAttribute("value") + " valor esperado: " + vlrEsperadoCaixaCampo[i]);
					//falha("#ALERTA#  Input não preenchido com o valor esperado, input: " + idCaixaCampo[i] + " valor esperado: " + vlrEsperadoCaixaCampo[i], driver, nomeTeste);
				}
			} else {
				logger.info("Elemento " + idCaixaCampo[i] + " não possui value para validar!");
			}

		}

	}
	public void validaBotoesDaTelaCadastro(String nomeTeste, WebDriver driver, int tentativas, String[] idBotoesTelaCadastro, String[] valueBotoes, boolean possuiBotoesNaTelaCadastro) throws IOException {
		if (possuiBotoesNaTelaCadastro == true) {

			for (int i = 0; i < idBotoesTelaCadastro.length; i++) {
				logger.info("-----");
				logger.info("Verificando botões da tela cadastro...");
				logger.info("-----");
				String valueObtido = driver.findElement(By.id(idBotoesTelaCadastro[i])).getAttribute("value");
				if (valueObtido.contentEquals(valueBotoes[i])) {
					logger.info("Input " + idBotoesTelaCadastro[i] + " Preenchido com o valor esperado " + valueBotoes[i] + "!");
				} else {

					//logger.info("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + idBotoesTelaCadastro[i]);
					falha("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + idBotoesTelaCadastro[i], driver, nomeTeste);
				}

			}

		}
	}
	public void validaBotoesPadrao(String nomeTeste, WebDriver driver, int tentativas, boolean possuiBotoesNaTelaCadastro, String[] idBotoesPadraoCadastro) {
		if (possuiBotoesNaTelaCadastro == true) {

			for (int i = 0; i < idBotoesPadraoCadastro.length; i++) {

				if (driver.findElement(By.id(idBotoesPadraoCadastro[i])).isDisplayed()) {
					logger.info("Botão padrão " + idBotoesPadraoCadastro[i] + " localizado com sucesso!");
				} else {
					logger.info("#ALERTA# Botão não localizado " + idBotoesPadraoCadastro[i]);
					//falha("#ALERTA# Botão não localizado "+idBotoesPadraoCadastro[i], driver, nomeTeste);
				}

			}

		}

	}
	public void validaOrdenacao(WebDriver driver, String nomeTeste, String caminho, int tentativas, String[] xpathColunasResultados, String[] indOrdenacaoColuna, String[] labelColuna) throws InterruptedException, IOException {

		try {

			aguardaCarregamento(caminho, xpathColunasResultados[0], nomeTeste, tentativas, driver);

			for (int i = 0; i < xpathColunasResultados.length; i++) {
				logger.info("Testando ordenação da coluna " + driver.findElement(By.xpath(xpathColunasResultados[i])).getText() + "...");

				driver.findElement(By.xpath(xpathColunasResultados[i])).click();

				moveParaIconeHome(driver);

				for (int second = 0;; second++) {

					if (second >= tentativas)
						falha("Timeout, elemento nao localizado " + indOrdenacaoColuna, driver, nomeTeste);
					try {

						if (isElementPresentByXpath(driver, indOrdenacaoColuna[i]))
							break;
					} catch (Exception e) {
					}
					Thread.sleep(1000);
				}
				logger.info("indicador de Ordenação visualizado com sucesso!");

			}
		} catch (Exception e) {
			falha("Não foi possível efetuar a ordenação de colunas!", driver, nomeTeste);
		}

	}

	private void moveParaIconeHome(WebDriver driver) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.id("ui-id-1"));
		action.moveToElement(we).build().perform();

	}
	public void checkAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.getText();
		} catch (Exception e) {
			//exception handling
		}
	}
	public void acessaTelaResultadosPelaAba(WebDriver driver, String nomeTeste, int tentativas, String xpathAbaPesquisa, String xpathAbaResultados, String xPathCarregaPesquisa, String xpathCarregaResultadoPesquisa) throws InterruptedException, IOException {

		try {
			Thread.sleep(1000);
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			aguardaCarregamento(nomeTeste, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
			logger.info("Acessando a tela de Resultados através da Aba...");
			aguardaCarregamento(nomeTeste, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
			driver.findElement(By.xpath(xpathAbaResultados)).click();
			moveParaIconeHome(driver);
			aguardaCarregamento(nomeTeste, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);

			logger.info("Tela de Resultados acessa com sucesso pela aba, retornando para a tela de Pesquisa...");
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			aguardaCarregamento(nomeTeste, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);
			driver.findElement(By.xpath(xpathAbaPesquisa)).click();
			moveParaIconeHome(driver);
			aguardaCarregamento(nomeTeste, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

			logger.info("Retornada a tela de pesquisa com sucesso!");

		} catch (Exception e) {
			falha("Falha ao acessar a tela", driver, nomeTeste);

		}

	}
	public void validaOrdenacao(WebDriver driver, String nomeTeste, String caminho, int tentativas, boolean possuiTabelasNaTelaCadastro, String[] xpathColunaOrdenacao, String[] indOrdenacaoColuna, String[] labelColuna) throws IOException {
		// TODO Auto-generated method stub

		if (possuiTabelasNaTelaCadastro == true) {

			try {

				aguardaCarregamento(caminho, xpathColunaOrdenacao[0], nomeTeste, tentativas, driver);

				for (int i = 0; i < xpathColunaOrdenacao.length; i++) {
					logger.info("Testando ordenação da coluna " + driver.findElement(By.xpath(xpathColunaOrdenacao[i])).getText() + "...");

					driver.findElement(By.xpath(xpathColunaOrdenacao[i])).click();

					moveParaIconeHome(driver);

					for (int second = 0;; second++) {

						if (second >= tentativas)
							falha("Timeout, elemento nao localizado " + indOrdenacaoColuna, driver, nomeTeste);
						try {

							if (isElementPresentByXpath(driver, indOrdenacaoColuna[i]))
								break;
						} catch (Exception e) {
						}
						Thread.sleep(1000);
					}
					logger.info("indicador de Ordenação visualizado com sucesso!");

				}
			} catch (Exception e) {
				falha("Não foi possível efetuar a ordenação de colunas!", driver, nomeTeste);
			}
		} else {
			logger.info("Não Existem tabelas na tela para validar!");
		}
	}
	public void verificaToolTipPorXpath(WebDriver driver, String nomeTeste, int tentativas, String caminho, String[] xpathColunaTabelaCadastro, String[] tooltipEsperadoColunaTabelaCadastro, String[] possuiTooltipColunaTabelaCadastro) throws IOException {

		logger.info("Validando tooltip...");

		for (int i = 0; i < xpathColunaTabelaCadastro.length; i++) {
			if (Boolean.parseBoolean(possuiTooltipColunaTabelaCadastro[i]) == true) {
				boolean toolTipVazia = driver.findElement(By.xpath(xpathColunaTabelaCadastro[i])).getAttribute("original-title").isEmpty();
				String tooltipObtido = driver.findElement(By.xpath(xpathColunaTabelaCadastro[i])).getAttribute("original-title");

				if (toolTipVazia == false) {
					if (tooltipObtido.contentEquals(tooltipEsperadoColunaTabelaCadastro[i])) {
						logger.info("#OK# Tooltip esperado: " + tooltipEsperadoColunaTabelaCadastro[i] + ", tooltip obtido: " + driver.findElement(By.xpath(xpathColunaTabelaCadastro[i])).getAttribute("original-title"));
					} else {

						logger.info("#ALERTA# Tooltip esperado: " + tooltipEsperadoColunaTabelaCadastro[i] + ", tooltip obtido: " + driver.findElement(By.xpath(xpathColunaTabelaCadastro[i])).getAttribute("original-title"));
						String obtido = driver.findElement(By.xpath(xpathColunaTabelaCadastro[i])).getAttribute("original-title");
						// TODO MUDAR PRA FALHA!
						//falha("#ALERTA# Tooltip esperado: " + toolTipEsperado[i] + ", tooltip obtido: " + obtido, driver, nomeTeste);
					}
				} else {
					logger.info("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + xpathColunaTabelaCadastro[i]);
					falha("#ALERTA# Tooltip Tooltip 'i' não localizado no campo " + xpathColunaTabelaCadastro[i], driver, nomeTeste);
				}
			} else {
				logger.info("Elemento " + xpathColunaTabelaCadastro[i] + " não possui tooltip 'i' para verificar");
			}

		}
	}
	public void validaLabelsDaAba(String nomeTeste, WebDriver driver, int tentativas, String[] labels, String[] idLabelsAba, boolean possuiLabel) throws IOException {
		if (possuiLabel == true) {
			validaLabels(driver, nomeTeste, tentativas, labels, idLabelsAba);
		}

	}
	public void validaTootipDaAba(String nomeTeste, WebDriver driver, int tentativas, String[] idLabelsAba, String[] PossuiTooltipNaLabelAba, String[] toolTipEsperadoAba) throws IOException {

		validaTootip(driver, nomeTeste, tentativas, idLabelsAba, PossuiTooltipNaLabelAba, toolTipEsperadoAba);

	}
	public void preencheCamposDeApuracao(WebDriver driver, String caminho, int tentativas, String nomeTeste, String[] idCamposTelaApuracao, String[] valorCamposTelaApuracao) throws IOException, InterruptedException {

		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
		aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCamposTelaApuracao[0]);
		for (int i = 0; i < idCamposTelaApuracao.length; i++) {
			aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCamposTelaApuracao[i]);
			logger.info("Preenchendo o campo " + idCamposTelaApuracao[i] + " com o valor: " + valorCamposTelaApuracao[i]);
			driver.findElement(By.id(idCamposTelaApuracao[i])).click();
			driver.findElement(By.id(idCamposTelaApuracao[i])).sendKeys(valorCamposTelaApuracao[i]);
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCamposTelaApuracao[i]);
			Thread.sleep(500);

		}

	}
	public void marcaCheckBox(WebDriver driver, String caminho, int tentativas, String nomeTeste, String[] idCampo, String[] valorCampo) throws IOException, InterruptedException {
		aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
		for (int i = 0; i < idCampo.length; i++) {
			boolean valorCampoBoolean = Boolean.parseBoolean(valorCampo[i]);
			aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCampo[i]);
			if (valorCampoBoolean == true) {
				aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
				aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCampo[i]);
				logger.info("Marcando o campo " + idCampo[i]);
				driver.findElement(By.id(idCampo[i])).click();
				aguardaCarregamentoPorId(driver, tentativas, nomeTeste, idCampo[i]);
				aguardaProcessamentoDesaparecer(driver, tentativas, nomeTeste);
			}

		}

	}
}
