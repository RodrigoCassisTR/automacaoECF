package ECF.automacaoECF.padrao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ManipuladorDeArquivos {

	public boolean verificaSeEstaVazia(String enderecoDaPasta) {

		File file = new File(enderecoDaPasta);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void remover(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; ++i) {
				remover(files[i]);
			}

		}
		f.delete();
	}

	public void copiaArquivos(String origem, String destino) throws IOException {
		File inputFile = new File(origem);
		File outputFile = new File(destino);

		FileReader in = new FileReader(inputFile);
		FileWriter out = new FileWriter(outputFile);
		int c;

		while ((c = in.read()) != -1)
			out.write(c);

		in.close();
		out.close();

	}
	
	public void limpaPastas(String diretorio) throws IOException {
		File diretorioParaApagar = new File(diretorio);
		FileUtils.cleanDirectory(diretorioParaApagar);

	}
	
	public String retornaNomeEmData(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		String nomeData = dateFormat.format(date);
		return nomeData;
		
	}

	public void gravaArquivoDeUmaString(String arquivoRetornado, String string) throws IOException {
		File file = new File(arquivoRetornado);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(string);
		bw.close();
		
	}
}
