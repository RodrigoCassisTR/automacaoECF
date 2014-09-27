package ECF.automacaoECF.consistencias;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.model.iface.Response;

public class WsdlAnalyzer {

	public static void main(String[] args) throws Exception {
		WsdlProject project = new WsdlProject();
		WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, "http://166.78.0.215:8092/taxbr-solution-webservices/services/CostCenterWebServiceImplPort?wsdl");
		WsdlInterface wsdl = wsdls[0];
		for (Operation operation : wsdl.getOperationList()) {
			WsdlOperation op = (WsdlOperation) operation;
			System.out.println("OP:" + op.getName());
			System.out.println(op.createRequest(true));
			System.out.println("Response:");
			System.out.println(op.createResponse(true));
		}

		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, "http://166.78.0.215:8092/taxbr-solution-webservices/services/CostCenterWebServiceImplPort?wsdl", true)[0];
		WsdlOperation operation = (WsdlOperation) iface.getOperationByName("");
		WsdlRequest request = operation.addNewRequest("My request");
		request.setRequestContent(operation.createRequest(true));
		WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);
		Response response = submit.getResponse();
		
		System.out.println(response);
	}
}