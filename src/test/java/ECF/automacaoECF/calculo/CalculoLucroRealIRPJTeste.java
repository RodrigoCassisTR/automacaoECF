package ECF.automacaoECF.calculo;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;

import ECF.automacaoECF.padrao.CasoDeTesteBasico;
import ECF.automacaoECF.padrao.RecebeParametros;
import ECF.automacaoECF.padrao.ValidacaoDeTela;
import ECF.automacaoECF.padrao.VerificacoesDeTela;

public class CalculoLucroRealIRPJTeste extends CasoDeTesteBasico {
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
	public void testeApuracao() throws Throwable {
		long inicio = System.currentTimeMillis();

		//CARREGANDO AS INFORMAÇÕES NO PROPERTIES DE TELA
		String nomeClasseTela = properties.getProperty("nomeClasseTela");
		String arquivoPropertiesTela = "./src/test/resources/" + nomeClasseTela + ".properties";
		Properties propertiesTela = new Properties();
		FileInputStream file2 = new FileInputStream(arquivoPropertiesTela);
		propertiesTela.load(file2);

		//CARREGANDO AS INFORMAÇÕES NO PROPERTIES DE TELA PROCESSO EM LOTE
		String nomeClasseTelaProcessoEmLote = properties.getProperty("nomeClasseTelaProcessoEmLote");
		String arquivoPropertiesTelaProcessoEmLote = "./src/test/resources/" + nomeClasseTelaProcessoEmLote + ".properties";
		Properties propertiesTelaProcessoEmLote = new Properties();
		FileInputStream file3 = new FileInputStream(arquivoPropertiesTelaProcessoEmLote);
		propertiesTelaProcessoEmLote.load(file3);

		//ACESSO E TITULOS
		String caminho = propertiesTela.getProperty("caminho");

		//ACESSO A TELA
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

		//VALIDACOES PADRAO
		String xPathCarregaPesquisa = propertiesTela.getProperty("xPathCarregaPesquisa");
		String idBotaoExecutarConsulta = propertiesTela.getProperty("idBotaoExecutarConsulta");

		//ACESSO A TELA PROCESSO EM LOTE
		int qtdeMenuTelaProcessoEmLote = Integer.parseInt(propertiesTelaProcessoEmLote.getProperty("qtdeMenu"));
		String labelMenu1TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("labelMenu1");
		String xpathMenu1TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xpathMenu1");
		String labelMenu2TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("labelMenu2");
		String xpathMenu2TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xpathMenu2");
		String labelMenu3TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("labelMenu3");
		String xpathMenu3TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xpathMenu3");
		String labelMenu4TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("labelMenu4");
		String xpathMenu4TelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xpathMenu4");
		String labelTelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("labelTela");
		String xpathTelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xpathTela");
		String xPathCarregaPesquisaTelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("xPathCarregaPesquisa");
		String idBotaoExecutarConsultaTelaProcessoEmLote = propertiesTelaProcessoEmLote.getProperty("idBotaoExecutarConsulta");

		String nomeTeste = getClass().getSimpleName().toUpperCase();

		//INFORMCOES DA APURACAO
		String empresaApuracao = properties.getProperty("String empresaApuracao");

		////// INFORMAÇÕES APURACAO (PROCESSO EM LOTE)

		String idBotaoProcessar = properties.getProperty("idBotaoProcessar");
		String idCampoAnoApuracao = properties.getProperty("idCampoAnoApuracao");
		String valorCampoAnoApuracao = properties.getProperty("valorCampoAnoApuracao");
		String idCampoFormaTributacao = properties.getProperty("idCampoFormaTributacao");
		String valorCampoFormaTributacao = properties.getProperty("valorCampoFormaTributacao");
		String idCampoApuracao = properties.getProperty("idCampoApuracao");
		String valorCampoApuracao = properties.getProperty("valorCampoApuracao");
		String idCampoPeriodo = properties.getProperty("idCampoPeriodo");
		String valorCampoPeriodo = properties.getProperty("valorCampoPeriodo");

		String[] idCamposTelaApuracao = {idCampoAnoApuracao, idCampoFormaTributacao, idCampoApuracao, idCampoPeriodo};
		String[] valorCamposTelaApuracao = {valorCampoAnoApuracao, valorCampoFormaTributacao, valorCampoApuracao, valorCampoPeriodo};

		//CAMPO 5 '*Considerar no processamento:'
		String idCampoReceitaBruta = properties.getProperty("idCampoReceitaBruta");
		String valorCampoReceitaBruta = properties.getProperty("valorCampoReceitaBruta");
		String idCampoBalancoSuspensaoReducao = properties.getProperty("idCampoBalancoSuspensaoReducao");
		String valorCampoBalancoSuspensaoReducao = properties.getProperty("valorCampoBalancoSuspensaoReducao");

		String[] idOpcoesConsiderarNoProcessamento = {idCampoReceitaBruta, idCampoBalancoSuspensaoReducao};
		String[] valorOpcoesConsiderarnoProcessamento = {valorCampoReceitaBruta, valorCampoBalancoSuspensaoReducao};

		//CAMPO 6 '*Opções de Processamento'
		String idCampoImportarSaldosDasContasLalur = properties.getProperty("idCampoImportarSaldosDasContasLalur");
		String valorCampoImportarSaldosDasContasLalur = properties.getProperty("valorCampoImportarSaldosDasContasLalur");
		String idCampoCalcularApuracaoDoTributo = properties.getProperty("idCampoCalcularApuracaoDoTributo");
		String valorCampoCalcularApuracaoDoTributo = properties.getProperty("valorCampoCalcularApuracaoDoTributo");
		String idCampoAmbas = properties.getProperty("idCampoAmbas");
		String valorCampoAmbas = properties.getProperty("valorCampoAmbas");

		String[] idOpcoesDeProcessamento = {idCampoImportarSaldosDasContasLalur, idCampoCalcularApuracaoDoTributo, idCampoAmbas};
		String[] valorOpcoesDeProcessamento = {valorCampoImportarSaldosDasContasLalur, valorCampoCalcularApuracaoDoTributo, valorCampoAmbas};

		//CAMPO 7 'Tipo de Cálculo'
		String idCampoCsll = properties.getProperty("idCampoCsll");
		String valorCampoCsll = properties.getProperty("valorCampoCsll");
		String idCampoIrpj = properties.getProperty("idCampoIrpj");
		String valorCampoIrpj = properties.getProperty("valorCampoIrpj");

		String[] idTipoDeCalculo = {idCampoCsll, idCampoIrpj};
		String[] valorTipoDeCalculo = {valorCampoCsll, valorCampoIrpj};

		//CAMPO 8 '*Calcular Reinvestimento'
		String idCampoCalcularReinvestimentoNao = properties.getProperty("idCampoCalcularReinvestimentoNao");
		String valorCampoCalcularReinvestimentoNao = properties.getProperty("valorCampoCalcularReinvestimentoNao");
		String idCampoCalcularReinvestimentoSim = properties.getProperty("idCampoCalcularReinvestimentoSim");
		String valorCampoCalcularReinvestimentoSim = properties.getProperty("valorCampoCalcularReinvestimentoSim");

		String[] idCalcularReinvestimento = {idCampoCalcularReinvestimentoNao, idCampoCalcularReinvestimentoSim};
		String[] valorCalcularReinvestimento = {valorCampoCalcularReinvestimentoNao, valorCampoCalcularReinvestimentoSim};

		//CAMPO 9 '*Atualizar Saldo com base na SELIC'
		String idCampoAtualizarSaldoComBaseNaSelicNao = properties.getProperty("idCampoAtualizarSaldoComBaseNaSelicNao");
		String valorCampoAtualizarSaldoComBaseNaSelicNao = properties.getProperty("valorCampoAtualizarSaldoComBaseNaSelicNao");
		String idCampoAtualizarSaldoComBaseNaSelicSim = properties.getProperty("idCampoAtualizarSaldoComBaseNaSelicSim");
		String valorCampoAtualizarSaldoComBaseNaSelicSim = properties.getProperty("valorCampoAtualizarSaldoComBaseNaSelicSim");

		String[] idAtualizarSaldoComBaseNaSelic = {idCampoAtualizarSaldoComBaseNaSelicNao, idCampoAtualizarSaldoComBaseNaSelicSim};
		String[] valorAtualizarSaldoComBaseNaSelic = {valorCampoAtualizarSaldoComBaseNaSelicNao, valorCampoAtualizarSaldoComBaseNaSelicSim};

		//CAMPO 10 'Transportar Excesso de Juros para'
		String idCampoAdicoes = properties.getProperty("idCampoAdicoes");
		String valorCampoAdicoes = properties.getProperty("valorCampoAdicoes");
		String idCampoExclusoes = properties.getProperty("idCampoExclusoes");
		String valorCampoExclusoes = properties.getProperty("valorCampoExclusoes");
		String idCampoNaoTransportar = properties.getProperty("idCampoNaoTransportar");
		String valorCampoNaoTransportar = properties.getProperty("valorCampoNaoTransportar");

		String[] idTransportarExcessoDeJurosPara = {idCampoAdicoes, idCampoExclusoes, idCampoNaoTransportar};
		String[] valorTransportarExcessoDeJurosPara = {valorCampoAdicoes, valorCampoExclusoes, valorCampoNaoTransportar};

		//CAMPO 11 '*Processar'
		String idCampoProcessarEmpresa = properties.getProperty("idCampoProcessarEmpresa");
		String valorCampoProcessarEmpresa = properties.getProperty("valorCampoProcessarEmpresa");
		String idCampoProcessarEstabelecimento = properties.getProperty("idCampoProcessarEstabelecimento");
		String valorCampoProcessarEstabelecimento = properties.getProperty("valorCampoProcessarEstabelecimento");
		String idCampoProcessarScp = properties.getProperty("idCampoProcessarScp");
		String valorCampoProcessarScp = properties.getProperty("valorCampoProcessarScp");

		String[] idProcessar = {idCampoProcessarEmpresa, idCampoProcessarEstabelecimento, idCampoProcessarScp};
		String[] valorProcessar = {valorCampoProcessarEmpresa, valorCampoProcessarEstabelecimento, valorCampoProcessarScp};

		//CAMPO 12 'SCP'
		String idCampoComScp = properties.getProperty("idCampoComScp");
		String valorCampoComScp = properties.getProperty("valorCampoComScp");

		String[] idScp = {idCampoComScp};
		String[] valorScp = {valorCampoComScp};

		//CAMPO 12 '*Empresas/Estabelecimentos/SCPs'

		VerificacoesDeTela automacao = new VerificacoesDeTela();

		automacao.informaTeste(0, caminho, nomeTeste);
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);

