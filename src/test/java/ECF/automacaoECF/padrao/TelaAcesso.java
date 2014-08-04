package ECF.automacaoECF.padrao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;

public class TelaAcesso extends CasoDeTesteBasico {

	org.apache.log4j.Logger logger = Logger.getLogger(TelaAcesso.class.getName());
	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	public String xpathModulo = new RecebeParametros().xpathModulo;
	int tentativas = Integer.parseInt(numerotentativas);

	public static String xpathHome = "/html/body/div[3]/div[2]/div/ul/li/a/span";
	public long duracaoTeste;

	@SuppressWarnings("unused")
	@Test
	public void tesTela() throws Throwable {
		long inicio = System.currentTimeMillis();

		// ////////1 ACESSO E TITULOS
		String nomeTeste = properties.getProperty("nomeTeste");
		String qtdeMenu = properties.getProperty("qtdeMenu");
		int qtdeMenuInt = Integer.parseInt(qtdeMenu);
		String xpathMenu1 = properties.getProperty("xpathMenu1");
		String xpathMenu2 = properties.getProperty("xpathMenu2");
		String xpathMenu3 = properties.getProperty("xpathMenu3");
		String xpathMenu4 = properties.getProperty("xpathMenu4");
		String labelMenu1 = properties.getProperty("labelMenu1");
		String labelMenu2 = properties.getProperty("labelMenu2");
		String labelMenu3 = properties.getProperty("labelMenu3");
		String labelMenu4 = properties.getProperty("labelMenu4");
		String labelTela = properties.getProperty("labelTela");

		String xpathTela = properties.getProperty("xpathTela");
		String caminho = properties.getProperty("caminho");
		String categoria = properties.getProperty("categoria");
		String idAba = properties.getProperty("idAba");
		String labelAba = properties.getProperty("labelAba");

		String xPathCarregaPesquisa = properties.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = properties.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = properties.getProperty("xpathCarregaRegistro");

		String idBotaoExecutarConsulta = properties.getProperty("idBotaoExecutarConsulta");

		String xpathAbaPesquisa = properties.getProperty("xpathAbaPesquisa");
		String xpathAbaResultados = properties.getProperty("xpathAbaResultados");
		String xpathAbaCadastro = properties.getProperty("xpathAbaCadastro");

		String abaPesquisa = properties.getProperty("abaPesquisa");
		boolean abaPesquisaBoolean = Boolean.parseBoolean(abaPesquisa);

		String abaResultadosPesquisa = properties.getProperty("abaResultadosPesquisa");
		boolean abaResultadosPesquisaBoolean = Boolean.parseBoolean(abaResultadosPesquisa);

		String abaRegistro = properties.getProperty("abaRegistro");
		boolean abaRegistroBoolean = Boolean.parseBoolean(abaRegistro);

		VerificacoesDeTela automacao = new VerificacoesDeTela();

		// ACESSA TELA
		automacao.informaTeste(0, caminho, nomeTeste);
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick(driver, qtdeMenuInt, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenuInt);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		if (abaPesquisaBoolean == true) {
			// ACESSO A TELA PESQUISA
			automacao.informaTeste(1, caminho, nomeTeste);
			automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
			if (automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho) == true)
				automacao.falha("A tela apresentou mensagem de erro", driver, nomeTeste);
		}

		if (abaResultadosPesquisaBoolean == true) {
			// TELA RESULTADO PESQUISA
			automacao.informaTeste(2, caminho, nomeTeste);
			Thread.sleep(500);
			driver.findElement(By.id(idBotaoExecutarConsulta)).click();
			automacao.aguardaCarregamento(caminho, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);
			if (automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho) == true)
				automacao.falha("A tela apresentou mensagem de erro", driver, nomeTeste);

		}

		if (abaRegistroBoolean == true) {
			// TELA REGISTRO
			
			automacao.informaTeste(3, caminho, nomeTeste);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAbaCadastro, nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathCarregaRegistro, nomeTeste, tentativas, driver);
			if (automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho) == true)
				automacao.falha("A tela apresentou mensagem de erro", driver, nomeTeste);

		}
		duracaoTeste = System.currentTimeMillis() - inicio;
		automacao.informaTerminoDoTeste(nomeTeste, categoria, duracaoTeste);
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}

	public void executaTeste() throws Throwable {
		setup();
		tesTela();
		tearDown();

	}

}
