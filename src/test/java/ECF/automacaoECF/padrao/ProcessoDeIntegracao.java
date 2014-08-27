package ECF.automacaoECF.padrao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessoDeIntegracao extends CasoDeTesteBasico {

	org.apache.log4j.Logger logger = Logger.getLogger(ProcessoDeIntegracao.class.getName());
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

		//INFORMCOES REGISTRO
		String arquivoIntegracao = properties.getProperty("arquivoIntegracao");
		String arquivoIntegracaoExclui = properties.getProperty("arquivoIntegracaoExclui");

		// Lendo o arquivo taxbr.ecf.integrator.ledgeraccount.cfg
		String filename = caminhoIntegrador + "/etc/taxbr.ecf.integrator." + nomeIntegracao + ".cfg";
		propertiesIntegrador = new Properties();
		FileInputStream file = new FileInputStream(filename);
		propertiesIntegrador.load(file);

		String wsclientHost = propertiesIntegrador.getProperty("wsclient.host");
		String wsclientReturnHost = propertiesIntegrador.getProperty("wsclient.return.host");
		String routeImportFilePayloadType = propertiesIntegrador.getProperty("route.import.file.payload.type");

		//CARREGANDO AS INFORMAÇÕES NO PROPERTIES DE TELA
		String nomeClasseTela = properties.getProperty("nomeClasseTela");
		String filename2 = "./src/test/resources/" + nomeClasseTela + ".properties";
		Properties propertiesTela = new Properties();
		FileInputStream file2 = new FileInputStream(filename2);
		propertiesTela.load(file2);

		////////PROPERTIES DE TELA 
		//TELA: PLANO DE CONTAS (ACESSO)

		//////////ACESSO E TITULOS
		String caminho = propertiesTela.getProperty("caminho");

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
		String xPathCarregaPesquisa = propertiesTela.getProperty("xPathCarregaPesquisa");


		String idBotaoExecutarConsulta = propertiesTela.getProperty("idBotaoExecutarConsulta");

		String idBotaoConsultar = propertiesTela.getProperty("idBotaoConsultar");
		String idBotaoNovo = propertiesTela.getProperty("idBotaoNovo");
		String idBotaoCriarCopia = propertiesTela.getProperty("idBotaoCriarCopia");
		String idBotaoEditar = propertiesTela.getProperty("idBotaoEditar");
		String idBotaoExcluir = propertiesTela.getProperty("idBotaoExcluir");
		String idBotaoExportar = propertiesTela.getProperty("idBotaoExportar");

		String[] idBotoesResultados = {idBotaoConsultar, idBotaoNovo, idBotaoCriarCopia, idBotaoEditar, idBotaoExcluir, idBotaoExportar};

		// Pastas Integração
		String pastaEntrada = "C:/temp/ECF/" + nomeIntegracao + "/entrada";
		String pastaEnviado = "C:/temp/ECF/" + nomeIntegracao + "/enviado";
		String pastaErro = "C:/temp/ECF/" + nomeIntegracao + "/erro";
		String pastaRecebido = "C:/temp/ECF/" + nomeIntegracao + "/recebido";
		String[] pastasIntegracao = {pastaEntrada, pastaEnviado, pastaErro, pastaRecebido};

		//CAMPOS DO ARQUIVO

		String campo1 = properties.getProperty("campo1");
		String campo2 = properties.getProperty("campo2");
		String campo3 = properties.getProperty("campo3");
		String campo4 = properties.getProperty("campo4");
		String campo5 = properties.getProperty("campo5");
		String campo6 = properties.getProperty("campo6");
		String campo7 = properties.getProperty("campo7");
		String campo8 = properties.getProperty("campo8");
		String campo9 = properties.getProperty("campo9");
		String campo10 = properties.getProperty("campo10");
		String campo11 = properties.getProperty("campo11");
		String campo12 = properties.getProperty("campo12");
		String campo13 = properties.getProperty("campo13");
		String campo14 = properties.getProperty("campo14");
		String campo15 = properties.getProperty("campo15");
		String campo16 = properties.getProperty("campo16");
		String campo17 = properties.getProperty("campo17");
		String campo18 = properties.getProperty("campo18");
		String campo19 = properties.getProperty("campo19");
		String campo20 = properties.getProperty("campo20");

		//CAMPOS PESQUISA
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
		String[] camposPesquisa = {campoPesquisa1, campoPesquisa2, campoPesquisa3, campoPesquisa4, campoPesquisa5, campoPesquisa6, campoPesquisa7, campoPesquisa8, campoPesquisa9, campoPesquisa10};
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

		////// CAMPOS CADASTRO
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

		String[] camposRegistro = {"!campo1", "!campo2", "!campo3", "!campo4", "!campo5", "!campo6", "!campo7", "!campo8", "!campo9", "!campo10", "!campo11", "!campo12", "!campo13", "!campo14", "!campo15", "!campo16", "!campo17", "!campo18", "!campo19", "!campo20"};
		String[] informacoesRegistro = {campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9, campo10, campo11, campo12, campo13, campo14, campo15, campo16, campo17, campo18, campo19, campo20};

		boolean existeRegistro = false;
		boolean registroNaoVisualizado = false;
		int tentativasDeLocalizar = 0;

		VerificacoesDeIntegracao integracao = new VerificacoesDeIntegracao();
		VerificacoesDeTela automacao = new VerificacoesDeTela();

		//ROTEIRO DE INTEGRACAO
		automacao.informaTeste(0, "-", nomeTeste);
		
		logger.info("----------------------------------------------------------");
		logger.info("PASTAS INTEGRAÇÃO ECF");
		logger.info("entrada: " + pastaEntrada);
		logger.info("enviado: " + pastaEnviado);
		logger.info("erro: " + pastaErro);
		logger.info("recebido: " + pastaRecebido);
		logger.info("----------------------------------------------------------");
		logger.info("PARAMETROS");
		logger.info("wsclient.host: " + wsclientHost);
		logger.info("wsclient.return.host: " + wsclientReturnHost);
		logger.info("route.import.file.payload.type: " + routeImportFilePayloadType);
		logger.info("----------------------------------------------------------");

		//VERIFICA SE O SERVIÇO DO INTEGRADOR ESTÁ INICIADO
		integracao.verficaServicoIntegracaoIniciado(nomeDoServicoIntegrador);

		//VERIFICA SE O REGISTRO A SER INTEGRADO JA EXISTE, SE EXISTE EXCLUI
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);
		existeRegistro = automacao.verificaExistenciaDoRegistro(driver, tentativas, nomeTeste, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta, qtdeResultados, colunasResultados, valoresResultados);
		if (existeRegistro == true) {
			integracao.integraRegistro(arquivoIntegracaoExclui, pastasIntegracao, camposRegistro, informacoesRegistro);
			Thread.sleep(1000);
		}

		//INTEGRAD REGISTRO
		integracao.integraRegistro(arquivoIntegracao, pastasIntegracao, camposRegistro, informacoesRegistro);

		//PESQUISA REGISTRO
		automacao.efetuaLogout(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.pesquisaRegistroIntegrado(driver, tentativas, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta);
		registroNaoVisualizado = automacao.verificaResultadoDaPesquisa(driver, tentativas, qtdeResultados, colunasResultados, valoresResultados, idBotoesResultados, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta, xpathSemResultados);

		//TENTA 4 VEZES LOCALIZAR O REGISTRO NA TELA, FAZENDO O LOGOUT E LOGIN A CADA TENTATIVA
		do {
			if (registroNaoVisualizado == false) {
				automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
				automacao.efetuaLogout(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
				automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
				automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
				automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
				automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
				automacao.pesquisaRegistroIntegrado(driver, tentativas, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta);
				registroNaoVisualizado = automacao.verificaResultadoDaPesquisa(driver, tentativas, qtdeResultados, colunasResultados, valoresResultados, idBotoesResultados, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta, xpathSemResultados);
				tentativasDeLocalizar++;
			}
		} while (registroNaoVisualizado == false || tentativasDeLocalizar > 4);

		automacao.verificaTelaCadastro(driver, tentativas, qtdeCadastro, camposTelaCadastro, valoresCadastro);

		//EXCLUI REGISTRO
		integracao.integraRegistro(arquivoIntegracaoExclui, pastasIntegracao, camposRegistro, informacoesRegistro);

		//VERIFICA SE O REGISTRO FOI EXCLUIDO
		automacao.reotrnaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.VerificaExclusaoDoRegistro(driver, tentativas, nomeTeste, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta, qtdeResultados, colunasResultados, valoresResultados);

		duracaoTeste = System.currentTimeMillis() - inicio;
		automacao.informaTerminoDoTeste(nomeTeste, categoria, duracaoTeste);
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
