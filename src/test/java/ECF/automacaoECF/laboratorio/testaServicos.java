package ECF.automacaoECF.laboratorio;

import java.io.IOException;

public class testaServicos {

	public static void main(String[] args) throws Exception {
		Servicos serv=new Servicos();
		MsWinSvc serv2=new MsWinSvc();
		
		String nomeDoServico="TAXBR-INTEGRATOR-ECF";
		
		//serv.iniciaServico(nomeDoServico);
		serv.paraServico(nomeDoServico);
	
		
		

	}

}
