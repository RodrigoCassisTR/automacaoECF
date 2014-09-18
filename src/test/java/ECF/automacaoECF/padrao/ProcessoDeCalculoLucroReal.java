package ECF.automacaoECF.padrao;



import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;

import ECF.automacaoECF.padrao.CasoDeTesteBasico;
import ECF.automacaoECF.padrao.RecebeParametros;
import ECF.automacaoECF.padrao.ValidacaoDeTela;
import ECF.automacaoECF.padrao.VerificacoesDeTela;

public class ProcessoDeCalculoLucroReal extends CasoDeTesteBasico {
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
		String nomeTeste = getClass().getSimpleName().toUpperCase();

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

		//TELA RESULTADOS
		String idBotaoConsultar = propertiesTela.getProperty("idBotaoConsultar");
		String idBotaoNovo = propertiesTela.getProperty("idBotaoNovo");
		String idBotaoCriarCopia = propertiesTela.getProperty("idBotaoCriarCopia");
		String idBotaoEditar = propertiesTela.getProperty("idBotaoEditar");
		String idBotaoExcluir = propertiesTela.getProperty("idBotaoExcluir");
		String idBotaoExportar = propertiesTela.getProperty("idBotaoExportar");
		String[] idBotoesResultados = {idBotaoConsultar, idBotaoNovo, idBotaoCriarCopia, idBotaoEditar, idBotaoExcluir, idBotaoExportar};

		//TELA CADASTRO
		String idBotaoSalvarCadastro = propertiesTela.getProperty("idBotaoSalvarCadastro");
		String idBotaoNovoCadastro = propertiesTela.getProperty("idBotaoNovoCadastro");
		String idBotaoCriarCopiaCadastro = propertiesTela.getProperty("idBotaoCriarCopiaCadastro");
		String idBotaoEditarCadastro = propertiesTela.getProperty("idBotaoEditarCadastro");
		String idBotaoExcluirCadastro = propertiesTela.getProperty("idBotaoExcluirCadastro");
		String idBotaoCancelarCadastro = propertiesTela.getProperty("idBotaoCancelarCadastro");
		String[] idBotoesPadraoCadastro = {idBotaoSalvarCadastro, idBotaoNovoCadastro, idBotaoCriarCopiaCadastro, idBotaoEditarCadastro, idBotaoExcluirCadastro, idBotaoCancelarCadastro};

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
		String[] idListaDeEmpresasNaCaixaDeSelecao = properties.getProperty("idListaDeEmpresasNaCaixaDeSelecao").split(";");
		String[] idBotoesDeSelecaoDeEmpresa = properties.getProperty("idBotoesDeSelecaoDeEmpresa").split(";");
		String empresaSelecao = properties.getProperty("empresaSelecao");

		// DE X PARA PESQUISA
		int qtdePesquisa = Integer.parseInt(properties.getProperty("qtdePesquisa"));
		String valorPesquisa1 = properties.getProperty("valorPesquisa1");
		String valorPesquisa2 = properties.getProperty("valorPesquisa2");
		String valorPesquisa3 = properties.getProperty("valorPesquisa3");
		String valorPesquisa4 = properties.getProperty("valorPesquisa4");
		String valorPesquisa5 = properties.getProperty("valorPesquisa5");
		String valorPesquisa6 = properties.getProperty("valorPesquisa6");
		String valorPesquisa7 = properties.getProperty("valorPesquisa7");
		String valorPesquisa8 = properties.getProperty("valorPesquisa8");
		String valorPesquisa9 = properties.getProperty("valorPesquisa9");
		String valorPesquisa10 = properties.getProperty("valorPesquisa10");

		String campoPesquisa1 = properties.getProperty("campoPesquisa1");
		String campoPesquisa2 = properties.getProperty("campoPesquisa2");
		String campoPesquisa3 = properties.getProperty("campoPesquisa3");
		String campoPesquisa4 = properties.getProperty("campoPesquisa4");
		String campoPesquisa5 = properties.getProperty("campoPesquisa5");
		String campoPesquisa6 = properties.getProperty("campoPesquisa6");
		String campoPesquisa7 = properties.getProperty("campoPesquisa7");
		String campoPesquisa8 = properties.getProperty("campoPesquisa8");
		String campoPesquisa9 = properties.getProperty("campoPesquisa9");
		String campoPesquisa10 = properties.getProperty("campoPesquisa10");

