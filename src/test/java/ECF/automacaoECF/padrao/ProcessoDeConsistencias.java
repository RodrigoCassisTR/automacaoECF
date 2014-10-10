package ECF.automacaoECF.padrao;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.XmlException;
import org.junit.After;
import org.junit.Test;

import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.support.SoapUIException;

public class ProcessoDeConsistencias extends CasoDeTesteBasico {
	public String numerotentativas = new RecebeParametros().numerotentativas;
	int tentativas = Integer.parseInt(numerotentativas);

	@Test
	public void enviaXmlDaIntegracao() throws XmlException, IOException, SoapUIException, SubmitException, ParserConfigurationException, InterruptedException {

		String arquivoRetornoRequest;
		String arquivoRetornoProtocolo;
		String nomeTeste = properties.getProperty("nomeTeste");
		String nomeIntegracao = properties.getProperty("nomeIntegracao");
		String enderecoWSDL = properties.getProperty("enderecoWSDL");
		String nomeOperation = properties.getProperty("nomeOperation");

		String enderecoWSDLResponse = properties.getProperty("enderecoWSDLResponse");
		String nomeOperationResponse = properties.getProperty("nomeOperationResponse");

		int falhasWS = 0;
		String password = properties.getProperty("password");
		String username = properties.getProperty("username");
		String wssPasswordType = properties.getProperty("wssPasswordType");
		String[] arquivosEnvio = properties.getProperty("arquivosEnvio").split(";");
		String[] retornoEsperado = properties.getProperty("retornoEsperado").split(";");

		VerificacoesDeWS testeWS = new VerificacoesDeWS();
		VerificacoesDeTela automacao = new VerificacoesDeTela();
		
		automacao.informaTeste(0, "", nomeTeste);
		automacao.informaTeste(9, "", nomeTeste);
		

		for (int i = 0; i < arquivosEnvio.length; i++) {
			arquivoRetornoRequest = testeWS.enviaRequest(nomeIntegracao, arquivosEnvio[i], enderecoWSDL, nomeOperation, password, username, wssPasswordType, tentativas);
			arquivoRetornoProtocolo = testeWS.enviaRequestDoProtocolo(nomeIntegracao, arquivoRetornoRequest, enderecoWSDLResponse, nomeOperationResponse, password, username, wssPasswordType, tentativas);
			falhasWS = falhasWS + testeWS.comparaResponseObtidoComEsperado(retornoEsperado[i], arquivoRetornoProtocolo, nomeIntegracao, arquivosEnvio[i]);
		}
		testeWS.verificaErros(falhasWS);

	}
	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
