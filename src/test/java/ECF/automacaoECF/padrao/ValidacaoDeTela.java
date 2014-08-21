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

		////ABAS
		boolean possuiAbas = Boolean.parseBoolean(properties.getProperty("possuiAbas"));
		String xpathAbas = properties.getProperty("xpathAbas");
		String labelAbas = properties.getProperty("labelAbas");
		int qtdeAbasDaTelaCadastro = xpathAbas.length();

		String[] xpathAbasDaTelaCadastro = xpathAbas.split(",");
		String[] labelAbasDaTelaCadastro = labelAbas.split(",");

		//ABA 1
		String labelsAba1 = properties.getProperty("labelsAba1");
		String idLabelsAba1 = properties.getProperty("idLabelsAba1");
		String idCaixasAba1 = properties.getProperty("idCaixasAba1");
		String idBotoesAba1 = properties.getProperty("idBotoesAba1");

		String[] labelsAba1Cadastro = labelsAba1.split(",");
		String[] idLabelsAba1Cadastro = idLabelsAba1.split(",");
		String[] idCaixasAba1Cadastro = idCaixasAba1.split(",");
		String[] idBotoesAba1Cadastro = idBotoesAba1.split(",");

		//ABA 2
		String labelsAba2 = properties.getProperty("labelsAba2");
		String idLabelsAba2 = properties.getProperty("idLabelsAba2");
		String idCaixasAba2 = properties.getProperty("idCaixasAba2");
		String idBotoesAba2 = properties.getProperty("idBotoesAba2");

		String[] labelsAba2Cadastro = labelsAba2.split(",");
		String[] idLabelsAba2Cadastro = idLabelsAba2.split(",");
		String[] idCaixasAba2Cadastro = idCaixasAba2.split(",");
		String[] idBotoesAba2Cadastro = idBotoesAba2.split(",");

		//ABA 3
		String labelsAba3 = properties.getProperty("labelsAba3");
		String idLabelsAba3 = properties.getProperty("idLabelsAba3");
		String idCaixasAba3 = properties.getProperty("idCaixasAba3");
		String idBotoesAba3 = properties.getProperty("idBotoesAba3");

		String[] labelsAba3Cadastro = labelsAba3.split(",");
		String[] idLabelsAba3Cadastro = idLabelsAba3.split(",");
		String[] idCaixasAba3Cadastro = idCaixasAba3.split(",");
		String[] idBotoesAba3Cadastro = idBotoesAba3.split(",");

		//ABA 4
		String labelsAba4 = properties.getProperty("labelsAba4");
		String idLabelsAba4 = properties.getProperty("idLabelsAba4");
		String idCaixasAba4 = properties.getProperty("idCaixasAba4");
		String idBotoesAba4 = properties.getProperty("idBotoesAba4");

		String[] labelsAba4Cadastro = labelsAba4.split(",");
		String[] idLabelsAba4Cadastro = idLabelsAba4.split(",");
		String[] idCaixasAba4Cadastro = idCaixasAba4.split(",");
		String[] idBotoesAba4Cadastro = idBotoesAba4.split(",");

		//ABA 5
		String labelsAba5 = properties.getProperty("labelsAba5");
		String idLabelsAba5 = properties.getProperty("idLabelsAba5");
		String idCaixasAba5 = properties.getProperty("idCaixasAba5");
		String idBotoesAba5 = properties.getProperty("idBotoesAba5");

		String[] labelsAba5Cadastro = labelsAba5.split(",");
		String[] idLabelsAba5Cadastro = idLabelsAba5.split(",");
		String[] idCaixasAba5Cadastro = idCaixasAba5.split(",");
		String[] idBotoesAba5Cadastro = idBotoesAba5.split(",");

		//ABA 6
		String labelsAba6 = properties.getProperty("labelsAba6");
		String idLabelsAba6 = properties.getProperty("idLabelsAba6");
		String idCaixasAba6 = properties.getProperty("idCaixasAba6");
		String idBotoesAba6 = properties.getProperty("idBotoesAba6");

		String[] labelsAba6Cadastro = labelsAba6.split(",");
		String[] idLabelsAba6Cadastro = idLabelsAba6.split(",");
		String[] idCaixasAba6Cadastro = idCaixasAba6.split(",");
		String[] idBotoesAba6Cadastro = idBotoesAba6.split(",");

		//ABA 7
		String labelsAba7 = properties.getProperty("labelsAba7");
		String idLabelsAba7 = properties.getProperty("idLabelsAba7");
		String idCaixasAba7 = properties.getProperty("idCaixasAba7");
		String idBotoesAba7 = properties.getProperty("idBotoesAba7");

		String[] labelsAba7Cadastro = labelsAba7.split(",");
		String[] idLabelsAba7Cadastro = idLabelsAba7.split(",");
		String[] idCaixasAba7Cadastro = idCaixasAba7.split(",");
		String[] idBotoesAba7Cadastro = idBotoesAba7.split(",");

		//ABA 8
		String labelsAba8 = properties.getProperty("labelsAba8");
		String idLabelsAba8 = properties.getProperty("idLabelsAba8");
		String idCaixasAba8 = properties.getProperty("idCaixasAba8");
		String idBotoesAba8 = properties.getProperty("idBotoesAba8");

		String[] labelsAba8Cadastro = labelsAba8.split(",");
		String[] idLabelsAba8Cadastro = idLabelsAba8.split(",");
		String[] idCaixasAba8Cadastro = idCaixasAba8.split(",");
		String[] idBotoesAba8Cadastro = idBotoesAba8.split(",");

		//ABA 9
		String labelsAba9 = properties.getProperty("labelsAba9");
		String idLabelsAba9 = properties.getProperty("idLabelsAba9");
		String idCaixasAba9 = properties.getProperty("idCaixasAba9");
		String idBotoesAba9 = properties.getProperty("idBotoesAba9");

		String[] labelsAba9Cadastro = labelsAba9.split(",");
		String[] idLabelsAba9Cadastro = idLabelsAba9.split(",");
		String[] idCaixasAba9Cadastro = idCaixasAba9.split(",");
		String[] idBotoesAba9Cadastro = idBotoesAba9.split(",");

		//ABA 10
		String labelsAba10 = properties.getProperty("labelsAba10");
		String idLabelsAba10 = properties.getProperty("idLabelsAba10");
		String idCaixasAba10 = properties.getProperty("idCaixasAba10");
		String idBotoesAba10 = properties.getProperty("idBotoesAba10");

		String[] labelsAba10Cadastro = labelsAba10.split(",");
		String[] idLabelsAba10Cadastro = idLabelsAba10.split(",");
		String[] idCaixasAba10Cadastro = idCaixasAba10.split(",");
		String[] idBotoesAba10Cadastro = idBotoesAba10.split(",");

		int countAbas = 0;

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
			//automacao.verificaSeExiteCamposNaoPrevistos(driver,nomeTeste,caminho,tentativas,verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);

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
			automacao.verificaAbasDaTelaCadastro(driver, nomeTeste, caminho, tentativas, possuiAbas, xpathAbasDaTelaCadastro, labelAbasDaTelaCadastro);
			automacao.acessaAbas(driver, nomeTeste, caminho, tentativas, possuiAbas, xpathAbasDaTelaCadastro);

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