		String[] camposPesquisa = {campoPesquisa1, campoPesquisa2, campoPesquisa3, campoPesquisa4, campoPesquisa5, campoPesquisa6, campoPesquisa7, campoPesquisa8, campoPesquisa8, campoPesquisa9, campoPesquisa10};
		String[] valoresPesquisa = {valorPesquisa1, valorPesquisa2, valorPesquisa3, valorPesquisa4, valorPesquisa5, valorPesquisa6, valorPesquisa7, valorPesquisa8, valorPesquisa9, valorPesquisa10};

		//CAMPOS RESULTADOS
		int qtdeResultados = Integer.parseInt(properties.getProperty("qtdeResultados"));
		String valorResultados1 = properties.getProperty("valorResultados1");
		String valorResultados2 = properties.getProperty("valorResultados2");
		String valorResultados3 = properties.getProperty("valorResultados3");
		String valorResultados4 = properties.getProperty("valorResultados4");
		String valorResultados5 = properties.getProperty("valorResultados5");
		String valorResultados6 = properties.getProperty("valorResultados6");
		String valorResultados7 = properties.getProperty("valorResultados7");
		String valorResultados8 = properties.getProperty("valorResultados8");
		String valorResultados9 = properties.getProperty("valorResultados9");
		String valorResultados10 = properties.getProperty("valorResultados10");
		String campoResultados1 = properties.getProperty("campoResultados1");
		String campoResultados2 = properties.getProperty("campoResultados2");
		String campoResultados3 = properties.getProperty("campoResultados3");
		String campoResultados4 = properties.getProperty("campoResultados4");
		String campoResultados5 = properties.getProperty("campoResultados5");
		String campoResultados6 = properties.getProperty("campoResultados6");
		String campoResultados7 = properties.getProperty("campoResultados7");
		String campoResultados8 = properties.getProperty("campoResultados8");
		String campoResultados9 = properties.getProperty("campoResultados9");
		String campoResultados10 = properties.getProperty("campoResultados10");

		String[] valoresResultados = {valorResultados1, valorResultados2, valorResultados3, valorResultados4, valorResultados5, valorResultados6, valorResultados7, valorResultados8, valorResultados9, valorResultados10};
		String[] colunasResultados = {campoResultados1, campoResultados2, campoResultados3, campoResultados4, campoResultados5, campoResultados6, campoResultados7, campoResultados8, campoResultados9, campoResultados10};
		boolean registroNaoVisualizado = false;

		// DE X PARA CADASTROS
		int qtdeCadastro = Integer.parseInt(properties.getProperty("qtdeCadastro"));

		String valorCadastro1 = properties.getProperty("valorCadastro1");
		String valorCadastro2 = properties.getProperty("valorCadastro2");
		String valorCadastro3 = properties.getProperty("valorCadastro3");
		String valorCadastro4 = properties.getProperty("valorCadastro4");
		String valorCadastro5 = properties.getProperty("valorCadastro5");
		String valorCadastro6 = properties.getProperty("valorCadastro6");
		String valorCadastro7 = properties.getProperty("valorCadastro7");
		String valorCadastro8 = properties.getProperty("valorCadastro8");
		String valorCadastro9 = properties.getProperty("valorCadastro9");
		String valorCadastro10 = properties.getProperty("valorCadastro10");
		String valorCadastro11 = properties.getProperty("valorCadastro11");
		String valorCadastro12 = properties.getProperty("valorCadastro12");
		String valorCadastro13 = properties.getProperty("valorCadastro13");
		String valorCadastro14 = properties.getProperty("valorCadastro14");
		String valorCadastro15 = properties.getProperty("valorCadastro15");
		String valorCadastro16 = properties.getProperty("valorCadastro16");
		String valorCadastro17 = properties.getProperty("valorCadastro17");
		String valorCadastro18 = properties.getProperty("valorCadastro18");
		String valorCadastro19 = properties.getProperty("valorCadastro19");
		String valorCadastro20 = properties.getProperty("valorCadastro20");

