package ECF.automacaoECF.acessoTela;



import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ECF.automacaoECF.padrao.CasoDeTesteBasico;
import ECF.automacaoECF.padrao.RecebeParametros;
import ECF.automacaoECF.padrao.VerificacoesDeTela;



public class TelaFeriadoAcesso extends CasoDeTesteBasico {

	org.apache.log4j.Logger logger = Logger.getLogger(TelaFeriadoAcesso.class.getName());
	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	int tentativas = Integer.parseInt(numerotentativas);
	public static String xpathHome = "/html/body/div[3]/div[2]/div/ul/li/a/span";
	public long duracaoTeste;

	@Test
	public void testelaFeriadoAcesso() throws Throwable {
		long inicio = System.currentTimeMillis();

		// ////////1 ACESSO E TITULOS
		String nomeTeste = properties.getProperty("nomeTeste");
		String xpathMenu1 = properties.getProperty("xpathMenu1");
		String xpathMenu2 = properties.getProperty("xpathMenu2");
		String xpathTela = properties.getProperty("xpathTela");
		String caminho = properties.getProperty("caminho");
		String categoria = properties.getProperty("categoria");
		String idAba = properties.getProperty("idAba");
		String labelAba = properties.getProperty("labelAba");

		String xPathCarregaPesquisa = properties.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = properties.getProperty("xpathCarregaResultadoPesquisa");
		String idAbaRegistro = properties.getProperty("idAbaRegistro");
		String xpathCarregaRegistro = properties.getProperty("xpathCarregaRegistro");

		String idBotaoExecutarConsulta = properties.getProperty("idBotaoExecutarConsulta");

		String xpathAbaPesquisa = properties.getProperty("xpathAbaPesquisa");
		String xpathAbaResultados = properties.getProperty("xpathAbaResultados");
		String xpathAbaCadastro = properties.getProperty("xpathAbaCadastro");

		VerificacoesDeTela automacao = new VerificacoesDeTela();

		// ACESSA TELA
		automacao.informaTeste(0, caminho, nomeTeste);
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTela(driver, tentativas, xpathMenu1, xpathMenu2, xpathTela, nomeTeste);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		// ACESSO A TELA PESQUISA
		automacao.informaTeste(1, caminho, nomeTeste);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		// TELA RESULTADO PESQUISA
		automacao.informaTeste(2, caminho, nomeTeste);
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();
		automacao.aguardaCarregamento(caminho, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);

		// TELA REGISTRO
		automacao.informaTeste(3, caminho, nomeTeste);
		automacao.acessaAbaPorId(driver, tentativas, idAbaRegistro, nomeTeste);
		automacao.aguardaCarregamento(caminho, xpathCarregaRegistro, nomeTeste, tentativas, driver);

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
		testelaFeriadoAcesso();
		tearDown();

	}

}
