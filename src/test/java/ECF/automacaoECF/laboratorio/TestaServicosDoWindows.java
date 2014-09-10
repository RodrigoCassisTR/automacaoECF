package ECF.automacaoECF.laboratorio;

public class TestaServicosDoWindows {

	public static void main(String[] args) throws Exception {
		NTSvc ntSvc = new NTSvc();
		String serviceName = "TAXBR-INTEGRATOR-ECF"; // 'remote access to registry' service.

		int count=0;
		while(count<100){
			
		
		System.out.println("State of service " + serviceName + " = " + ntSvc.stateService(serviceName));
		Thread.sleep(1000);
		count++;
		}

//		System.out.println("Starting service " + serviceName + ", state = " + ntSvc.startService(serviceName));
//
//		Thread.sleep(5000);
//		System.out.println("State of service " + serviceName + " = " + ntSvc.stateService(serviceName));
//
//		System.out.println("Stopping service " + serviceName + ", state = " + ntSvc.stopService(serviceName));
	}

}