		String idCampoCadastro1 = properties.getProperty("idCampoCadastro1");
		String idCampoCadastro2 = properties.getProperty("idCampoCadastro2");
		String idCampoCadastro3 = properties.getProperty("idCampoCadastro3");
		String idCampoCadastro4 = properties.getProperty("idCampoCadastro4");
		String idCampoCadastro5 = properties.getProperty("idCampoCadastro5");
		String idCampoCadastro6 = properties.getProperty("idCampoCadastro6");
		String idCampoCadastro7 = properties.getProperty("idCampoCadastro7");
		String idCampoCadastro8 = properties.getProperty("idCampoCadastro8");
		String idCampoCadastro9 = properties.getProperty("idCampoCadastro9");
		String idCampoCadastro10 = properties.getProperty("idCampoCadastro10");
		String idCampoCadastro11 = properties.getProperty("idCampoCadastro11");
		String idCampoCadastro12 = properties.getProperty("idCampoCadastro12");
		String idCampoCadastro13 = properties.getProperty("idCampoCadastro13");
		String idCampoCadastro14 = properties.getProperty("idCampoCadastro14");
		String idCampoCadastro15 = properties.getProperty("idCampoCadastro15");
		String idCampoCadastro16 = properties.getProperty("idCampoCadastro16");
		String idCampoCadastro17 = properties.getProperty("idCampoCadastro17");
		String idCampoCadastro18 = properties.getProperty("idCampoCadastro18");
		String idCampoCadastro19 = properties.getProperty("idCampoCadastro19");
		String idCampoCadastro20 = properties.getProperty("idCampoCadastro20");

		String[] valoresCadastro = {valorCadastro1, valorCadastro2, valorCadastro3, valorCadastro4, valorCadastro5, valorCadastro6, valorCadastro7, valorCadastro8, valorCadastro9, valorCadastro10, valorCadastro11, valorCadastro12, valorCadastro13, valorCadastro14, valorCadastro15, valorCadastro16, valorCadastro17, valorCadastro18, valorCadastro19, valorCadastro20};
		String[] camposTelaCadastro = {idCampoCadastro1, idCampoCadastro2, idCampoCadastro3, idCampoCadastro4, idCampoCadastro5, idCampoCadastro6, idCampoCadastro7, idCampoCadastro8, idCampoCadastro9, idCampoCadastro10, idCampoCadastro11, idCampoCadastro12, idCampoCadastro13, idCampoCadastro14, idCampoCadastro15, idCampoCadastro16, idCampoCadastro17, idCampoCadastro18, idCampoCadastro19, idCampoCadastro20};

		//VARIAVEIS DE VALIDACAO DE APURACAO
		String[] xpathAcessoAbas = properties.getProperty("xpathAcessoAbas").split(";");
		String[] labelAcessoAbas = properties.getProperty("labelAcessoAbas").split(";");

