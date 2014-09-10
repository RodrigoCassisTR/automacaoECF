package ECF.automacaoECF.laboratorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Servicos {

	public void iniciaServico(String nomeDoServico) {
		// TODO Auto-generated method stub

	}

	public void paraServico(String nomeDoServico) throws IOException, InterruptedException {
		String[] stop = {"cmd.exe", "/c", "sc", "stop", nomeDoServico};
		Process p = Runtime.getRuntime().exec(stop);
		
//		Runtime.getRuntime().exec("runas /profile /user:Administrator \"cmd.exe /c Powrprof.dll,SetSuspendState\"");
//		String[] stop = {"cmd.exe", "/c", "sc", "stop", nomeDoServico};
		
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = reader.readLine();
		while (line != null) {
			System.out.println(line);
			line = reader.readLine();
		}

	}

}
