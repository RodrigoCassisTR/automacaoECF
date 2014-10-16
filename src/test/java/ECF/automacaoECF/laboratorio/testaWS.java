package ECF.automacaoECF.laboratorio;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

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

public class testaWS {

	public static void main(String[] args) throws SubmitException, SoapUIException, XmlException, IOException {

		WsdlProject project = new WsdlProject();
		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, "http://166.78.0.215:8092/taxbr-solution-webservices/services/TrTaxBrMessageResponseImplPort", true)[0];
		WsdlOperation operation = (WsdlOperation) iface.getOperationByName("getResponseMessage");
		WsdlRequest request = operation.addNewRequest("My Request");
		request.setPassword("ADM");
		request.setUsername("ADM");
		request.setWssPasswordType("PasswordDigest");
		request.setRequestContent("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:res=\"http://response.webservices.taxbr.ta.thomsonreuters.com/\">" +
				"   <soap:Header/>" +
				"   <soap:Body>" +
				"<res:getResponseMessage>" +
				"<PROTOCOL_NUMBER>e761999f-dd6a-4c96-a4d7-adc42d2afb91</PROTOCOL_NUMBER>" +
				"</res:getResponseMessage>" +
				"</soap:Body>" +
				"</soap:Envelope>");
		@SuppressWarnings("rawtypes")
		WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
		Response response = submit.getResponse();
		String resposta = response.getContentAsString();

		System.out.println("AQUI ESTA A RESPOSTA: \n" + resposta);

		

	}

}