		String[] idLabelsContasAba = {properties.getProperty("idLabelsContasAba1"), properties.getProperty("idLabelsContasAba2"), properties.getProperty("idLabelsContasAba3"), properties.getProperty("idLabelsContasAba4"), properties.getProperty("idLabelsContasAba5"), properties.getProperty("idLabelsContasAba6"), properties.getProperty("idLabelsContasAba7"), properties.getProperty("idLabelsContasAba8"), properties.getProperty("idLabelsContasAba9"), properties.getProperty("idLabelsContasAba10")};
		String[] labelContasAba = {properties.getProperty("labelContasAba1"), properties.getProperty("labelContasAba2"), properties.getProperty("labelContasAba3"), properties.getProperty("labelContasAba4"), properties.getProperty("labelContasAba5"), properties.getProperty("labelContasAba6"), properties.getProperty("labelContasAba7"), properties.getProperty("labelContasAba8"), properties.getProperty("labelContasAba9"), properties.getProperty("labelContasAba10")};
		String[] idCaixaContasAba = {properties.getProperty("idCaixaContasAba1"), properties.getProperty("idCaixaContasAba2"), properties.getProperty("idCaixaContasAba3"), properties.getProperty("idCaixaContasAba4"), properties.getProperty("idCaixaContasAba5"), properties.getProperty("idCaixaContasAba6"), properties.getProperty("idCaixaContasAba7"), properties.getProperty("idCaixaContasAba8"), properties.getProperty("idCaixaContasAba9"), properties.getProperty("idCaixaContasAba10")};
		String[] valorCaixaContasAntesCalculoAba = {properties.getProperty("valorCaixaContasAntesCalculoAba1"), properties.getProperty("valorCaixaContasAntesCalculoAba2"), properties.getProperty("valorCaixaContasAntesCalculoAba3"), properties.getProperty("valorCaixaContasAntesCalculoAba4"), properties.getProperty("valorCaixaContasAntesCalculoAba5"), properties.getProperty("valorCaixaContasAntesCalculoAba6"), properties.getProperty("valorCaixaContasAntesCalculoAba7"), properties.getProperty("valorCaixaContasAntesCalculoAba8"), properties.getProperty("valorCaixaContasAntesCalculoAba9"), properties.getProperty("valorCaixaContasAntesCalculoAba10")};
		String[] valorCaixaContasDepoisCalculoAba = {properties.getProperty("valorCaixaContasDepoisCalculoAba1"), properties.getProperty("valorCaixaContasDepoisCalculoAba2"), properties.getProperty("valorCaixaContasDepoisCalculoAba3"), properties.getProperty("valorCaixaContasDepoisCalculoAba4"), properties.getProperty("valorCaixaContasDepoisCalculoAba5"), properties.getProperty("valorCaixaContasDepoisCalculoAba6"), properties.getProperty("valorCaixaContasDepoisCalculoAba7"), properties.getProperty("valorCaixaContasDepoisCalculoAba8"), properties.getProperty("valorCaixaContasDepoisCalculoAba9"), properties.getProperty("valorCaixaContasDepoisCalculoAba10")};
		String[] idBotaoCalcular = {properties.getProperty("idBotaoCalcularAba1"), properties.getProperty("idBotaoCalcularAba2"), properties.getProperty("idBotaoCalcularAba3"), properties.getProperty("idBotaoCalcularAba4"), properties.getProperty("idBotaoCalcularAba5"), properties.getProperty("idBotaoCalcularAba6"), properties.getProperty("idBotaoCalcularAba7"), properties.getProperty("idBotaoCalcularAba8"), properties.getProperty("idBotaoCalcularAba9"), properties.getProperty("idBotaoCalcularAba10")};

		//VARIAVEIS DE EDICAO DE VALORES

		String[] xpathAbaEdicaoValores = properties.getProperty("xpathAbaEdicaoValores").split(";");
		String[] labelAbaEdicaoValores = properties.getProperty("labelAbaEdicaoValores").split(";");

		String[] xpathCaixaAbaEdicaoValoresAba = {properties.getProperty("xpathCaixaAbaEdicaoValoresAba1"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba2"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba3"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba4"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba5"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba6"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba7"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba8"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba9"), properties.getProperty("xpathCaixaAbaEdicaoValoresAba10")};
		String[] valorCaixaEdicaoValoresAba = {properties.getProperty("valorCaixaEdicaoValoresAba1"), properties.getProperty("valorCaixaEdicaoValoresAba2"), properties.getProperty("valorCaixaEdicaoValoresAba3"), properties.getProperty("valorCaixaEdicaoValoresAba4"), properties.getProperty("valorCaixaEdicaoValoresAba5"), properties.getProperty("valorCaixaEdicaoValoresAba6"), properties.getProperty("valorCaixaEdicaoValoresAba7"), properties.getProperty("valorCaixaEdicaoValoresAba8"), properties.getProperty("valorCaixaEdicaoValoresAba9"), properties.getProperty("valorCaixaEdicaoValoresAba10")};
		String[] xpathLabelEdicaoValoresAba = {properties.getProperty("xpathLabelEdicaoValoresAba1"), properties.getProperty("xpathLabelEdicaoValoresAba2"), properties.getProperty("xpathLabelEdicaoValoresAba3"), properties.getProperty("xpathLabelEdicaoValoresAba4"), properties.getProperty("xpathLabelEdicaoValoresAba5"), properties.getProperty("xpathLabelEdicaoValoresAba6"), properties.getProperty("xpathLabelEdicaoValoresAba7"), properties.getProperty("xpathLabelEdicaoValoresAba8"), properties.getProperty("xpathLabelEdicaoValoresAba9"), properties.getProperty("xpathLabelEdicaoValoresAba10")};

