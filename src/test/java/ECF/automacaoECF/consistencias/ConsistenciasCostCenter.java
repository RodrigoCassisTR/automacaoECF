package ECF.automacaoECF.consistencias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import ECF.automacaoECF.padrao.CasoDeTesteBasico;
import ECF.automacaoECF.padrao.VerificacoesDeIntegracao;
import ECF.automacaoECF.padrao.VerificacoesDeTela;
import ECF.automacaoECF.padrao.VerificacoesDeWS;

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

public class ConsistenciasCostCenter extends CasoDeTesteBasico {

	@Test
	public void enviaXmlDaIntegracao() throws XmlException, IOException, SoapUIException, SubmitException, ParserConfigurationException {
		
		String arquivoRetornoRequest;
		String nomeTeste = properties.getProperty("nomeTeste");
		String categoria = properties.getProperty("categoria");
		String nomeIntegracao = properties.getProperty("nomeIntegracao");
		String enderecoWSDL = properties.getProperty("enderecoWSDL");
		String nomeOperation = properties.getProperty("nomeOperation");
		String password = properties.getProperty("password");
		String username = properties.getProperty("username");
		String wssPasswordType = properties.getProperty("wssPasswordType");
		String[] arquivosEnvio = properties.getProperty("arquivosEnvio").split(";");
		String[] retornoEsperado = properties.getProperty("retornoEsperado").split(";");

		VerificacoesDeWS testeWS = new VerificacoesDeWS();
		VerificacoesDeTela automacao = new VerificacoesDeTela();

		automacao.informaTeste(0, "", nomeTeste);
		automacao.informaTeste(9, "", nomeTeste);
		arquivoRetornoRequest = testeWS.enviaRequest(nomeIntegracao, arquivosEnvio, enderecoWSDL, nomeOperation, password, username, wssPasswordType);
		testeWS.enviaRequestDoProtocolo(nomeIntegracao, arquivoRetornoRequest, enderecoWSDL, nomeOperation, password, username, wssPasswordType);

		System.out.println("\n\n\n\n\nOLHA O QUE EU PEGUEI: " + arquivoRetornoRequest);

	}
}
