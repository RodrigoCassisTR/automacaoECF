package ECF.automacaoECF.padrao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

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

	@Test
	public void testeTela() throws Throwable {
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

		String idBotaoSalvarCadastro = properties.getProperty("idBotaoSalvarCadastro");
		String idBotaoNovoCadastro = properties.getProperty("idBotaoNovoCadastro");
		String idBotaoCriarCopiaCadastro = properties.getProperty("idBotaoCriarCopiaCadastro");
		String idBotaoEditarCadastro = properties.getProperty("idBotaoEditarCadastro");
		String idBotaoExcluirCadastro = properties.getProperty("idBotaoExcluirCadastro");
		String idBotaoCancelarCadastro = properties.getProperty("idBotaoCancelarCadastro");
		String[] idBotoesCadastro = {idBotaoSalvarCadastro, idBotaoNovoCadastro, idBotaoCriarCopiaCadastro, idBotaoEditarCadastro, idBotaoExcluirCadastro, idBotaoCancelarCadastro};

		//CAMPOS
		boolean possuiBotoesNaTela = Boolean.parseBoolean(properties.getProperty("possuiBotoesNaTela"));
		boolean possuiTitulosNaTela = Boolean.parseBoolean(properties.getProperty("possuiTitulosNaTela"));
		boolean possuiLabelsNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiLabelsNaTelaCadastro"));
		boolean possuiCaixasNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiCaixasNaTelaCadastro"));
		boolean possuiTitulosNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiTitulosNaTelaCadastro"));
		boolean possuiBotoesNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiBotoesNaTelaCadastro"));

		String[] labelCampo = properties.getProperty("labelCampo").split(",");
		String[] idLabelCampo = properties.getProperty("idLabelCampo").split(",");
		String[] possuiTootip = properties.getProperty("possuiTootip").split(",");
		String[] toolTipEsperado = properties.getProperty("toolTipEsperado").split(",");

		String[] idCaixaCampo = properties.getProperty("idCaixaCampo").split(",");
		String[] possuiValue = properties.getProperty("possuiValue").split(",");
		String[] vlrEsperadoCaixaCampo = properties.getProperty("vlrEsperadoCaixaCampo").split(",");

		String[] labelTitulos = properties.getProperty("labelTitulos").split(",");
		String[] xpathTitulos = properties.getProperty("xpathTitulos").split(",");

		String[] idBotoesTelaCadastro = properties.getProperty("idBotoesTelaCadastro").split(",");
		String[] valueBotoesTelaCadastro = properties.getProperty("valueBotoesTelaCadastro").split(",");

		String xpathAbaCadastro = properties.getProperty("xpathAbaCadastro");

		////ABAS
		boolean possuiAbas = Boolean.parseBoolean(properties.getProperty("possuiAbas"));
		String xpathAbas = properties.getProperty("xpathAbas");
		String labelAbas = properties.getProperty("labelAbas");

		String[] xpathAbasDaTelaCadastro = xpathAbas.split(",");
		String[] labelAbasDaTelaCadastro = labelAbas.split(",");

		//ABA
		String[] labelsAba = {properties.getProperty("labelsAba1"), properties.getProperty("labelsAba2"), properties.getProperty("labelsAba3"), properties.getProperty("labelsAba4"), properties.getProperty("labelsAba5"), properties.getProperty("labelsAba6"), properties.getProperty("labelsAba7"), properties.getProperty("labelsAba8"), properties.getProperty("labelsAba9"), properties.getProperty("labelsAba10")};
		String[] idLabelsAba = {properties.getProperty("idLabelsAba1"), properties.getProperty("idLabelsAba2"), properties.getProperty("idLabelsAba3"), properties.getProperty("idLabelsAba4"), properties.getProperty("idLabelsAba5"), properties.getProperty("idLabelsAba6"), properties.getProperty("idLabelsAba7"), properties.getProperty("idLabelsAba8"), properties.getProperty("idLabelsAba9"), properties.getProperty("idLabelsAba10")};
		String[] idCaixasAba = {properties.getProperty("idCaixasAba1"), properties.getProperty("idCaixasAba2"), properties.getProperty("idCaixasAba3"), properties.getProperty("idCaixasAba4"), properties.getProperty("idCaixasAba5"), properties.getProperty("idCaixasAba6"), properties.getProperty("idCaixasAba7"), properties.getProperty("idCaixasAba8"), properties.getProperty("idCaixasAba9"), properties.getProperty("idCaixasAba10")};
		String[] idBotoesAba = {properties.getProperty("idBotoesAba1"), properties.getProperty("idBotoesAba2"), properties.getProperty("idBotoesAba3"), properties.getProperty("idBotoesAba4"), properties.getProperty("idBotoesAba5"), properties.getProperty("idBotoesAba6"), properties.getProperty("idBotoesAba7"), properties.getProperty("idBotoesAba8"), properties.getProperty("idBotoesAba9"), properties.getProperty("idBotoesAba10")};
		String[] possuiBotoesNaAba = {properties.getProperty("possuiBotoesNaAba1"), properties.getProperty("possuiBotoesNaAba2"), properties.getProperty("possuiBotoesNaAba3"), properties.getProperty("possuiBotoesNaAba4"), properties.getProperty("possuiBotoesNaAba5"), properties.getProperty("possuiBotoesNaAba6"), properties.getProperty("possuiBotoesNaAba7"), properties.getProperty("possuiBotoesNaAba8"), properties.getProperty("possuiBotoesNaAba9"), properties.getProperty("possuiBotoesNaAba10")};
		String[] possuiTitulosNaAba = {properties.getProperty("possuiTitulosNaAba1"), properties.getProperty("possuiTitulosNaAba2"), properties.getProperty("possuiTitulosNaAba3"), properties.getProperty("possuiTitulosNaAba4"), properties.getProperty("possuiTitulosNaAba5"), properties.getProperty("possuiTitulosNaAba6"), properties.getProperty("possuiTitulosNaAba7"), properties.getProperty("possuiTitulosNaAba8"), properties.getProperty("possuiTitulosNaAba9"), properties.getProperty("possuiTitulosNaAba10")};

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
			automacao.validaAbaDaTela(driver, nomeTeste, tentativas, idAba, labelAba);
			automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDePesquisa(driver, nomeTeste, caminho, tentativas, verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa, idBotaoExecutarConsulta, idBotaoLimpar);
			//automacao.verificaSeExiteCamposNaoPrevistos(driver,nomeTeste,caminho,tentativas,verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);
		}

		if (abaResultadosPesquisa == true) {
			// TELA RESULTADO PESQUISA
			automacao.informaTeste(2, caminho, nomeTeste);
			automacao.verificaTamanhoDeArray(driver, nomeTeste, xpathColunasResultados, labelsColunasResultados);
			automacao.executaConsulta(driver, nomeTeste, idBotaoExecutarConsulta);
			automacao.aguardaCarregamento(caminho, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDeResultados(driver, nomeTeste, caminho, tentativas, verificaCamposResultados, xpathColunasResultados, labelsColunasResultados, idBotoesResultados);
		}

		if (abaRegistro == true) {
			// TELA CADASTROS
			automacao.informaTeste(3, caminho, nomeTeste);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAbaCadastro, nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathCarregaRegistro, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaPresencaCamposPorId(nomeTeste, driver, qtdeMenu, idLabelCampo, idCaixaCampo, idBotoesTelaCadastro,possuiLabelsNaTelaCadastro,possuiCaixasNaTelaCadastro,possuiBotoesNaTelaCadastro);
			
			
			
			automacao.validaTitulosDaTelaPorXpath(nomeTeste, driver, tentativas, labelTitulos, xpathTitulos);
			automacao.validaLabelsDaTelCadastro(nomeTeste, driver, tentativas, labelCampo, idLabelCampo, possuiTootip, toolTipEsperado);
			automacao.validaElementosInput(nomeTeste, driver, tentativas, idCaixaCampo, possuiValue, vlrEsperadoCaixaCampo);
			automacao.validaBotoesDaTelaCadastro(nomeTeste, driver, tentativas, idBotoesTelaCadastro, valueBotoesTelaCadastro,possuiBotoesNaTelaCadastro);

			if (possuiAbas == true) {
				//				automacao.verificaAbasDaTelaCadastro(driver, nomeTeste, caminho, tentativas, possuiAbas, xpathAbasDaTelaCadastro, labelAbasDaTelaCadastro);
				//				automacao.validaAbasDaTelaCadastro(driver, nomeTeste, caminho, tentativas, possuiAbas, xpathAbasDaTelaCadastro, labelsAba, idLabelsAba, idCaixasAba, idBotoesAba, possuiBotoesNaAba, possuiTitulosNaAba);
			}
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
		testeTela();
		tearDown();

	}

}