		// POPUP
		boolean possuiLabelsNoPopUp = Boolean.parseBoolean(properties.getProperty("possuiLabelsNoPopUp"));
		String[] idLabelsPopUp = properties.getProperty("idLabelsPopUp").split(";");
		String[] labelsPopUp = properties.getProperty("labelsPopUp").split(";");
		String[] possuiTooltipPopUp = properties.getProperty("possuiTooltipPopUp").split(";");
		String[] toolTipEsperadoPopUp = properties.getProperty("toolTipEsperadoPopUp").split(";");

		boolean possuiCaixasNoPopUp = Boolean.parseBoolean(properties.getProperty("possuiCaixasNoPopUp"));
		String[] idCaixasPopUp = properties.getProperty("idCaixasPopUp").split(";");
		String[] possuiValuePopUp = properties.getProperty("possuiValuePopUp").split(";");
		String[] vlrEsperadoCaixaCampoPopUp = properties.getProperty("vlrEsperadoCaixaCampoPopUp").split(";");

		boolean possuiTitulosNoPopUp = Boolean.parseBoolean(properties.getProperty("possuiTitulosNoPopUp"));
		String[] labelTitulosPopUp = properties.getProperty("labelTitulosPopUp").split(";");
		String[] xpathTitulosPopUp = properties.getProperty("xpathTitulosPopUp").split(";");

		boolean possuiBotoesNoPopUp = Boolean.parseBoolean(properties.getProperty("possuiBotoesNoPopUp"));
		String[] idBotoesPopUp = properties.getProperty("idBotoesPopUp").split(";");
		String[] valueBotoesPopUp = properties.getProperty("valueBotoesPopUp").split(";");

		boolean possuiTabelasNoPopUp = Boolean.parseBoolean(properties.getProperty("possuiTabelasNoPopUp"));
		String[] labelColunaTabelaPopUp = properties.getProperty("labelColunaTabelaPopUp").split(";");
		String[] xpathColunaTabelaPopUp = properties.getProperty("xpathColunaTabelaPopUp").split(";");
		String[] possuiTooltipColunaTabelaPopUp = properties.getProperty("possuiTooltipColunaTabelaPopUp").split(";");
		String[] tooltipEsperadoColunaPopUp = properties.getProperty("tooltipEsperadoColunaPopUp").split(";");
		String[] indOrdenacaoColunaTabelaPopUp = properties.getProperty("indOrdenacaoColunaTabelaPopUp").split(";");
		String[] xpathColunaOrdenacaoTabelaPopUp = properties.getProperty("xpathColunaOrdenacaoTabelaPopUp").split(";");

		String[] idSeqClickSairPopUp = properties.getProperty("idSeqClickSairPopUp").split(";");
		String[] xpathSeqClickSairPopUp = properties.getProperty("xpathSeqClickSairPopUp").split(";");

		//VALIDACAO DE CALCULO
		String xpathAbavalidacaoDeCalculo = properties.getProperty("xpathAbavalidacaoDeCalculo");
		String labelAbavalidacaoDeCalculo = properties.getProperty("labelAbavalidacaoDeCalculo");

		String[] xpathLabelContasValidacaoDeCalculoAbaCalculo = properties.getProperty("xpathLabelContasValidacaoDeCalculoAbaCalculo").split(";");
		String[] labelContasValidacaoDeCalculoCalculo = properties.getProperty("labelContasValidacaoDeCalculoCalculo").split(";");
		String[] xpathCaixasContasValidacaoDeCalculoCalculo = properties.getProperty("xpathCaixasContasValidacaoDeCalculoCalculo").split(";");
		String[] valorCaixasContasValidacaoDeCalculoCalculo = properties.getProperty("valorCaixasContasValidacaoDeCalculoCalculo").split(";");

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
		automacao.selecionaEmpresaParaApuracao(driver, caminho, tentativas, nomeTeste, idListaDeEmpresasNaCaixaDeSelecao, idBotoesDeSelecaoDeEmpresa, empresaSelecao);
		automacao.clicaEmProcessarApuracao(driver, caminho, tentativas, nomeTeste, idBotaoProcessar);
		automacao.fechaTela(driver, caminho, tentativas, nomeTeste, "/html/body/div[3]/div[2]/div/ul/li[2]/a/span[2]");

