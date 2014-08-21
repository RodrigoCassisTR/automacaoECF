package ECF.automacaoECF.padrao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

public class ProcessoDeCalculoLucroReal extends CasoDeTesteBasico {

	org.apache.log4j.Logger logger = Logger.getLogger(ProcessoDeCalculoLucroReal.class.getName());
	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	public String xpathModulo = new RecebeParametros().xpathModulo;
	public String caminhoIntegrador = new RecebeParametros().caminhoIntegrador;
	public String nomeDoServicoIntegrador = new RecebeParametros().nomeDoServicoIntegrador;

	int tentativas = Integer.parseInt(numerotentativas);
	protected Properties propertiesIntegrador;

	public static String xpathHome = "/html/body/div[3]/div[2]/div/ul/li/a/span";
	public long duracaoTeste;

	@Test
	public void integraRegistro() throws IOException, InterruptedException {
		long inicio = System.currentTimeMillis();

		////// INTEGRACAO INFO
		String nomeTeste = properties.getProperty("nomeTeste");
		String categoria = properties.getProperty("categoria");
		String nomeIntegracao = properties.getProperty("nomeIntegracao");

		//CARREGANDO AS INFORMAÇÕES NO PROPERTIES DE TELA
		String nomeClasseTela = properties.getProperty("nomeClasseTela");
		String filename2 = "./src/test/resources/" + nomeClasseTela + ".properties";
		Properties propertiesTela = new Properties();
		FileInputStream file2 = new FileInputStream(filename2);
		propertiesTela.load(file2);

		////////PROPERTIES DE TELA 
		
		//////////ACESSO E TITULOS
		String caminho = propertiesTela.getProperty("caminho");
		String idAba = propertiesTela.getProperty("idAba");
		String labelAba = propertiesTela.getProperty("labelAba");

		//////////ACESSO A TELA
		int qtdeMenu = Integer.parseInt(propertiesTela.getProperty("qtdeMenu"));
		String labelMenu1 = propertiesTela.getProperty("labelMenu1");
		String xpathMenu1 = propertiesTela.getProperty("xpathMenu1");
		String labelMenu2 = propertiesTela.getProperty("labelMenu2");
		String xpathMenu2 = propertiesTela.getProperty("xpathMenu2");
		String labelMenu3 = propertiesTela.getProperty("labelMenu3");
		String xpathMenu3 = propertiesTela.getProperty("xpathMenu3");
		String labelMenu4 = propertiesTela.getProperty("labelMenu4");
		String xpathMenu4 = propertiesTela.getProperty("xpathMenu4");
		String labelTela = propertiesTela.getProperty("labelTela");
		String xpathTela = propertiesTela.getProperty("xpathTela");
		String xpathSemResultados = propertiesTela.getProperty("xpathSemResultados");

		//////////VALIDACOES PADRAO
		String xpathAbaCadastro = propertiesTela.getProperty("xpathAbaCadastro");
		String xPathCarregaPesquisa = propertiesTela.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = propertiesTela.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = propertiesTela.getProperty("xpathCarregaRegistro");

		VerificacoesDeIntegracao integracao = new VerificacoesDeIntegracao();
		VerificacoesDeTela automacao = new VerificacoesDeTela();

		//ROTEIRO DE CALCULO IRPJ
		automacao.informaTeste(0, "-", nomeTeste);
		logger.info("----------------------------------------------------------");

		logger.info("----------------------------------------------------------");

		logger.info("----------------------------------------------------------");

		//VERIFICA SE O REGISTRO A SER INTEGRADO JA EXISTE, SE EXISTE EXCLUI
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		duracaoTeste = System.currentTimeMillis() - inicio;
		automacao.informaTerminoDoTeste(nomeTeste, categoria, duracaoTeste);
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