		//GERANDO A APURACAO
		automacao.acessaTelaPorClick2(driver, qtdeMenuTelaProcessoEmLote, xpathMenu1TelaProcessoEmLote, xpathMenu2TelaProcessoEmLote, xpathMenu3TelaProcessoEmLote, xpathMenu4TelaProcessoEmLote, xpathTelaProcessoEmLote, nomeTeste, labelMenu1TelaProcessoEmLote, labelMenu2TelaProcessoEmLote, labelMenu3TelaProcessoEmLote, labelMenu4TelaProcessoEmLote, labelTelaProcessoEmLote, qtdeMenuTelaProcessoEmLote, tentativas);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisaTelaProcessoEmLote, nomeTeste, tentativas, driver);
		automacao.preencheCamposDeApuracao(driver, caminho, tentativas, nomeTeste, idCamposTelaApuracao, valorCamposTelaApuracao);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idOpcoesConsiderarNoProcessamento, valorOpcoesConsiderarnoProcessamento);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idOpcoesDeProcessamento, valorOpcoesDeProcessamento);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idTipoDeCalculo, valorTipoDeCalculo);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idCalcularReinvestimento, valorCalcularReinvestimento);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idAtualizarSaldoComBaseNaSelic, valorAtualizarSaldoComBaseNaSelic);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idTransportarExcessoDeJurosPara, valorTransportarExcessoDeJurosPara);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idProcessar, valorProcessar);
		automacao.marcaCheckBox(driver, caminho, tentativas, nomeTeste, idScp, valorScp);

		


		Thread.sleep(100000);

	}
}
