package ECF.automacaoECF.padrao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ValidacaoDeTela extends CasoDeTesteBasico {

	public org.apache.log4j.Logger logger = Logger.getLogger(ValidacaoDeTela.class.getName());
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
		String nomeTeste = getClass().getSimpleName().toUpperCase();
		String categoria = properties.getProperty("categoria");
		String caminho = properties.getProperty("caminho");
		String idAba = properties.getProperty("idAba");
		String labelAba = properties.getProperty("labelAba");

		////////// ACESSO A TELA
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

		//////////ABAS PADROES
		String xpathAbaPesquisa = properties.getProperty("xpathAbaPesquisa");
		String xpathAbaResultados = properties.getProperty("xpathAbaResultados");
		String xpathAbaCadastro = properties.getProperty("xpathAbaCadastro");

		//////////VALIDACOES PADRAO
		String xPathCarregaPesquisa = properties.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = properties.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = properties.getProperty("xpathCarregaRegistro");

		////TELA PESQUISA
		boolean verificaCamposPesquisa = Boolean.parseBoolean(properties.getProperty("verificaCamposPesquisa"));

		//CAMPOS TELA PESQUISA
		String labelCampoPesquisa = properties.getProperty("labelCampoPesquisa");
		String idLabelPesquisa = properties.getProperty("idLabelPesquisa");
		String idCaixaPesquisa = properties.getProperty("idCaixaPesquisa");
		String[] labelsTelaPesquisa = labelCampoPesquisa.split(";");
		String[] idLabelsTelaPesquisa = idLabelPesquisa.split(";");
		String[] idCamposTelaPesquisa = idCaixaPesquisa.split(";");
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

		//CAMPOS TELA RESULTADOS
		String[] labelColuna = properties.getProperty("labelColuna").split(";");
		String[] xpathColuna = properties.getProperty("xpathColuna").split(";");
		String[] indOrdenacaoColuna = properties.getProperty("indOrdenacaoColuna").split(";");
		String[] xpathColunaOrdenacao = properties.getProperty("xpathColunaOrdenacao").split(";");
		String[] idBotoesResultados = {idBotaoConsultar, idBotaoNovo, idBotaoCriarCopia, idBotaoEditar, idBotaoExcluir, idBotaoExportar};

		////////////// TELA CADASTRO
		boolean verificaCamposCadastro = Boolean.parseBoolean(properties.getProperty("verificaCamposCadastro"));
		boolean possuiBotoesPadrao = Boolean.parseBoolean(properties.getProperty("possuiBotoesPadrao"));
		String idBotaoSalvarCadastro = properties.getProperty("idBotaoSalvarCadastro");
		String idBotaoNovoCadastro = properties.getProperty("idBotaoNovoCadastro");
		String idBotaoCriarCopiaCadastro = properties.getProperty("idBotaoCriarCopiaCadastro");
		String idBotaoEditarCadastro = properties.getProperty("idBotaoEditarCadastro");
		String idBotaoExcluirCadastro = properties.getProperty("idBotaoExcluirCadastro");
		String idBotaoCancelarCadastro = properties.getProperty("idBotaoCancelarCadastro");
		String[] idBotoesPadraoCadastro = {idBotaoSalvarCadastro, idBotaoNovoCadastro, idBotaoCriarCopiaCadastro, idBotaoEditarCadastro, idBotaoExcluirCadastro, idBotaoCancelarCadastro};

		////////  CAMPOS TELA CADASTRO
		boolean possuiLabelsNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiLabelsNaTelaCadastro"));
		boolean possuiCaixasNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiCaixasNaTelaCadastro"));
		boolean possuiTitulosNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiTitulosNaTelaCadastro"));
		boolean possuiBotoesNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiBotoesNaTelaCadastro"));
		boolean possuiTabelasNaTelaCadastro = Boolean.parseBoolean(properties.getProperty("possuiTabelasNaTelaCadastro"));

		String[] labelCampo = properties.getProperty("labelCampo").split(";");
		String[] idLabelCampo = properties.getProperty("idLabelCampo").split(";");
		String[] possuiTootip = properties.getProperty("possuiTootip").split(";");
		String[] toolTipEsperado = properties.getProperty("toolTipEsperado").split(";");
		String[] idCaixaCampo = properties.getProperty("idCaixaCampo").split(";");
		String[] possuiValue = properties.getProperty("possuiValue").split(";");
		String[] vlrEsperadoCaixaCampo = properties.getProperty("vlrEsperadoCaixaCampo").split(";");
		String[] labelTitulos = properties.getProperty("labelTitulos").split(";");
		String[] xpathTitulos = properties.getProperty("xpathTitulos").split(";");
		String[] idBotoesTelaCadastro = properties.getProperty("idBotoesTelaCadastro").split(";");
		String[] valueBotoesTelaCadastro = properties.getProperty("valueBotoesTelaCadastro").split(";");

		String[] xpathColunaTabelaCadastro = properties.getProperty("xpathColunaTabelaCadastro").split(";");
		String[] tooltipEsperadoColunaTabelaCadastro = properties.getProperty("tooltipEsperadoColunaTabelaCadastro").split(";");
		String[] xpathColunaOrdenacaoTelaCadastro = properties.getProperty("xpathColunaOrdenacaoTelaCadastro").split(";");
		String[] indOrdenacaoColunaTabelaCadastro = properties.getProperty("indOrdenacaoColunaTabelaCadastro").split(";");
		String[] possuiTooltipColunaTabelaCadastro = properties.getProperty("possuiTooltipColunaTabelaCadastro").split(";");

		String[] xpathColunaOrdenacaoTabelaCadastro = properties.getProperty("xpathColunaOrdenacaoTabelaCadastro").split(";");

		////ABAS
		boolean possuiAbas = Boolean.parseBoolean(properties.getProperty("possuiAbas"));

		String[] xpathAcessoAbas = properties.getProperty("xpathAcessoAbas").split(";");
		String[] labelAcessoAbas = properties.getProperty("labelAcessoAbas").split(";");

		String[] possuiLabelsNaAba = {properties.getProperty("possuiLabelsNaAba1"), properties.getProperty("possuiLabelsNaAba2"), properties.getProperty("possuiLabelsNaAba3"), properties.getProperty("possuiLabelsNaAba4"), properties.getProperty("possuiLabelsNaAba5"), properties.getProperty("possuiLabelsNaAba6"), properties.getProperty("possuiLabelsNaAba7"), properties.getProperty("possuiLabelsNaAba8"), properties.getProperty("possuiLabelsNaAba9"), properties.getProperty("possuiLabelsNaAba10")};
		String[] possuiCaixasNaAba = {properties.getProperty("possuiCaixasNaAba1"), properties.getProperty("possuiCaixasNaAba2"), properties.getProperty("possuiCaixasNaAba3"), properties.getProperty("possuiCaixasNaAba4"), properties.getProperty("possuiCaixasNaAba5"), properties.getProperty("possuiCaixasNaAba6"), properties.getProperty("possuiCaixasNaAba7"), properties.getProperty("possuiCaixasNaAba8"), properties.getProperty("possuiCaixasNaAba9"), properties.getProperty("possuiCaixasNaAba10")};
		String[] possuiTitulosNaAba = {properties.getProperty("possuiTitulosNaAba1"), properties.getProperty("possuiTitulosNaAba2"), properties.getProperty("possuiTitulosNaAba3"), properties.getProperty("possuiTitulosNaAba4"), properties.getProperty("possuiTitulosNaAba5"), properties.getProperty("possuiTitulosNaAba6"), properties.getProperty("possuiTitulosNaAba7"), properties.getProperty("possuiTitulosNaAba8"), properties.getProperty("possuiTitulosNaAba9"), properties.getProperty("possuiTitulosNaAba10")};
		String[] possuiBotoesNaAba = {properties.getProperty("possuiBotoesNaAba1"), properties.getProperty("possuiBotoesNaAba2"), properties.getProperty("possuiBotoesNaAba3"), properties.getProperty("possuiBotoesNaAba4"), properties.getProperty("possuiBotoesNaAba5"), properties.getProperty("possuiBotoesNaAba6"), properties.getProperty("possuiBotoesNaAba7"), properties.getProperty("possuiBotoesNaAba8"), properties.getProperty("possuiBotoesNaAba9"), properties.getProperty("possuiBotoesNaAba10")};
		String[] possuiTabelaNaAba = {properties.getProperty("possuiTabelaNaAba1"), properties.getProperty("possuiTabelaNaAba2"), properties.getProperty("possuiTabelaNaAba3"), properties.getProperty("possuiTabelaNaAba4"), properties.getProperty("possuiTabelaNaAba5"), properties.getProperty("possuiTabelaNaAba6"), properties.getProperty("possuiTabelaNaAba7"), properties.getProperty("possuiTabelaNaAba8"), properties.getProperty("possuiTabelaNaAba9"), properties.getProperty("possuiTabelaNaAba10")};

		String[] idLabelsAba = {properties.getProperty("idLabelsAba1"), properties.getProperty("idLabelsAba2"), properties.getProperty("idLabelsAba3"), properties.getProperty("idLabelsAba4"), properties.getProperty("idLabelsAba5"), properties.getProperty("idLabelsAba6"), properties.getProperty("idLabelsAba7"), properties.getProperty("idLabelsAba8"), properties.getProperty("idLabelsAba9"), properties.getProperty("idLabelsAba10")};
		String[] labelsAba = {properties.getProperty("labelsAba1"), properties.getProperty("labelsAba2"), properties.getProperty("labelsAba3"), properties.getProperty("labelsAba4"), properties.getProperty("labelsAba5"), properties.getProperty("labelsAba6"), properties.getProperty("labelsAba7"), properties.getProperty("labelsAba8"), properties.getProperty("labelsAba9"), properties.getProperty("labelsAba10")};
		String[] PossuiTooltipNaLabelAba = {properties.getProperty("possuiTooltipAba1"), properties.getProperty("possuiTooltipAba2"), properties.getProperty("possuiTooltipAba3"), properties.getProperty("possuiTooltipAba4"), properties.getProperty("possuiTooltipAba5"), properties.getProperty("possuiTooltipAba6"), properties.getProperty("possuiTooltipAba7"), properties.getProperty("possuiTooltipAba8"), properties.getProperty("possuiTooltipAba9"), properties.getProperty("possuiTooltipAba10")};
		String[] toolTipEsperadoAba = {properties.getProperty("toolTipEsperadoAba1"), properties.getProperty("toolTipEsperadoAba2"), properties.getProperty("toolTipEsperadoAba3"), properties.getProperty("toolTipEsperadoAba4"), properties.getProperty("toolTipEsperadoAba5"), properties.getProperty("toolTipEsperadoAba6"), properties.getProperty("toolTipEsperadoAba7"), properties.getProperty("toolTipEsperadoAba8"), properties.getProperty("toolTipEsperadoAba9"), properties.getProperty("toolTipEsperadoAba10")};

		String[] idCaixasAba = {properties.getProperty("idCaixasAba1"), properties.getProperty("idCaixasAba2"), properties.getProperty("idCaixasAba3"), properties.getProperty("idCaixasAba4"), properties.getProperty("idCaixasAba5"), properties.getProperty("idCaixasAba6"), properties.getProperty("idCaixasAba7"), properties.getProperty("idCaixasAba8"), properties.getProperty("idCaixasAba9"), properties.getProperty("idCaixasAba10")};
		String[] possuiValueCaixasAba = {properties.getProperty("possuiValueAba1"), properties.getProperty("possuiValueAba2"), properties.getProperty("possuiValueAba3"), properties.getProperty("possuiValueAba4"), properties.getProperty("possuiValueAba5"), properties.getProperty("possuiValueAba6"), properties.getProperty("possuiValueAba7"), properties.getProperty("possuiValueAba8"), properties.getProperty("possuiValueAba9"), properties.getProperty("possuiValueAba10")};
		String[] vlrEsperadoCaixaCampoAba = {properties.getProperty("vlrEsperadoCaixaCampoAba1"), properties.getProperty("vlrEsperadoCaixaCampoAba2"), properties.getProperty("vlrEsperadoCaixaCampoAba3"), properties.getProperty("vlrEsperadoCaixaCampoAba4"), properties.getProperty("vlrEsperadoCaixaCampoAba5"), properties.getProperty("vlrEsperadoCaixaCampoAba6"), properties.getProperty("vlrEsperadoCaixaCampoAba7"), properties.getProperty("vlrEsperadoCaixaCampoAba8"), properties.getProperty("vlrEsperadoCaixaCampoAba9"), properties.getProperty("vlrEsperadoCaixaCampoAba10")};

		String[] labelTitulosAba = {properties.getProperty("labelTitulosAba1"), properties.getProperty("labelTitulosAba2"), properties.getProperty("labelTitulosAba3"), properties.getProperty("labelTitulosAba4"), properties.getProperty("labelTitulosAba5"), properties.getProperty("labelTitulosAba6"), properties.getProperty("labelTitulosAba7"), properties.getProperty("labelTitulosAba8"), properties.getProperty("labelTitulosAba9"), properties.getProperty("labelTitulosAba10")};
		String[] xpathTitulosAba = {properties.getProperty("xpathTitulosAba1"), properties.getProperty("xpathTitulosAba2"), properties.getProperty("xpathTitulosAba3"), properties.getProperty("xpathTitulosAba4"), properties.getProperty("xpathTitulosAba5"), properties.getProperty("xpathTitulosAba6"), properties.getProperty("xpathTitulosAba7"), properties.getProperty("xpathTitulosAba8"), properties.getProperty("xpathTitulosAba9"), properties.getProperty("xpathTitulosAba10")};

		String[] idBotoesAba = {properties.getProperty("idBotoesAba1"), properties.getProperty("idBotoesAba2"), properties.getProperty("idBotoesAba3"), properties.getProperty("idBotoesAba4"), properties.getProperty("idBotoesAba5"), properties.getProperty("idBotoesAba6"), properties.getProperty("idBotoesAba7"), properties.getProperty("idBotoesAba8"), properties.getProperty("idBotoesAba9"), properties.getProperty("idBotoesAba10")};
		String[] valueBotoesAba = {properties.getProperty("valueBotoesAba1"), properties.getProperty("valueBotoesAba2"), properties.getProperty("valueBotoesAba3"), properties.getProperty("valueBotoesAba4"), properties.getProperty("valueBotoesAba5"), properties.getProperty("valueBotoesAba6"), properties.getProperty("valueBotoesAba7"), properties.getProperty("valueBotoesAba8"), properties.getProperty("valueBotoesAba9"), properties.getProperty("valueBotoesAba10")};

		String[] labelColunaTabelaAba = {properties.getProperty("labelColunaTabelaAba1"), properties.getProperty("labelColunaTabelaAba2"), properties.getProperty("labelColunaTabelaAba3"), properties.getProperty("labelColunaTabelaAba4"), properties.getProperty("labelColunaTabelaAba5"), properties.getProperty("labelColunaTabelaAba6"), properties.getProperty("labelColunaTabelaAba7"), properties.getProperty("labelColunaTabelaAba8"), properties.getProperty("labelColunaTabelaAba9"), properties.getProperty("labelColunaTabelaAba10")};
		String[] xpathColunaTabelaAba = {properties.getProperty("xpathColunaTabelaAba1"), properties.getProperty("xpathColunaTabelaAba2"), properties.getProperty("xpathColunaTabelaAba3"), properties.getProperty("xpathColunaTabelaAba4"), properties.getProperty("xpathColunaTabelaAba5"), properties.getProperty("xpathColunaTabelaAba6"), properties.getProperty("xpathColunaTabelaAba7"), properties.getProperty("xpathColunaTabelaAba8"), properties.getProperty("xpathColunaTabelaAba9"), properties.getProperty("xpathColunaTabelaAba10")};
		String[] possuiTooltipColunaTabelaAba = {properties.getProperty("possuiTooltipColunaTabelaAba1"), properties.getProperty("possuiTooltipColunaTabelaAba2"), properties.getProperty("possuiTooltipColunaTabelaAba3"), properties.getProperty("possuiTooltipColunaTabelaAba4"), properties.getProperty("possuiTooltipColunaTabelaAba5"), properties.getProperty("possuiTooltipColunaTabelaAba6"), properties.getProperty("possuiTooltipColunaTabelaAba7"), properties.getProperty("possuiTooltipColunaTabelaAba8"), properties.getProperty("possuiTooltipColunaTabelaAba9"), properties.getProperty("possuiTooltipColunaTabelaAba10")};
		String[] tooltipEsperadoColunaAba = {properties.getProperty("tooltipEsperadoColunaAba1"), properties.getProperty("tooltipEsperadoColunaAba2"), properties.getProperty("tooltipEsperadoColunaAba3"), properties.getProperty("tooltipEsperadoColunaAba4"), properties.getProperty("tooltipEsperadoColunaAba5"), properties.getProperty("tooltipEsperadoColunaAba6"), properties.getProperty("tooltipEsperadoColunaAba7"), properties.getProperty("tooltipEsperadoColunaAba8"), properties.getProperty("tooltipEsperadoColunaAba9"), properties.getProperty("tooltipEsperadoColunaAba10")};
		String[] indOrdenacaoColunaTabelaAba = {properties.getProperty("indOrdenacaoColunaTabelaAba1"), properties.getProperty("indOrdenacaoColunaTabelaAba2"), properties.getProperty("indOrdenacaoColunaTabelaAba3"), properties.getProperty("indOrdenacaoColunaTabelaAba4"), properties.getProperty("indOrdenacaoColunaTabelaAba5"), properties.getProperty("indOrdenacaoColunaTabelaAba6"), properties.getProperty("indOrdenacaoColunaTabelaAba7"), properties.getProperty("indOrdenacaoColunaTabelaAba8"), properties.getProperty("indOrdenacaoColunaTabelaAba9"), properties.getProperty("indOrdenacaoColunaTabelaAba10")};
		String[] xpathColunaOrdenacaoTabelaAba = {properties.getProperty("xpathColunaOrdenacaoTabelaAba1"), properties.getProperty("xpathColunaOrdenacaoTabelaAba2"), properties.getProperty("xpathColunaOrdenacaoTabelaAba3"), properties.getProperty("xpathColunaOrdenacaoTabelaAba4"), properties.getProperty("xpathColunaOrdenacaoTabelaAba5"), properties.getProperty("xpathColunaOrdenacaoTabelaAba6"), properties.getProperty("xpathColunaOrdenacaoTabelaAba7"), properties.getProperty("xpathColunaOrdenacaoTabelaAba8"), properties.getProperty("xpathColunaOrdenacaoTabelaAba9"), properties.getProperty("xpathColunaOrdenacaoTabelaAba10")};

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
			automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, this.getClass().getSimpleName(), tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDePesquisa(driver, nomeTeste, caminho, tentativas, verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa, idBotaoExecutarConsulta, idBotaoLimpar);
			//automacao.verificaSeExiteCamposNaoPrevistos(driver,nomeTeste,caminho,tentativas,verificaCamposPesquisa, labelsTelaPesquisa, idLabelsTelaPesquisa, idCamposTelaPesquisa);
			testeEspecificoDaTelaDePesquisa(driver, nomeTeste, caminho, tentativas);
		}

		if (abaResultadosPesquisa == true) {
			// TELA RESULTADO PESQUISA
			automacao.informaTeste(2, caminho, nomeTeste);
			automacao.verificaTamanhoDeArray(driver, nomeTeste, xpathColuna, labelColuna);
			automacao.acessaTelaResultadosPelaAba(driver, nomeTeste, tentativas, xpathAbaPesquisa, xpathAbaResultados, xPathCarregaPesquisa, xpathCarregaResultadoPesquisa);
			automacao.executaConsulta(driver, nomeTeste, tentativas, idBotaoExecutarConsulta);
			automacao.aguardaCarregamento(caminho, xpathCarregaResultadoPesquisa, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaCamposTelaDeResultados(driver, nomeTeste, caminho, tentativas, verificaCamposResultados, xpathColuna, labelColuna, idBotoesResultados);
			automacao.validaOrdenacao(driver, nomeTeste, caminho, tentativas, xpathColunaOrdenacao, indOrdenacaoColuna, labelColuna);
			testeEspecificoDaTelaDeResultados(driver, nomeTeste, caminho, tentativas);
		}

		if (abaRegistro == true) {
			// TELA CADASTROS
			automacao.informaTeste(3, caminho, nomeTeste);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAbaCadastro, nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathCarregaRegistro, nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaPresencaCamposPorId(nomeTeste, driver, qtdeMenu, idLabelCampo, idCaixaCampo, idBotoesTelaCadastro, possuiLabelsNaTelaCadastro, possuiCaixasNaTelaCadastro, possuiBotoesNaTelaCadastro);
			automacao.validaBotoesPadrao(nomeTeste, driver, tentativas, possuiBotoesPadrao, idBotoesPadraoCadastro);
			automacao.validaTitulosDaTelaPorXpath(nomeTeste, driver, tentativas, labelTitulos, xpathTitulos, possuiTitulosNaTelaCadastro);
			automacao.validaLabelsDaTelCadastro(nomeTeste, driver, tentativas, labelCampo, idLabelCampo, possuiTootip, toolTipEsperado);
			automacao.validaElementosInput(nomeTeste, driver, tentativas, idCaixaCampo, possuiValue, vlrEsperadoCaixaCampo);
			automacao.validaBotoesDaTelaCadastro(nomeTeste, driver, tentativas, idBotoesTelaCadastro, valueBotoesTelaCadastro, possuiBotoesNaTelaCadastro);
			automacao.verificaToolTipPorXpath(driver, nomeTeste, tentativas, caminho, xpathColunaTabelaCadastro, tooltipEsperadoColunaTabelaCadastro, possuiTooltipColunaTabelaCadastro);
			automacao.validaOrdenacao(driver, nomeTeste, caminho, tentativas, possuiTabelasNaTelaCadastro, xpathColunaOrdenacao, indOrdenacaoColuna, labelColuna);
			testeEspecificoDaTelaDeCadastro(driver, nomeTeste, caminho, tentativas);

			if (possuiAbas == true) {
				for (int i = 0; i < xpathAcessoAbas.length; i++) {
					boolean possuiLabelsNaAbaBoolean = Boolean.parseBoolean(possuiLabelsNaAba[i]);
					boolean possuiCaixasNaAbaBoolean = Boolean.parseBoolean(possuiCaixasNaAba[i]);
					boolean possuiTitulosNaAbaBoolean = Boolean.parseBoolean(possuiTitulosNaAba[i]);
					boolean possuiBotoesNaAbaBoolean = Boolean.parseBoolean(possuiBotoesNaAba[i]);
					boolean possuiTabelaNaAbaBoolean = Boolean.parseBoolean(possuiTabelaNaAba[i]);

					// ABAS
					automacao.informaTeste(1, caminho, nomeTeste, labelAcessoAbas[i]);
					automacao.acessaAbaPorXpath(driver, tentativas, xpathAcessoAbas[i], nomeTeste);
					automacao.aguardaCarregamento(caminho, xpathAcessoAbas[i], nomeTeste, tentativas, driver);
					automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
					automacao.verificaPresencaCamposPorId(nomeTeste, driver, qtdeMenu, idLabelsAba[i].split(";"), idCaixasAba[i].split(";"), idBotoesAba[i].split(";"), possuiLabelsNaAbaBoolean, possuiCaixasNaAbaBoolean, possuiBotoesNaAbaBoolean);
					automacao.validaTitulosDaTelaPorXpath(nomeTeste, driver, tentativas, labelTitulosAba[i].split(";"), xpathTitulosAba[i].split(";"), possuiTitulosNaAbaBoolean);
					automacao.validaLabelsDaAba(nomeTeste, driver, tentativas, labelsAba[i].split(";"), idLabelsAba[i].split(";"), possuiLabelsNaAbaBoolean);
					automacao.validaTootipDaAba(nomeTeste, driver, tentativas, idLabelsAba[i].split(";"), PossuiTooltipNaLabelAba[i].split(";"), toolTipEsperadoAba[i].split(";"));

					automacao.validaElementosInput(nomeTeste, driver, tentativas, idCaixasAba[i].split(";"), possuiValueCaixasAba[i].split(";"), vlrEsperadoCaixaCampoAba[i].split(";"));
					automacao.validaBotoesDaTelaCadastro(nomeTeste, driver, tentativas, idBotoesAba[i].split(";"), valueBotoesAba[i].split(";"), possuiBotoesNaAbaBoolean);
					if (possuiTabelaNaAbaBoolean == true) {
						automacao.verificaToolTipPorXpath(driver, nomeTeste, tentativas, caminho, xpathColunaTabelaAba[i].split(";"), tooltipEsperadoColunaAba[i].split(";"), possuiTooltipColunaTabelaAba[i].split(";"));
						//						automacao.validaOrdenacao(driver, nomeTeste, caminho, tentativas, possuiTabelasNaTelaCadastro, xpathColunaOrdenacao, indOrdenacaoColuna, labelColuna);
					}

				}

			}
		}

		duracaoTeste = System.currentTimeMillis() - inicio;
		automacao.informaTerminoDoTeste(nomeTeste, categoria, duracaoTeste);
	}

	public void testeEspecificoDaTelaDePesquisa(WebDriver driver, String nomeTeste, String caminho, int tentativas2) {
		// TESTES ESPECIFICO DA TELA DE CADASTRO
		
	}

	public void testeEspecificoDaTelaDeResultados(WebDriver driver, String nomeTeste, String caminho, int tentativas2) {
		// TESTES ESPECIFICO DA TELA DE CADASTRO
		
	}

	public void testeEspecificoDaTelaDeCadastro(WebDriver driver, String nomeTeste, String caminho, int tentativas) {
		// TESTES ESPECIFICO DA TELA DE CADASTRO

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
