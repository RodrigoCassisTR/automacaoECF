package ECF.automacaoECF.padrao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.jdom.input.SAXBuilder;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.parser.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

		String protocoloObtido = pegaValorDeTag(arquivoRetornado, "PROTOCOL_NUMBER");

		System.out.println("OLHA O QUE VC PRECISA");
		System.out.println(protocoloObtido);
		
		return arquivoRetornado;
		//
		// String[] campos = {"!protocolo"};
		// String[] informacoes = {protocoloObtido};
		//
		// substituiValorNoXml("D:/automacao/automacaoECF/files/requestWS/modeloRequestProtocolo.xml",
		// campos, informacoes);

	}
	
	
	public void enviaRequestDoProtocolo(String nomeIntegracao, String arquivoRetornoRequest, String enderecoWSDL, String nomeOperation, String password, String username, String wssPasswordType) throws XmlException, IOException, SoapUIException, SubmitException {
		VerificacoesDeIntegracao integracao=new VerificacoesDeIntegracao();
		
		// COPIA O ARQUIVO MODELO PARA UMA PASTA TEMPORARIA...
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		logger.info("Copiando arquivo modelo '" + nomeIntegracao + "'...");
		String diretorioTemporario = "./files/requestWS/temp";
		String prefixoNome = "/protocolo_";
		String nomeArquivo = dateFormat.format(date) + ".xml";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
		
		integracao.copiaArquivos(arquivoRetornoRequest, arquivoTemporario);
		logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");
		
//		String requestDoArquivo = converteXmlParaString("./files/requestWS/" + nomeIntegracao + "/envio/" + arquivoRetornoRequest[0]);
//		logger.info("Arquivo de request: " + requestDoArquivo);
//
//		WsdlProject project = new WsdlProject();
//		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, enderecoWSDL, true)[0];
//		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(nomeOperation);
//		WsdlRequest request = operation.addNewRequest("My Request");
//		request.setPassword(password);
//		request.setUsername(username);
//		request.setWssPasswordType(wssPasswordType);
//		request.setRequestContent(requestDoArquivo);
//		WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
//		Response response = submit.getResponse();
//		String content = response.getContentAsString();
//
//		logger.info("Request para envio : ");
//		logger.info(request.getRequestContent());
//
//		logger.info("Response obtido : ");
//		logger.info(content);
//
//		String responseString = response.getContentAsString();
//
//		logger.info("Granvando o arquivo de retorno ...");
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
//		Date date = new Date();
//		String nomeArquivo = dateFormat.format(date) + ".xml";
//		String arquivoRetornado = "./files/requestWS/temp/retornoWS_" + arquivoRetornoRequest[0] + "_" + nomeArquivo;
//		File file = new File(arquivoRetornado);
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write(responseString);
//		bw.close();
//
//		String protocoloObtido = pegaValorDeTag(arquivoRetornado, "PROTOCOL_NUMBER");
//
//		System.out.println("OLHA O QUE VC PRECISA");
//		System.out.println(protocoloObtido);
//		
//		return arquivoRetornado;
		//
		// String[] campos = {"!protocolo"};
		// String[] informacoes = {protocoloObtido};
		//
		// substituiValorNoXml("D:/automacao/automacaoECF/files/requestWS/modeloRequestProtocolo.xml",
		// campos, informacoes);

	}
}
