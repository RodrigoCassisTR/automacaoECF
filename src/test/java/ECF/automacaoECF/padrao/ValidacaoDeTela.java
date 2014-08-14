package ECF.automacaoECF.padrao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;

public class ValidacaoDeTela extends CasoDeTesteBasico {

	org.apache.log4j.Logger logger = Logger.getLogger(ValidacaoDeTela.class.getName());
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
		String categoria = properties.getProperty("categoria");
		String caminho = properties.getProperty("caminho");
		String idAba = properties.getProperty("idAba");
		String labelAba = properties.getProperty("labelAba");

		//////////ACESSO A TELA

		int qtdeMenu = Integer.parseInt(properties.getProperty("qtdeMenu"));
		String labelMenu1 = properties.getProperty("labelMenu1");
		String xpathMenu1 = properties.getProperty("xpathMenu1");
		String labelMenu2 = properties.getProperty("labelMenu2");
		String xpathMenu2 = properties.getProperty("xpathMenu2");
		String labelMenu3 = properties.getProperty("labelMenu3");
		String xpathMenu3 = properties.getProperty("xpathMenu3");
		String labelMenu4 = properties.getProperty("labelMenu4");
		String xpathMenu4 = properties.getProperty("xpathMenu4");
		String labelTela = properties.getProperty("labelTela");
		String xpathTela = properties.getProperty("xpathTela");
		boolean abaPesquisa = Boolean.parseBoolean(properties.getProperty("abaPesquisa"));
		boolean abaResultadosPesquisa = Boolean.parseBoolean(properties.getProperty("abaResultadosPesquisa"));
		boolean abaRegistro = Boolean.parseBoolean(properties.getProperty("abaRegistro"));

		//////////VALIDACOES PADRAO
		String xPathCarregaPesquisa = properties.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = properties.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = properties.getProperty("xpathCarregaRegistro");

		////TELA PESQUISA
		boolean verificaCamposPesquisa = Boolean.parseBoolean(properties.getProperty("verificaCamposPesquisa"));

		//CAMPOS
		String labelCampoPesquisa = properties.getProperty("labelCampoPesquisa");
		String idLabelPesquisa = properties.getProperty("idLabelPesquisa");
		String idCaixaPesquisa = properties.getProperty("idCaixaPesquisa");

		String[] labelsTelaPesquisa = labelCampoPesquisa.split(",");
		String[] idLabelsTelaPesquisa = idLabelPesquisa.split(",");
		String[] idCamposTelaPesquisa = idCaixaPesquisa.split(",");

		//BOTOES
		String idBotaoExecutarConsulta = properties.getProperty("idBotaoExecutarConsulta");
		String idBotaoLimpar = properties.getProperty("idBotaoLimpar");

		////TELA RESULTADOS
		boolean verificaCamposResultados = Boolean.parseBoolean(properties.getProperty("verificaCamposResultados"));

		String idBotaoConsultar = properties.getProperty("idBotaoConsultar");
		String idBotaoNovo = properties.getProperty("idBotaoNovo");
		String idBotaoCriarCopia = properties.getProperty("idBotaoCriarCopia");
		String idBotaoEditar = properties.getProperty("idBotaoEditar");
		String idBotaoExcluir = properties.getProperty("idBotaoExcluir");
		String idBotaoExportar = properties.getProperty("idBotaoExportar");

		String[] idBotoesResultados = {idBotaoConsultar, idBotaoNovo, idBotaoCriarCopia, idBotaoEditar, idBotaoExcluir, idBotaoExportar};

		//CAMPOs
		String labelColuna = properties.getProperty("labelColuna");
		String xpathColuna = properties.getProperty("xpathColuna");

		String[] labelsColunasResultados = labelColuna.split(",");
		String[] xpathColunasResultados = xpathColuna.split(",");

		////TELA CADASTRO
		boolean verificaCamposCadastro = Boolean.parseBoolean(properties.getProperty("verificaCamposCadastro"));

		//CAMPOS
		String idLabelCampo = properties.getProperty("idLabelCampo");
		String idCaixaCampo = properties.getProperty("idCaixaCampo");
		String labelCampo = properties.getProperty("labelCampo");

		String[] labelsCadastro = labelCampo.split(",");
		String[] idLabelsCadastro = idLabelCampo.split(",");
		String[] idCaixasCadastro = idCaixaCampo.split(",");

		String xpathAbaPesquisa = properties.getProperty("xpathAbaPesquisa");
		String xpathAbaResultados = properties.getProperty("xpathAbaResultados");
		String xpathAbaCadastro = properties.getProperty("xpathAbaCadastro");

		VerificacoesDeTela automacao = new VerificacoesDeTela();

		// ACESSA TELA
		automacao.informaTeste(0, caminho, nomeTeste);
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		if (abaPesquisa == true) {
			// ACESSO A TELA PESQUISA
			automacao.informaTeste(1, caminho, nomeTeste);
			automacao.verificaTamanhoDeArray(driver, nomeTeste, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);
			automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDePesquisa(driver, nomeTeste, caminho, tentativas, verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);
			automacao.verificaSeExiteCamposNaoPrevistos(driver,nomeTeste,caminho,tentativas,verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);
			
		}

		if (abaResultadosPesquisa == true) {
			// TELA RESULTADO PESQUISA
			automacao.informaTeste(2, caminho, nomeTeste);
			automacao.verificaTamanhoDeArray(driver, nomeTeste, xpathColunasResultados, labelsColunasResultados);
			Thread.sleep(500);
			driver.findElement(By.id(idBotaoExecutarConsulta)).click();
			automacao.aguardaCarregamento(caminho, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDeResultados(driver, nomeTeste, caminho, tentativas, verificaCamposResultados, xpathColunasResultados, labelsColunasResultados);

		}

		if (abaRegistro == true) {
			// TELA REGISTRO
			automacao.informaTeste(3, caminho, nomeTeste);
			automacao.verificaTamanhoDeArray(driver, nomeTeste, labelsCadastro, idLabelsCadastro, idCaixasCadastro);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAbaCadastro, nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathCarregaRegistro, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDeCadastro(driver, nomeTeste, caminho, tentativas, verificaCamposCadastro, labelsCadastro, idLabelsCadastro, idCaixasCadastro);

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