		//ACESSA TELA DA APURACAO
		automacao.informaTeste(7, caminho, nomeTeste);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.pesquisaRegistroIntegrado(driver, tentativas, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta);
		registroNaoVisualizado = automacao.verificaResultadoDaPesquisa(driver, tentativas, qtdeResultados, colunasResultados, valoresResultados, idBotoesResultados, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta, xpathSemResultados);
		automacao.verificaTelaCadastro(driver, tentativas, qtdeCadastro, camposTelaCadastro, valoresCadastro);

		automacao.informaTeste(6, caminho, nomeTeste);
		for (int i = 0; i < xpathAcessoAbas.length; i++) {
			// VALIDACAO DE VALORES NAS ABAS ANTES DO CALCULO
			automacao.informaTeste(1, caminho, nomeTeste, labelAcessoAbas[i]);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAcessoAbas[i], nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathAcessoAbas[i], nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.verificaValoresDasContasAntesDoCalculo(driver, nomeTeste, tentativas, caminho, idLabelsContasAba[i].split(";"), labelContasAba[i].split(";"), idCaixaContasAba[i].split(";"), valorCaixaContasAntesCalculoAba[i].split(";"));

		}

		automacao.informaTeste(8, caminho, nomeTeste);
		automacao.entrarEmModoEdicaoApuracao(driver, tentativas, nomeTeste, idBotoesPadraoCadastro);
		for (int i = 0; i < xpathAbaEdicaoValores.length; i++) {
			// VALIDACAO DE VALORES NAS ABAS ANTES DO CALCULO
			automacao.informaTeste(1, caminho, nomeTeste, labelAbaEdicaoValores[i]);
			automacao.acessaAbaPorXpath(driver, tentativas, xpathAbaEdicaoValores[i], nomeTeste);
			automacao.aguardaCarregamento(caminho, xpathAbaEdicaoValores[i], nomeTeste, tentativas, driver);
			automacao.verificaSeApresentaMensagemDeErro(driver, nomeTeste, tentativas, caminho);
			automacao.editaValoresDaApuracao(driver, nomeTeste, tentativas, xpathCaixaAbaEdicaoValoresAba[i].split(";"), valorCaixaEdicaoValoresAba[i].split(";"), xpathLabelEdicaoValoresAba[i].split(";"), idBotoesPadraoCadastro);
		}

		automacao.salvaApuracao(driver, nomeTeste, tentativas, idBotoesPadraoCadastro);
		automacao.calculaApuracao(driver, nomeTeste, tentativas, xpathAcessoAbas[0], idBotaoCalcular[0]);

		//VALIDACAO DE POPUP QUE É APRESENTADO APÓS O CÁLCULO

		automacao.validaTitulosDaTelaPorXpath(nomeTeste, driver, tentativas, labelTitulosPopUp, xpathTitulosPopUp, possuiTitulosNoPopUp);
		automacao.validaLabelsDaTelCadastro(nomeTeste, driver, tentativas, labelsPopUp, idLabelsPopUp, possuiTooltipPopUp, toolTipEsperadoPopUp);
		automacao.validaElementosInput(nomeTeste, driver, tentativas, idCaixasPopUp, possuiValuePopUp, vlrEsperadoCaixaCampoPopUp);
		automacao.validaBotoesDaTelaCadastro(nomeTeste, driver, tentativas, idBotoesPopUp, valueBotoesPopUp, possuiBotoesNoPopUp);
		automacao.clicaNaSequenciaPopUp(nomeTeste, driver, tentativas, idSeqClickSairPopUp, xpathSeqClickSairPopUp);

		//VALIDACAO DE CALCULO
		automacao.acessaAbaCalculo(nomeTeste, driver, tentativas, xpathAbavalidacaoDeCalculo, labelAbavalidacaoDeCalculo);
		automacao.validaAbaCalculo(nomeTeste, driver, tentativas, xpathCaixasContasValidacaoDeCalculoCalculo, valorCaixasContasValidacaoDeCalculoCalculo, xpathLabelContasValidacaoDeCalculoAbaCalculo, labelContasValidacaoDeCalculoCalculo);

	}
}
