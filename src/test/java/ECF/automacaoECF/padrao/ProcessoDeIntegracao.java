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
		String estabelecimento = properties.getProperty("estabelecimento");

		////// CADASTROS
		//CAMPO 1 ''
		String RegistroCampo1 = properties.getProperty("RegistroCampo1");
		String RegistroCampo2 = properties.getProperty("RegistroCampo2");
		String RegistroCampo3 = properties.getProperty("RegistroCampo3");

		//INFORMCOES REGISTRO
		String arquivoIntegracao = properties.getProperty("arquivoIntegracao");

		// Lendo o arquivo taxbr.ecf.integrator.ledgeraccount.cfg
		String filename = caminhoIntegrador + "/etc/taxbr.ecf.integrator.ledgeraccount.cfg";
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
		boolean abaPesquisa = Boolean.parseBoolean(propertiesTela.getProperty("abaPesquisa"));
		boolean abaResultadosPesquisa = Boolean.parseBoolean(propertiesTela.getProperty("abaResultadosPesquisa"));
		boolean abaRegistro = Boolean.parseBoolean(propertiesTela.getProperty("abaRegistro"));

		//////////VALIDACOES PADRAO
		String xpathAbaCadastro = propertiesTela.getProperty("xpathAbaCadastro");
		String idBotaoExecutarConsulta = propertiesTela.getProperty("idBotaoExecutarConsulta");
		String xPathCarregaPesquisa = propertiesTela.getProperty("xPathCarregaPesquisa");
		String xpathCarregaResultadoPesquisa = propertiesTela.getProperty("xpathCarregaResultadoPesquisa");
		String xpathCarregaRegistro = propertiesTela.getProperty("xpathCarregaRegistro");

		////TELA PESQUISA
		boolean verificaCamposPesquisa = Boolean.parseBoolean(propertiesTela.getProperty("verificaCamposPesquisa"));
		int qtdeCamposPesquisa = Integer.parseInt(propertiesTela.getProperty("qtdeCamposPesquisa"));

		//CAMPO 1 
		String labelCampoPesquisa1 = propertiesTela.getProperty("labelCampoPesquisa1");
		String idLabelPesquisa1 = propertiesTela.getProperty("idLabelPesquisa1");
		String idCaixaPesquisa1 = propertiesTela.getProperty("idCaixaPesquisa1");

		//CAMPO 2 
		String labelCampoPesquisa2 = propertiesTela.getProperty("labelCampoPesquisa2");
		String idLabelPesquisa2 = propertiesTela.getProperty("idLabelPesquisa2");
		String idCaixaPesquisa2 = propertiesTela.getProperty("idCaixaPesquisa2");

		//CAMPO 3 
		String labelCampoPesquisa3 = propertiesTela.getProperty("labelCampoPesquisa3");
		String idLabelPesquisa3 = propertiesTela.getProperty("idLabelPesquisa3");
		String idCaixaPesquisa3 = propertiesTela.getProperty("idCaixaPesquisa3");

		//CAMPO 4 
		String labelCampoPesquisa4 = propertiesTela.getProperty("labelCampoPesquisa4");
		String idLabelPesquisa4 = propertiesTela.getProperty("idLabelPesquisa4");
		String idCaixaPesquisa4 = propertiesTela.getProperty("idCaixaPesquisa4");

		//CAMPO 5 
		String labelCampoPesquisa5 = propertiesTela.getProperty("labelCampoPesquisa5");
		String idLabelPesquisa5 = propertiesTela.getProperty("idLabelPesquisa5");
		String idCaixaPesquisa5 = propertiesTela.getProperty("idCaixaPesquisa5");

		//CAMPO 6 
		String labelCampoPesquisa6 = propertiesTela.getProperty("labelCampoPesquisa6");
		String idLabelPesquisa6 = propertiesTela.getProperty("idLabelPesquisa6");
		String idCaixaPesquisa6 = propertiesTela.getProperty("idCaixaPesquisa6");

		//CAMPO 7
		String labelCampoPesquisa7 = propertiesTela.getProperty("labelCampoPesquisa7");
		String idLabelPesquisa7 = propertiesTela.getProperty("idLabelPesquisa7");
		String idCaixaPesquisa7 = propertiesTela.getProperty("idCaixaPesquisa7");

		//CAMPO 8
		String labelCampoPesquisa8 = propertiesTela.getProperty("labelCampoPesquisa8");
		String idLabelPesquisa8 = propertiesTela.getProperty("idLabelPesquisa8");
		String idCaixaPesquisa8 = propertiesTela.getProperty("idCaixaPesquisa8");

		//CAMPO 9
		String labelCampoPesquisa9 = propertiesTela.getProperty("labelCampoPesquisa9");
		String idLabelPesquisa9 = propertiesTela.getProperty("idLabelPesquisa9");
		String idCaixaPesquisa9 = propertiesTela.getProperty("idCaixaPesquisa9");

		//CAMPO 10
		String labelCampoPesquisa10 = propertiesTela.getProperty("labelCampoPesquisa10");
		String idLabelPesquisa10 = propertiesTela.getProperty("idLabelPesquisa10");
		String idCaixaPesquisa10 = propertiesTela.getProperty("idCaixaPesquisa10");

		//CAMPO 11
		String labelCampoPesquisa11 = propertiesTela.getProperty("labelCampoPesquisa11");
		String idLabelPesquisa11 = propertiesTela.getProperty("idLabelPesquisa11");
		String idCaixaPesquisa11 = propertiesTela.getProperty("idCaixaPesquisa11");

		//CAMPO 12
		String labelCampoPesquisa12 = propertiesTela.getProperty("labelCampoPesquisa12");
		String idLabelPesquisa12 = propertiesTela.getProperty("idLabelPesquisa12");
		String idCaixaPesquisa12 = propertiesTela.getProperty("idCaixaPesquisa12");

		//CAMPO 13
		String labelCampoPesquisa13 = propertiesTela.getProperty("labelCampoPesquisa13");
		String idLabelPesquisa13 = propertiesTela.getProperty("idLabelPesquisa13");
		String idCaixaPesquisa13 = propertiesTela.getProperty("idCaixaPesquisa13");

		//CAMPO 14
		String labelCampoPesquisa14 = propertiesTela.getProperty("labelCampoPesquisa14");
		String idLabelPesquisa14 = propertiesTela.getProperty("idLabelPesquisa14");
		String idCaixaPesquisa14 = propertiesTela.getProperty("idCaixaPesquisa14");

		//CAMPO 15
		String labelCampoPesquisa15 = propertiesTela.getProperty("labelCampoPesquisa15");
		String idLabelPesquisa15 = propertiesTela.getProperty("idLabelPesquisa15");
		String idCaixaPesquisa15 = propertiesTela.getProperty("idCaixaPesquisa15");

		//CAMPO 16
		String labelCampoPesquisa16 = propertiesTela.getProperty("labelCampoPesquisa16");
		String idLabelPesquisa16 = propertiesTela.getProperty("idLabelPesquisa16");
		String idCaixaPesquisa16 = propertiesTela.getProperty("idCaixaPesquisa16");

		//CAMPO 17
		String labelCampoPesquisa17 = propertiesTela.getProperty("labelCampoPesquisa17");
		String idLabelPesquisa17 = propertiesTela.getProperty("idLabelPesquisa17");
		String idCaixaPesquisa17 = propertiesTela.getProperty("idCaixaPesquisa17");

		//CAMPO 18
		String labelCampoPesquisa18 = propertiesTela.getProperty("labelCampoPesquisa18");
		String idLabelPesquisa18 = propertiesTela.getProperty("idLabelPesquisa18");
		String idCaixaPesquisa18 = propertiesTela.getProperty("idCaixaPesquisa18");

		//CAMPO 19
		String labelCampoPesquisa19 = propertiesTela.getProperty("labelCampoPesquisa19");
		String idLabelPesquisa19 = propertiesTela.getProperty("idLabelPesquisa19");
		String idCaixaPesquisa19 = propertiesTela.getProperty("idCaixaPesquisa19");

		//CAMPO 20
		String labelCampoPesquisa20 = propertiesTela.getProperty("labelCampoPesquisa20");
		String idLabelPesquisa20 = propertiesTela.getProperty("idLabelPesquisa20");
		String idCaixaPesquisa20 = propertiesTela.getProperty("idCaixaPesquisa20");

		String[] camposTelaPesquisa = {labelCampoPesquisa1, idLabelPesquisa1, idCaixaPesquisa1, labelCampoPesquisa2, idLabelPesquisa2, idCaixaPesquisa2, labelCampoPesquisa3, idLabelPesquisa3, idCaixaPesquisa3, labelCampoPesquisa4, idLabelPesquisa4, idCaixaPesquisa4, labelCampoPesquisa5, idLabelPesquisa5, idCaixaPesquisa5, labelCampoPesquisa6, idLabelPesquisa6, idCaixaPesquisa6, labelCampoPesquisa7, idLabelPesquisa7, idCaixaPesquisa7, labelCampoPesquisa8, idLabelPesquisa8, idCaixaPesquisa8, labelCampoPesquisa9, idLabelPesquisa9, idCaixaPesquisa9, labelCampoPesquisa10, idLabelPesquisa10, idCaixaPesquisa10, labelCampoPesquisa11, idLabelPesquisa11, idCaixaPesquisa11, labelCampoPesquisa12, idLabelPesquisa12, idCaixaPesquisa12, labelCampoPesquisa13, idLabelPesquisa13, idCaixaPesquisa13, labelCampoPesquisa14, idLabelPesquisa14, idCaixaPesquisa14, labelCampoPesquisa15, idLabelPesquisa15, idCaixaPesquisa15, labelCampoPesquisa16, idLabelPesquisa16, idCaixaPesquisa16, labelCampoPesquisa17, idLabelPesquisa17, idCaixaPesquisa17, labelCampoPesquisa18, idLabelPesquisa18, idCaixaPesquisa18, labelCampoPesquisa19, idLabelPesquisa19, idCaixaPesquisa19, labelCampoPesquisa20, idLabelPesquisa20, idCaixaPesquisa20};

		//BOTAO 'Executar Consulta'
		String idBotaoLimpar = propertiesTela.getProperty("idBotaoLimpar");

		////TELA RESULTADOS
		boolean verificaCamposResultados = Boolean.parseBoolean(propertiesTela.getProperty("verificaCamposResultados"));
		int qtdeCamposResultados = Integer.parseInt(propertiesTela.getProperty("qtdeCamposResultados"));

		String idBotaoConsultar = propertiesTela.getProperty("idBotaoConsultar");
		String idBotaoNovo = propertiesTela.getProperty("idBotaoNovo");
		String idBotaoCriarCopia = propertiesTela.getProperty("idBotaoCriarCopia");
		String idBotaoEditar = propertiesTela.getProperty("idBotaoEditar");
		String idBotaoExcluir = propertiesTela.getProperty("idBotaoExcluir");
		String idBotaoExportar = propertiesTela.getProperty("idBotaoExportar");

		String[] idBotoesResultados = {idBotaoConsultar, idBotaoNovo, idBotaoCriarCopia, idBotaoEditar, idBotaoExcluir, idBotaoExportar};

		//CAMPO 1
		String labelColuna1 = propertiesTela.getProperty("labelColuna1");
		String xpathColuna1 = propertiesTela.getProperty("xpathColuna1");

		//CAMPO 2
		String labelColuna2 = propertiesTela.getProperty("labelColuna2");
		String xpathColuna2 = propertiesTela.getProperty("xpathColuna2");

		//CAMPO 3
		String labelColuna3 = propertiesTela.getProperty("labelColuna3");
		String xpathColuna3 = propertiesTela.getProperty("xpathColuna3");

		//CAMPO 4
		String labelColuna4 = propertiesTela.getProperty("labelColuna4");
		String xpathColuna4 = propertiesTela.getProperty("xpathColuna4");

		//CAMPO 5
		String labelColuna5 = propertiesTela.getProperty("labelColuna5");
		String xpathColuna5 = propertiesTela.getProperty("xpathColuna5");

		//CAMPO 6
		String labelColuna6 = propertiesTela.getProperty("labelColuna6");
		String xpathColuna6 = propertiesTela.getProperty("xpathColuna6");

		//CAMPO 7
		String labelColuna7 = propertiesTela.getProperty("labelColuna7");
		String xpathColuna7 = propertiesTela.getProperty("xpathColuna7");

		//CAMPO 8
		String labelColuna8 = propertiesTela.getProperty("labelColuna8");
		String xpathColuna8 = propertiesTela.getProperty("xpathColuna8");

		//CAMPO 9
		String labelColuna9 = propertiesTela.getProperty("labelColuna9");
		String xpathColuna9 = propertiesTela.getProperty("xpathColuna9");

		//CAMPO 10
		String labelColuna10 = propertiesTela.getProperty("labelColuna10");
		String xpathColuna10 = propertiesTela.getProperty("xpathColuna10");

		String[] camposTelaResultados = {labelColuna1, xpathColuna1, labelColuna2, xpathColuna2, labelColuna3, xpathColuna3, labelColuna4, xpathColuna4, labelColuna5, xpathColuna5, labelColuna6, xpathColuna6, labelColuna7, xpathColuna7, labelColuna8, xpathColuna8, labelColuna9, xpathColuna9, labelColuna10, xpathColuna10};

		////TELA CADASTRO
		boolean verificaCamposCadastro = Boolean.parseBoolean(propertiesTela.getProperty("verificaCamposCadastro"));
		int qtdeCamposCadastro = Integer.parseInt(propertiesTela.getProperty("qtdeCamposCadastro"));

		//CAMPO 1 
		String idLabelCampo1 = propertiesTela.getProperty("idLabelCampo1");
		String idCaixaCampo1 = propertiesTela.getProperty("idCaixaCampo1");
		String labelCampo1 = propertiesTela.getProperty("labelCampo1");

		//CAMPO 2 
		String idLabelCampo2 = propertiesTela.getProperty("idLabelCampo2");
		String idCaixaCampo2 = propertiesTela.getProperty("idCaixaCampo2");
		String labelCampo2 = propertiesTela.getProperty("labelCampo2");

		//CAMPO 3 
		String idLabelCampo3 = propertiesTela.getProperty("idLabelCampo3");
		String idCaixaCampo3 = propertiesTela.getProperty("idCaixaCampo3");
		String labelCampo3 = propertiesTela.getProperty("labelCampo3");

		//CAMPO 4 
		String idLabelCampo4 = propertiesTela.getProperty("idLabelCampo4");
		String idCaixaCampo4 = propertiesTela.getProperty("idCaixaCampo4");
		String labelCampo4 = propertiesTela.getProperty("labelCampo4");

		//CAMPO 5 
		String idLabelCampo5 = propertiesTela.getProperty("idLabelCampo5");
		String idCaixaCampo5 = propertiesTela.getProperty("idCaixaCampo5");
		String labelCampo5 = propertiesTela.getProperty("labelCampo5");

		//CAMPO 6 
		String idLabelCampo6 = propertiesTela.getProperty("idLabelCampo6");
		String idCaixaCampo6 = propertiesTela.getProperty("idCaixaCampo6");
		String labelCampo6 = propertiesTela.getProperty("labelCampo6");

		//CAMPO 7 
		String idLabelCampo7 = propertiesTela.getProperty("idLabelCampo7");
		String idCaixaCampo7 = propertiesTela.getProperty("idCaixaCampo7");
		String labelCampo7 = propertiesTela.getProperty("labelCampo7");

		//CAMPO 8 
		String idLabelCampo8 = propertiesTela.getProperty("idLabelCampo8");
		String idCaixaCampo8 = propertiesTela.getProperty("idCaixaCampo8");
		String labelCampo8 = propertiesTela.getProperty("labelCampo8");

		//CAMPO 9 
		String idLabelCampo9 = propertiesTela.getProperty("idLabelCampo9");
		String idCaixaCampo9 = propertiesTela.getProperty("idCaixaCampo9");
		String labelCampo9 = propertiesTela.getProperty("labelCampo9");

		//CAMPO 10
		String idLabelCampo10 = propertiesTela.getProperty("idLabelCampo10");
		String idCaixaCampo10 = propertiesTela.getProperty("idCaixaCampo10");
		String labelCampo10 = propertiesTela.getProperty("labelCampo10");

		//CAMPO 11
		String idLabelCampo11 = propertiesTela.getProperty("idLabelCampo11");
		String idCaixaCampo11 = propertiesTela.getProperty("idCaixaCampo11");
		String labelCampo11 = propertiesTela.getProperty("labelCampo11");

		//CAMPO 12
		String idLabelCampo12 = propertiesTela.getProperty("idLabelCampo12");
		String idCaixaCampo12 = propertiesTela.getProperty("idCaixaCampo12");
		String labelCampo12 = propertiesTela.getProperty("labelCampo12");

		//CAMPO 13
		String idLabelCampo13 = propertiesTela.getProperty("idLabelCampo13");
		String idCaixaCampo13 = propertiesTela.getProperty("idCaixaCampo13");
		String labelCampo13 = propertiesTela.getProperty("labelCampo13");

		//CAMPO 14
		String idLabelCampo14 = propertiesTela.getProperty("idLabelCampo14");
		String idCaixaCampo14 = propertiesTela.getProperty("idCaixaCampo14");
		String labelCampo14 = propertiesTela.getProperty("labelCampo14");

		//CAMPO 15
		String idLabelCampo15 = propertiesTela.getProperty("idLabelCampo15");
		String idCaixaCampo15 = propertiesTela.getProperty("idCaixaCampo15");
		String labelCampo15 = propertiesTela.getProperty("labelCampo15");

		//CAMPO 16
		String idLabelCampo16 = propertiesTela.getProperty("idLabelCampo16");
		String idCaixaCampo16 = propertiesTela.getProperty("idCaixaCampo16");
		String labelCampo16 = propertiesTela.getProperty("labelCampo16");

		//CAMPO 17
		String idLabelCampo17 = propertiesTela.getProperty("idLabelCampo17");
		String idCaixaCampo17 = propertiesTela.getProperty("idCaixaCampo17");
		String labelCampo17 = propertiesTela.getProperty("labelCampo17");

		//CAMPO 18
		String idLabelCampo18 = propertiesTela.getProperty("idLabelCampo18");
		String idCaixaCampo18 = propertiesTela.getProperty("idCaixaCampo18");
		String labelCampo18 = propertiesTela.getProperty("labelCampo18");

		//CAMPO 19
		String idLabelCampo19 = propertiesTela.getProperty("idLabelCampo19");
		String idCaixaCampo19 = propertiesTela.getProperty("idCaixaCampo19");
		String labelCampo19 = propertiesTela.getProperty("labelCampo19");

		//CAMPO 20
		String idLabelCampo20 = propertiesTela.getProperty("idLabelCampo20");
		String idCaixaCampo20 = propertiesTela.getProperty("idCaixaCampo20");
		String labelCampo20 = propertiesTela.getProperty("labelCampo20");

		String[] camposCadastro = {idLabelCampo1, idCaixaCampo1, labelCampo1, idLabelCampo2, idCaixaCampo2, labelCampo2, idLabelCampo3, idCaixaCampo3, labelCampo3, idLabelCampo4, idCaixaCampo4, labelCampo4, idLabelCampo5, idCaixaCampo5, labelCampo5, idLabelCampo6, idCaixaCampo6, labelCampo6, idLabelCampo7, idCaixaCampo7, labelCampo7, idLabelCampo8, idCaixaCampo8, labelCampo8, idLabelCampo9, idCaixaCampo9, labelCampo9, idLabelCampo10, idCaixaCampo10, labelCampo10, idLabelCampo11, idCaixaCampo11, labelCampo11, idLabelCampo12, idCaixaCampo12, labelCampo12, idLabelCampo13, idCaixaCampo13, labelCampo13, idLabelCampo14, idCaixaCampo14, labelCampo14, idLabelCampo15, idCaixaCampo15, labelCampo15, idLabelCampo16, idCaixaCampo16, labelCampo16, idLabelCampo17, idCaixaCampo17, labelCampo17, idLabelCampo18, idCaixaCampo18, labelCampo18, idLabelCampo19, idCaixaCampo19, labelCampo19, idLabelCampo20, idCaixaCampo20, labelCampo20};

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

		String[] valoresCadastro = {valorCadastro1, valorCadastro2, valorCadastro3, valorCadastro4, valorCadastro5, valorCadastro6, valorCadastro7, valorCadastro8, valorCadastro9, valorCadastro10, valorCadastro10, valorCadastro11, valorCadastro12, valorCadastro13, valorCadastro14, valorCadastro15, valorCadastro16, valorCadastro17, valorCadastro18, valorCadastro19, valorCadastro20};
		String[] camposTelaCadastro = {idCampoCadastro1, idCampoCadastro2, idCampoCadastro3, idCampoCadastro4, idCampoCadastro5, idCampoCadastro6, idCampoCadastro7, idCampoCadastro8, idCampoCadastro9, idCampoCadastro10, idCampoCadastro11, idCampoCadastro12, idCampoCadastro13, idCampoCadastro14, idCampoCadastro15, idCampoCadastro16, idCampoCadastro17, idCampoCadastro18, idCampoCadastro19, idCampoCadastro20};

		String[] camposRegistro = {"!campo1", "!campo2", "!campo3", "!campo4", "!campo5", "!campo6", "!campo7", "!campo8", "!campo9", "!campo10", "!campo11", "!campo12", "!campo13", "!campo14", "!campo15", "!campo16", "!campo17", "!campo18", "!campo19", "!campo20"};
		String[] informacoesRegistro = {campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9, campo10, campo11, campo12, campo13, campo14, campo15, campo16, campo17, campo18, campo19, campo20};

		VerificacoesDeIntegracao integracao = new VerificacoesDeIntegracao();
		VerificacoesDeTela automacao = new VerificacoesDeTela();

		//ROTEIRO DE INTEGRACAO

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

		integracao.verficaServicoIntegracaoIniciado(nomeDoServicoIntegrador);
		integracao.integraRegistro(arquivoIntegracao, pastasIntegracao, camposRegistro, informacoesRegistro);

		// ACESSA TELA
		automacao.acessaSistema(driver, tentativas, url, usuario, senha, navegador, nomeTeste);
		automacao.efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		automacao.acessaModuloECF(driver, tentativas, xpathModulo, nomeTeste);
		automacao.aguardaCarregamento("Home", xpathHome, nomeTeste, tentativas, driver);
		automacao.acessaTelaPorClick2(driver, qtdeMenu, xpathMenu1, xpathMenu2, xpathMenu3, xpathMenu4, xpathTela, nomeTeste, labelMenu1, labelMenu2, labelMenu3, labelMenu4, labelTela, qtdeMenu, tentativas);
		automacao.aguardaCarregamento(caminho, xPathCarregaPesquisa, nomeTeste, tentativas, driver);

		//PESQUISA DOCUMENTO
		automacao.pesquisaRegistroIntegrado(driver, tentativas, qtdePesquisa, camposPesquisa, valoresPesquisa, idBotaoExecutarConsulta);
		automacao.verificaResultadoDaPesquisa(driver, tentativas, qtdeResultados, colunasResultados, valoresResultados, idBotoesResultados);
		automacao.verificaTelaCadastro(driver, tentativas, qtdeCadastro, camposTelaCadastro, valoresCadastro);

		duracaoTeste = System.currentTimeMillis() - inicio;
		automacao.informaTerminoDoTeste(nomeTeste, categoria, duracaoTeste);
	}
	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
