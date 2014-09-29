package ECF.automacaoECF.padrao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.jsoup.*;
import org.jsoup.parser.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;

public class VerificacoesDeWS {
	public org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeWS.class.getName());
	ManipuladorDeArquivos manipulaArquivos;

	public String converteXmlParaString(String arquivoXml) {

		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(arquivoXml);
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
			String strFileContent = stringWriter.toString(); // This is string
																// data of xml
																// file

			return strFileContent;

		} catch (Exception e) {
			e.getMessage();
		}
		return null;

	}

	public String pegaValorDeTag(String enderecoXml, String tag) {
		VerificacoesDeWS xml = new VerificacoesDeWS();

		String str = xml.converteXmlParaString(enderecoXml);
		org.jsoup.nodes.Document doc = Jsoup.parse(str, "", Parser.xmlParser());
		String valorObtidoDaTag = doc.select(tag).text();
		return valorObtidoDaTag;

	}

	public String pegaValorDeTagXML(String xmlOrigem, String tag) {
		VerificacoesDeWS xml = new VerificacoesDeWS();

		org.jsoup.nodes.Document doc = Jsoup.parse(xmlOrigem, "", Parser.xmlParser());
		String valorObtidoDaTag = doc.select(tag).text();
		return valorObtidoDaTag;

	}

	public void substituiValorNoXml(String arquivoTemporario, String[] camposRegistro, String[] informacoesRegistro) throws IOException {

		Path path = Paths.get(arquivoTemporario);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);

		for (int count = 0; count < camposRegistro.length; count++) {
			content = content.replaceAll(camposRegistro[count], informacoesRegistro[count]);
			Files.write(path, content.getBytes(charset));
		}

	}

	public String enviaRequest(String nomeIntegracao, String[] arquivosEnvio, String enderecoWSDL, String nomeOperation, String password, String username, String wssPasswordType) throws XmlException, IOException, SoapUIException, SubmitException {

		VerificacoesDeIntegracao integrador = new VerificacoesDeIntegracao();

		// LIMPANDO PASTAS
		logger.info("Limpando a pasta temporária: " + "./files/requestWS/temp/");
		File pastaTempWS = new File("./files/requestWS/temp/");
		FileUtils.cleanDirectory(pastaTempWS);

		String requestDoArquivo = converteXmlParaString("./files/requestWS/" + nomeIntegracao + "/envio/" + arquivosEnvio[0]);
		logger.info("Arquivo de request: " + requestDoArquivo);

		WsdlProject project = new WsdlProject();
		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, enderecoWSDL, true)[0];
		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(nomeOperation);
		WsdlRequest request = operation.addNewRequest("My Request");
		request.setPassword(password);
		request.setUsername(username);
		request.setWssPasswordType(wssPasswordType);
		request.setRequestContent(requestDoArquivo);
		WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
		Response response = submit.getResponse();
		String content = response.getContentAsString();

		logger.info("Request para envio : ");
		logger.info(request.getRequestContent());

		logger.info("Response obtido : ");
		logger.info(content);

		String responseString = response.getContentAsString();

		logger.info("Granvando o arquivo de retorno ...");

		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		String nomeArquivo = dateFormat.format(date) + ".xml";
		String arquivoRetornado = "./files/requestWS/temp/retornoWS_" + arquivosEnvio[0] + "_" + nomeArquivo;
		File file = new File(arquivoRetornado);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(responseString);
		bw.close();

		return arquivoRetornado;

	}

	@SuppressWarnings("rawtypes")
	public String enviaRequestDoProtocolo(String nomeIntegracao, String arquivoRetornoRequest, String enderecoWSDL, String nomeOperation, String password, String username, String wssPasswordType, int tentativas) throws XmlException, IOException, SoapUIException, SubmitException {
		VerificacoesDeIntegracao integracao = new VerificacoesDeIntegracao();

		String protocoloObtido = pegaValorDeTag(arquivoRetornoRequest, "PROTOCOL_NUMBER");

		// COPIA O ARQUIVO MODELO PARA UMA PASTA TEMPORARIA...
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		logger.info("Copiando arquivo modelo '" + nomeIntegracao + "'...");
		String diretorioTemporario = "./files/requestWS/temp";
		String prefixoNome = "/protocolo_";
		String nomeArquivo = dateFormat.format(date) + ".xml";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;

		String modeloArquivoProtocolo = "./files/requestWS/modeloRequestProtocolo.xml";

		integracao.copiaArquivos(modeloArquivoProtocolo, arquivoTemporario);
		logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

		// SUBSTITUIÇÃO DE DO PROTOCOLO NO MODELO
		String[] campos = {"!protocolo"};
		String[] informacoes = {protocoloObtido};
		substituiValorNoXml(arquivoTemporario, campos, informacoes);

		// TRANSFORMANDO O ARQUIVO EM REQUEST
		String requestDoArquivo = converteXmlParaString(arquivoTemporario);
		logger.info("Arquivo de request: " + requestDoArquivo);

		for (int i = 0; i < tentativas; i++) {

			WsdlProject project = new WsdlProject();
			WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, "http://166.78.0.215:8092/taxbr-solution-webservices/services/TrTaxBrMessageResponseImplPort?wsdl", true)[0];
			WsdlOperation operation = (WsdlOperation) iface.getOperationByName("getResponseMessage");
			WsdlRequest request = operation.addNewRequest("My Request");
			request.setPassword(password);
			request.setUsername(username);
			request.setWssPasswordType(wssPasswordType);
			request.setRequestContent(requestDoArquivo);
			WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
			Response response = submit.getResponse();
			String content = response.getContentAsString();

			logger.info("Request para envio : ");
			logger.info(request.getRequestContent());

			logger.info("Response obtido : ");
			logger.info(content);

			String responseString = response.getContentAsString();

			System.out.println(responseString);

			String mensagemRecebida = pegaValorDeTagXML(response.getContentAsXml(), "RESPONSE_MESSAGE");
			if (mensagemRecebida.contentEquals("Aguarde um momento. O registro está sendo integrado.")) {

				logger.info(mensagemRecebida);

			} else {
				logger.info("Mensagem Recebida: " + mensagemRecebida);
				logger.info("Granvando o arquivo de retorno ...");
				String nomeArquivoRetorno = dateFormat.format(date) + ".xml";
				String arquivoRetornado = "./files/requestWS/temp/retornoWS_" +
						nomeArquivoRetorno + "_retorno.xml";
				File file = new File(arquivoRetornado);
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(responseString);
				bw.close();
				return arquivoRetornado;
			}
		}

		return null;

	}

	public void comparaResponseObtidoComEsperado(String arquivoEsperado, String arquivoObtido, String nomeIntegracao, String arquivosEnvio) throws IOException {
		manipulaArquivos = new ManipuladorDeArquivos();
		String enderecoDoArquivoEsperado = "./files/requestWS/" + nomeIntegracao + "/retornoEsperado/" + arquivoEsperado;

		String xmlEsperadoEmString = converterArquivoXmlParaString(enderecoDoArquivoEsperado);
		String xmlObtidoEmString = converterArquivoXmlParaString(arquivoObtido);

		String responseMessageEsperado = pegaValorDeTagXML(xmlEsperadoEmString, "RESPONSE_MESSAGE");
		String responseMessageObtido = pegaValorDeTagXML(xmlObtidoEmString, "RESPONSE_MESSAGE");

		if (responseMessageObtido.contentEquals(responseMessageEsperado)) {
			System.out.println("SÂO IGUAIS!!!");
		} else {

			System.out.println("SÂO DIFERENTES!!!");
			manipulaArquivos.copiaArquivos(enderecoDoArquivoEsperado, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_responseEsperado" + ".xml");
			manipulaArquivos.copiaArquivos(arquivoObtido, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_responseObtido" + ".xml");
			manipulaArquivos.copiaArquivos("./files/requestWS/" + nomeIntegracao + "/envio/" + arquivosEnvio, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_request" + ".xml");

		}

		System.out.println("AQUI EU PRECISO COMPARAR: ");

		System.out.println("MENSAGEM ESPERADO: " + responseMessageEsperado + "\nARQUIVO: \n" + enderecoDoArquivoEsperado);
		System.out.println("MENSAGEM OBTIDO: " + responseMessageObtido + "\nARQUIVO: \n" + arquivoObtido);

	}

	private String converterArquivoXmlParaString(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		reader.close();

		return stringBuilder.toString();
	}
}
