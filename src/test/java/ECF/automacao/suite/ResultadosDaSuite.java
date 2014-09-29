package ECF.automacao.suite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.log4j.Logger;

import ECF.automacaoECF.padrao.FuncionalidadesUteis;
import ECF.automacaoECF.padrao.RecebeParametros;
import ECF.automacaoECF.padrao.ZipUtils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResultadosDaSuite {

	FuncionalidadesUteis utilidade = new FuncionalidadesUteis();
	static org.apache.log4j.Logger logger = Logger.getLogger(ResultadosDaSuite.class.getName());
	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	int tentativas = Integer.parseInt(numerotentativas);
	public String tempoMedio = new RecebeParametros().tempoMedio;
	int tempoMedioAceitavel = Integer.parseInt(tempoMedio);
	public String testaTela = new RecebeParametros().testaTela;
	public String testaCRUD = new RecebeParametros().testaCRUD;
	public String testaIntegracao = new RecebeParametros().testaIntegracao;
	public String geraData() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		return (df.format(c.getTime()));
	}

	public String[] imprimeResultadoNaTela(Class<?>[] teste, String[] resultados, String categoria, String[] duracaoTestes) {
		String[] resultadoParaImprimir = new String[duracaoTestes.length];

		for (int i = 0; i < teste.length; i++) {
			long duracaoParaFormatar = Long.parseLong(duracaoTestes[i]);
			String duracaoFormatada = utilidade.formataDuracaoResumida(duracaoParaFormatar);

			logger.info(String.format("%-60s %10s %20s %20s", teste[i].getSimpleName(), resultados[i], categoria, duracaoFormatada));
			resultadoParaImprimir[i] = (String.format("%-60s %10s %20s %20s", teste[i].getSimpleName(), resultados[i], categoria, duracaoFormatada));
		}
		return resultadoParaImprimir;
	}

	public void imprimeTitulo() {

		
		logger.info("************************************************************************");
		logger.info("AUTOMACAO | ONESOURCE ECF");
		logger.info("************************************************************************");
		
		logger.info("------------------------------------------------------------------------");
		logger.info("SUITE DE TESTE INICIADA EM " + geraData().toUpperCase());
		logger.info("------------------------------------------------------------------------");

	}

	public void imprimeParametrosGeraisDaSuite() {

		logger.info("PARÂMETROS GERAIS: ");
		logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("URL: " + url);
		logger.info("USUARIO: " + usuario);
		logger.info("SENHA: " + senha);
		logger.info("NAVEGADOR: " + url);
		logger.info("QTDE DE TENTATIVAS: " + tentativas);
		logger.info("TEMPO ACEITÁVEL DE CARREGAMENTO: " + tentativas + " (milissegundos)");

	}

	public void imprimeParametrosDaSuite() {

		logger.info("PARÂMETROS DA SUITE: ");
		logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("TESTES DE TELA (testaTela): " + testaTela);
		logger.info("TESTES DE CRUD (testaCRUD): " + testaCRUD);
		logger.info("TESTES DE INTEGRAÇÃO (testaIntegracao): " + testaIntegracao);
		logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("");
		logger.info("");

	}

	public String imprimeCabecalhoDeResultado(Class<?>[] testesTela, Class<?>[] testesCrud, Class<?>[] testesIntegracao, Boolean testaTelaBoolean, Boolean testaCRUDBoolean, Boolean testaIntegracaoBoolean, int qtdeSucesso, int qtdeFalhas) {
		String[] cabecalhoLinha = {"", "", "", ""};
		String cabecalho;
		int totalDeTestes = qtdeSucesso + qtdeFalhas;

		//para a tela
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));
		logger.info("RESULTADOS DA SUITE EM " + geraData().toUpperCase());
		logger.info("TOTAL DE TESTES: " + totalDeTestes + " | FALHA: " + qtdeFalhas + " | SUCESSO: " + qtdeSucesso);
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));

		//para o arquivo
		cabecalhoLinha[0] = "--------------------------------------------------------------------------------------------------------------------------------";
		cabecalhoLinha[1] = "RESULTADOS DA SUITE EM " + geraData().toUpperCase() + "\n";
		cabecalhoLinha[2] = "TOTAL DE TESTES: " + totalDeTestes + " | FALHA: " + qtdeFalhas + " | SUCESSO: " + qtdeSucesso;
		cabecalhoLinha[3] = "--------------------------------------------------------------------------------------------------------------------------------";

		cabecalho = cabecalhoLinha[0] + "\n" + cabecalhoLinha[1] + "\n" + cabecalhoLinha[2] + "\n" + cabecalhoLinha[3] + "\n";

		return cabecalho;

	}
	public String imprimeCabecalhoDaTabelaDeResultados() {
		String[] cabecalhoLinha = {"", "", ""};
		String cabecalhoTestes;

		//para a tela
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));
		logger.info(String.format("%-60s %10s %20s %20s", "TESTE", "RESULTADO", "CATEGORIA", "DURAÇÃO"));
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));

		//para o arquivo
		cabecalhoLinha[0] = "--------------------------------------------------------------------------------------------------------------------------------";
		cabecalhoLinha[1] = String.format("%-60s %10s %20s %20s", "TESTE", "RESULTADO", "CATEGORIA", "DURAÇÃO");
		cabecalhoLinha[2] = "--------------------------------------------------------------------------------------------------------------------------------";

		cabecalhoTestes = cabecalhoLinha[0] + "\n" + cabecalhoLinha[1] + "\n" + cabecalhoLinha[2] + "\n";
		return cabecalhoTestes;

	}

	public String imprimeDuracaoDaSuite(long duracaoTeste) {
		String[] rodapeLinha = {"", "", ""};
		String rodapeTestes;
		//para a tela
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));
		logger.info("SUITE DE TESTE EXECUTADA EM: " + utilidade.formataDuracao(duracaoTeste));
		logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));

		//para o arquivo
		rodapeLinha[0] = "--------------------------------------------------------------------------------------------------------------------------------";
		rodapeLinha[1] = "SUITE DE TESTE EXECUTADA EM: " + utilidade.formataDuracao(duracaoTeste);
		rodapeLinha[2] = "--------------------------------------------------------------------------------------------------------------------------------";

		rodapeTestes = rodapeLinha[0] + "\n" + rodapeLinha[1] + "\n" + rodapeLinha[2] + "\n";
		return rodapeTestes;

	}

	public File gravaResultadosTxt(Boolean testaTelaBoolean, Boolean testaCRUDBoolean, Boolean testaIntegracaoBoolean, String cabecalhoDeResultado, String cabecalhoDaTabela, String[] resultadoParaImprimirTela, String[] resultadoParaImprimirCrud, String[] resultadoParaImprimirIntegracao, String rodapeTestes, String[] parametrosDeTeste) throws IOException {

		Calendar c = Calendar.getInstance();

		String diretorioResultado = "./resultados/";
		String prefixoNome = "resultadosAutomacao_";
		String nomeArquivo = c.getTimeInMillis() + ".txt";
		String arquivoResultado = diretorioResultado + prefixoNome + nomeArquivo;

		File file = new File(arquivoResultado);

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("AUTOMACAO | ONESOURCE ECF\n");
		bw.write(cabecalhoDeResultado);
		bw.write(cabecalhoDaTabela);
		if (testaTelaBoolean == true)
			for (int i = 0; i < resultadoParaImprimirTela.length; i++)
				bw.write(resultadoParaImprimirTela[i] + "\n");

		if (testaCRUDBoolean == true)
			for (int i = 0; i < resultadoParaImprimirCrud.length; i++)
				bw.write(resultadoParaImprimirCrud[i] + "\n");

		if (testaIntegracaoBoolean == true)
			for (int i = 0; i < resultadoParaImprimirIntegracao.length; i++)
				bw.write(resultadoParaImprimirIntegracao[i] + "\n");

		bw.write(rodapeTestes);
		bw.write("--------------------------------------------------------------------------------------------------------------------------------");
		bw.write("\nPARAMETROS DA AUTOMACAO: \n");
		bw.write("AMBIENTE: " + parametrosDeTeste[0] + "\n");
		bw.write("NAVEGADOR: " + parametrosDeTeste[1] + "\n");
		bw.write("VERSAO: " + parametrosDeTeste[2] + "\n");
		bw.write("--------------------------------------------------------------------------------------------------------------------------------");
		bw.close();

		return file;
	}

	public String retornaResultadoParaString(Class<?>[] teste, String[] resultados, String categoria) {

		String resultadosParaArquivo = "";
		String[] resultadosParaArquivoPart = {"", "", "", ""};

		for (int i = 0; i < teste.length; i++) {
			resultadosParaArquivoPart[i] = String.format("%-60s %10s %20s %20s", teste[i].getSimpleName(), resultados[i], categoria, "20");
		}

		for (int u = 0; u < resultadosParaArquivoPart.length; u++) {
			resultadosParaArquivo = resultadosParaArquivoPart[u] + "\n";
		}

		return resultadosParaArquivo;

	}

	public void informaQuantidadeTestes(Class<?>[] testesTela, Class<?>[] testesCrud, Class<?>[] testesIntegracao, Boolean testaTelaBoolean, Boolean testaCRUDBoolean, Boolean testaIntegracaoBoolean) {
		int QuantidadeTela = 0;
		int QuantidadeCRUD = 0;
		int QuantidadeIntegracao = 0;

		if (testaTelaBoolean == true)
			QuantidadeTela = testesTela.length;

		if (testaCRUDBoolean == true)
			QuantidadeCRUD = testesCrud.length;

		if (testaIntegracaoBoolean == true)
			QuantidadeIntegracao = testesIntegracao.length;

		int totalTestes = QuantidadeTela + QuantidadeCRUD + QuantidadeIntegracao;

		logger.info("---------------------------------------------------------------------------------------------------");
		logger.info("TESTES: " + totalTestes);
		logger.info("---------------------------------------------------------------------------------------------------");

	}

	public String montaCorpoEmail(File corpoEmail) {
		String returnValue = "";
		FileReader file;
		String line = "";
		try {
			file = new FileReader(corpoEmail);
			BufferedReader reader = new BufferedReader(file);
			try {
				while ((line = reader.readLine()) != null) {
					returnValue += line + "\n";
				}
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		}

		return returnValue;

	}

	public String montaAssuntoEmail(String cabecalhoDeResultado) {
		//TODO montar este metodo
		return "AUTOMACAO DE TESTES | ONESOURCE ECF";
	}

	public void enviaEmail(String destinatarios, String assuntoEmail, String corpoEmailString) throws IOException {

		final String username = "tr.automation.webdriver@gmail.com";
		final String password = "viladopombo";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			//			FileDataSource fileDataSource = new FileDataSource(anexoEvidencias);
			//			MimeBodyPart attachmentPart = new MimeBodyPart();
			//			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			//			attachmentPart.setFileName(fileDataSource.getName());
			//
			//			Multipart multipart = new MimeMultipart();
			//			multipart.addBodyPart(attachmentPart);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("tr.automation.webdriver@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarios));
			message.setSubject(assuntoEmail);
			message.setText(corpoEmailString);
			//			message.setContent(multipart);

			Transport.send(message);
			logger.info("--------------------------------------------------------------------------------------------------------------------------------");
			logger.info("EMAIL COM OS RESULTADOS DO TESTE ENVIADO COM SUCESSO!");
			logger.info("--------------------------------------------------------------------------------------------------------------------------------");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String[] coletaInformacoesGeraisDoTeste() {
		String[] informacoesGerais = {"", "", ""};

		informacoesGerais[0] = url;
		informacoesGerais[1] = navegador;
		informacoesGerais[2] = "----";

		return informacoesGerais;
	}

	public void preparaSuite() {



	}

	private void apagaScreenshot() {
		FuncionalidadesUteis util = new FuncionalidadesUteis();
		util.remover(new File("./screenshot"));

	}

	public void enviaEmailComAnexos(String destinatarios, String assuntoEmail, String corpoEmailString) throws UnsupportedEncodingException {

		FuncionalidadesUteis utilidade = new FuncionalidadesUteis();

		String to = destinatarios;
		String from = "tr.automation.webdriver@gmail.com";

		final String username = "tr.automation.webdriver@gmail.com";
		final String password = "viladopombo";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from,"AUTOMACAO"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(assuntoEmail);

			BodyPart messageBodyPart = new MimeBodyPart();
			BodyPart messageBodyPart2 = new MimeBodyPart();

			messageBodyPart.setText(corpoEmailString);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = "./logs/automation.log";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("automation.log");

			if (utilidade.pastaVazia("./evidencias") == false) {

				String anexoEvidencias = zipaEvidencias();
				messageBodyPart2 = new MimeBodyPart();
				String filename2 = anexoEvidencias;
				DataSource source2 = new FileDataSource(filename2);
				messageBodyPart2.setDataHandler(new DataHandler(source2));
				messageBodyPart2.setFileName("evidencias.zip");
				multipart.addBodyPart(messageBodyPart2);
			}

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);

			logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));
			logger.info("E-MAIL COM AS INFORMAÇÕES DA SUITE ENVIADO COM SUCESSO! ");
			logger.info(String.format("--------------------------------------------------------------------------------------------------------------------------------"));

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String zipaEvidencias() {
		String[] u = null;
		ZipUtils.main(u);
		String evidenciasZip = "./files/temp/ZipToSend/evidencias.zip";
		return evidenciasZip;
	}

}
