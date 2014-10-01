package ECF.automacaoECF.padrao;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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
	RecebeParametros parametros;
	@SuppressWarnings("unused")
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

	public String enviaRequest(String nomeIntegracao, String arquivosEnvio, String enderecoWSDL, String nomeOperation, String password, String username, String wssPasswordType) throws XmlException, IOException, SoapUIException, SubmitException {
		manipulaArquivos = new ManipuladorDeArquivos();

		System.out.println("Limpando a pasta temporária: " + "./files/requestWS/temp/");
		manipulaArquivos.limpaPastas("./files/requestWS/temp/");

		String requestDoArquivo = converteXmlParaString("./files/requestWS/" + nomeIntegracao + "/envio/" + arquivosEnvio);
		System.out.println("Arquivo de request: " + requestDoArquivo);

		String resposta = enviaRequestParaWS(enderecoWSDL, username, password, wssPasswordType, requestDoArquivo, nomeOperation);
		System.out.println("RESPOSTA: " + resposta);

		System.out.println("Granvando o arquivo de retorno ...");
		String arquivoRetornado = "./files/requestWS/temp/" + manipulaArquivos.retornaNomeEmData() + "_" + arquivosEnvio;
		manipulaArquivos.gravaArquivoDeUmaString(arquivoRetornado, resposta);

		return arquivoRetornado;

	}

	@SuppressWarnings("rawtypes")
	private String enviaRequestParaWS(String enderecoWSDL, String username, String password, String wssPasswordType, String requestDoArquivo, String nomeOperation) throws SoapUIException, SubmitException, XmlException, IOException {
		parametros = new RecebeParametros();
		WsdlProject project = new WsdlProject();
		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, parametros.urlIntegracao + enderecoWSDL, true)[0];
		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(nomeOperation);
		WsdlRequest request = operation.addNewRequest("My Request");
		request.setPassword(password);
		request.setUsername(username);
		request.setWssPasswordType(wssPasswordType);
		request.setRequestContent(requestDoArquivo);
		WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
		Response response = submit.getResponse();
		String resposta = response.getContentAsString();
		return resposta;

	}

	public String enviaRequestDoProtocolo(String nomeIntegracao, String arquivoRetornoRequest, String enderecoWSDL, String nomeOperation, String password, String username, String wssPasswordType, int tentativas) throws XmlException, IOException, SoapUIException, SubmitException {

		manipulaArquivos = new ManipuladorDeArquivos();
		String protocoloObtido = pegaValorDeTag(arquivoRetornoRequest, "PROTOCOL_NUMBER");

		System.out.println("Copiando arquivo modelo de request protocolo '" + nomeIntegracao + "'...");
		String arquivoTemporario = "./files/requestWS/temp/" + manipulaArquivos.retornaNomeEmData() + "_prt_" + nomeIntegracao + ".xml";
		String modeloArquivoProtocolo = "./files/requestWS/modeloRequestProtocolo.xml";

		manipulaArquivos.copiaArquivos(modeloArquivoProtocolo, arquivoTemporario);
		System.out.println("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

		// SUBSTITUIÇÃO DE DO PROTOCOLO NO MODELO
		String[] campos = {"!protocolo"};
		String[] informacoes = {protocoloObtido};
		substituiValorNoXml(arquivoTemporario, campos, informacoes);

		// TRANSFORMANDO O ARQUIVO EM REQUEST
		String requestDoArquivo = converteXmlParaString(arquivoTemporario);
		System.out.println("Arquivo de request: \n" + requestDoArquivo);

		System.out.println("HERE: \n" + pegaValorDeTagXML(enviaRequestParaWS(enderecoWSDL, username, password, wssPasswordType, requestDoArquivo, nomeOperation), "RESPONSE_MESSAGE"));

		for (int i = 0; i < tentativas; i++) {
			String responseString = enviaRequestParaWS(enderecoWSDL, username, password, wssPasswordType, requestDoArquivo, nomeOperation);
			String mensagemRecebida = pegaValorDeTagXML(responseString, "RESPONSE_MESSAGE");

			if (mensagemRecebida.contentEquals("Aguarde um momento. O registro está sendo integrado.") || mensagemRecebida.contentEquals("Protocolo não encontrado: . Aguarde alguns instantes e tente novamente.")) {
				System.out.println(mensagemRecebida);
			} else {
				System.out.println("Mensagem Recebida: " + mensagemRecebida);
				System.out.println("Granvando o arquivo de retorno ...");

				manipulaArquivos.gravaArquivoDeUmaString("./files/requestWS/temp/" + manipulaArquivos.retornaNomeEmData() + "_res_" + nomeIntegracao + ".xml", responseString);

				return "./files/requestWS/temp/" + manipulaArquivos.retornaNomeEmData() + "_res_" + nomeIntegracao + ".xml";
			}
			
			//TODO COLOCAR QUEBRA SE PASSAR DAS TENTATIVAS
		}

		return null;

	}

	public int comparaResponseObtidoComEsperado(String arquivoEsperado, String arquivoObtido, String nomeIntegracao, String arquivosEnvio) throws IOException {
		int qtdeFalhas = 0;
		manipulaArquivos = new ManipuladorDeArquivos();
		String enderecoDoArquivoEsperado = "./files/requestWS/" + nomeIntegracao + "/retornoEsperado/" + arquivoEsperado;

		String xmlEsperadoEmString = converterArquivoXmlParaString(enderecoDoArquivoEsperado);
		String xmlObtidoEmString = converterArquivoXmlParaString(arquivoObtido);

		String responseMessageEsperado = pegaValorDeTagXML(xmlEsperadoEmString, "RESPONSE_MESSAGE");
		String responseMessageObtido = pegaValorDeTagXML(xmlObtidoEmString, "RESPONSE_MESSAGE");

		if (responseMessageObtido.contentEquals(responseMessageEsperado)) {
			System.out.println("#OK# Response Obtido igual ao esperado!");
			System.out.println("Obtido: " + responseMessageObtido);
			System.out.println("Esperado: " + responseMessageEsperado);
		} else {

			System.out.println("#ERRO# Response Obtido diferente do esperado!");
			System.out.println("Obtido: " + responseMessageObtido);
			System.out.println("Esperado: " + responseMessageEsperado);

			manipulaArquivos.copiaArquivos(enderecoDoArquivoEsperado, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_responseEsperado" + ".xml");
			manipulaArquivos.copiaArquivos(arquivoObtido, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_responseObtido" + ".xml");
			manipulaArquivos.copiaArquivos("./files/requestWS/" + nomeIntegracao + "/envio/" + arquivosEnvio, "./evidencias/WS/" + manipulaArquivos.retornaNomeEmData() + "_" + nomeIntegracao + "_request" + ".xml");
			qtdeFalhas++;
		}

		return qtdeFalhas;

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

	public void verificaErros(int falhasWS) throws IOException {

		System.out.println("Falhas: " + falhasWS);
		if (falhasWS > 0) {
			falhaWS("Encontrato erros nas consistencias");
		}

	}

	public void falhaWS(String mensagem) throws IOException {

		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");
		logger.info("FALHA: " + mensagem);
		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");

		fail(mensagem + " - Evidencias gravadas no diretório ./evidencias/WS");

	}
}
