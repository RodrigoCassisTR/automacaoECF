package ECF.automacao.suite;

import ECF.automacaoECF.padrao.ZipUtils;

public class TestaZip {

	public static void main(String[] args) {
					
		String[] u = null;
		ZipUtils.main(u);
		String evidenciasZip = "./files/temp/ZipToSend/evidencias.zip";
		
		System.out.println(evidenciasZip);
		
	}

}
