package ECF.automacaoECF.integracao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import ECF.automacaoECF.padrao.CasoDeTesteBasico;
import ECF.automacaoECF.padrao.RecebeParametros;
import ECF.automacaoECF.padrao.VerificacoesDeIntegracao;
import ECF.automacaoECF.padrao.VerificacoesDeTela;

public class IntegracaoLedgerAccountTeste extends CasoDeTesteBasico {
	org.apache.log4j.Logger logger = Logger.getLogger(IntegracaoLedgerAccountTeste.class.getName());

	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	int tentativas = Integer.parseInt(numerotentativas);
	public String tempoMedio = new RecebeParametros().tempoMedio;
	public String caminhoIntegrador = new RecebeParametros().caminhoIntegrador;
	public String nomeDoServicoIntegrador = new RecebeParametros().nomeDoServicoIntegrador;
	protected Properties propertiesIntegrador;
	int alertas = 0;

	@Test
	public void integracaoLedgerAccount() throws Throwable {
		long inicio = System.currentTimeMillis();

		// Lendo o arquivo taxbr.ecf.integrator.ledgeraccount.cfg
		String filename = caminhoIntegrador + "/etc/taxbr.ecf.integrator.ledgeraccount.cfg";
		propertiesIntegrador = new Properties();
		FileInputStream file = new FileInputStream(filename);
		propertiesIntegrador.load(file);

		// INFORMACOES DA TELA
		String qtdeMenu = properties.getProperty("qtdeMenu");
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
		String xPathCarregaPesquisa = properties.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = properties.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = properties.getProperty("xpathCarregaRegistro");

		////TELA PESQUISA

		//CAMPO 1 'CNPJ'
		String labelCampoPesquisa1 = properties.getProperty("labelCampoPesquisa1");
		String xpathLabelPesquisa1 = properties.getProperty("xpathLabelPesquisa1");
		String xpathCaixaPesquisa1 = properties.getProperty("xpathCaixaPesquisa1");

		//CAMPO 2 'Código Conta Contábil'
		String labelCampoPesquisa2 = properties.getProperty("labelCampoPesquisa2");
		String xpathLabelPesquisa2 = properties.getProperty("xpathLabelPesquisa2");
		String xpathCaixaPesquisa2 = properties.getProperty("xpathCaixaPesquisa2");

		//CAMPO 3 'Data Inicial'
		String labelCampoPesquisa3 = properties.getProperty("labelCampoPesquisa3");
		String xpathLabelPesquisa3 = properties.getProperty("xpathLabelPesquisa3");
		String xpathCaixaPesquisa3 = properties.getProperty("xpathCaixaPesquisa3");

		//CAMPO 4 'Descrição Conta Contábil '
		String labelCampoPesquisa4 = properties.getProperty("labelCampoPesquisa4");
		String xpathLabelPesquisa4 = properties.getProperty("xpathLabelPesquisa4");
		String xpathCaixaPesquisa4 = properties.getProperty("xpathCaixaPesquisa4");

		//CAMPO 5 'Tipo da Conta'
		String labelCampoPesquisa5 = properties.getProperty("labelCampoPesquisa5");
		String xpathLabelPesquisa5 = properties.getProperty("xpathLabelPesquisa5");
		String xpathCaixaPesquisa5 = properties.getProperty("xpathCaixaPesquisa5");

		//CAMPO 6 'Tipo da Conta'
		String labelCampoPesquisa6 = properties.getProperty("labelCampoPesquisa6");
		String xpathLabelPesquisa6 = properties.getProperty("xpathLabelPesquisa6");
		String xpathCaixaPesquisa6 = properties.getProperty("xpathCaixaPesquisa6");

		//CAMPO 7 'Ind. Natureza'
		String labelCampoPesquisa7 = properties.getProperty("labelCampoPesquisa7");
		String xpathLabelPesquisa7 = properties.getProperty("xpathLabelPesquisa7");
		String xpathCaixaPesquisa7 = properties.getProperty("xpathCaixaPesquisa7");

		//BOTAO 'Executar Consulta'
		String idBotaoExecutarConsulta = properties.getProperty("idBotaoExecutarConsulta");

		//INFORMACOES DE LEDGER ACCOUNT
		//INFORMACOES GERAIS DO REGISTRO

		String estabelecimento = properties.getProperty("estabelecimento");

		//CONTA SINTETICA
		String ContaSinteticaCodCentralRegistration = properties.getProperty("ContaSinteticaCodCentralRegistration");
		String ContaSinteticaCodLedgerAccount = properties.getProperty("ContaSinteticaCodLedgerAccount");
		String ContaSinteticaDatInstertUpdate = properties.getProperty("ContaSinteticaDatInstertUpdate");

		//CONTA ANALITICA
		String ContaAnaliticaCodCentralRegistration = properties.getProperty("ContaAnaliticaCodCentralRegistration");
		String ContaAnaliticaCodLedgerAccount = properties.getProperty("ContaAnaliticaCodLedgerAccount");
		String ContaAnaliticaDatInstertUpdate = properties.getProperty("ContaAnaliticaDatInstertUpdate");

		String arquivoLedgerAccountModelo = properties.getProperty("arquivoLedgerAccountModelo");
		String nomeTeste = properties.getProperty("nomeTeste");
		String categoria = properties.getProperty("categoria");
		String nomeIntegracao = properties.getProperty("nomeIntegracao");

		// Pastas Integração
		String pastaEntrada = "C:/temp/ECF/ledgeraccount/entrada";
		String pastaEnviado = "C:/temp/ECF/ledgeraccount/enviado";
		String pastaErro = "C:/temp/ECF/ledgeraccount/erro";
		String pastaRecebido = "C:/temp/ECF/ledgeraccount/recebido";

		String wsclientHost = propertiesIntegrador.getProperty("wsclient.host");
		String wsclientReturnHost = propertiesIntegrador.getProperty("wsclient.return.host");
		String routeImportFilePayloadType = propertiesIntegrador.getProperty("route.import.file.payload.type");
		String[] camposTelaPesquisa = {xpathCaixaPesquisa1, xpathCaixaPesquisa2, xpathCaixaPesquisa3, xpathCaixaPesquisa4, xpathCaixaPesquisa5, xpathCaixaPesquisa6, xpathCaixaPesquisa7};
		String[] camposLedgerAccount = {"!ContaSinteticaCodCentralRegistration", "!ContaSinteticaCodLedgerAccount", "!ContaSinteticaDatInstertUpdate", "!ContaAnaliticaCodCentralRegistration", "!ContaAnaliticaCodLedgerAccount", "!ContaAnaliticaDatInstertUpdate"};
		String[] informacoesRegistro = {ContaSinteticaCodCentralRegistration, ContaSinteticaCodLedgerAccount, ContaSinteticaDatInstertUpdate, ContaAnaliticaCodCentralRegistration, ContaAnaliticaCodLedgerAccount, ContaAnaliticaDatInstertUpdate};
		String[] pastasIntegracao = {pastaEntrada, pastaEnviado, pastaErro, pastaRecebido};

		VerificacoesDeIntegracao integracao = new VerificacoesDeIntegracao();
		VerificacoesDeTela selenium = new VerificacoesDeTela();

		selenium.informaTeste(0, "-", nomeTeste);
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

		logger.info("Verificando se o serviço do Integrador iniciado...");

		integracao.verficaServicoIntegracaoIniciado(nomeDoServicoIntegrador);
		integracao.integraLedgerAccount(camposLedgerAccount, informacoesRegistro, pastasIntegracao, arquivoLedgerAccountModelo);
		selenium.verificaRegistroIntegrado(driver, url, usuario, senha, navegador, nomeTeste, tentativas, nomeIntegracao, informacoesRegistro, qtdeMenu, labelMenu1, xpathMenu1, labelMenu2, xpathMenu2, labelMenu3, xpathMenu3, labelMenu4, xpathMenu4, labelTela, xpathTela, xPathCarregaPesquisa, estabelecimento, camposTelaPesquisa, idBotaoExecutarConsulta,xpathCarregaResultadoPesquisa,xpathCarregaRegistro);

	}
	@After
	public void tearDown() {
		driver.close();
		driver.quit();

	}
}
