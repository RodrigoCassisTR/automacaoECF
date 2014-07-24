package ECF.automacaoECF.padrao;



import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;



public class FuncionalidadesUteis {
	org.apache.log4j.Logger logger = Logger.getLogger(FuncionalidadesUteis.class.getName());

	public String formataDuracao(long millis) {

		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);

		sb.append(hours);
		sb.append(" HORAS ");
		sb.append(minutes);
		sb.append(" MINUTOS ");
		sb.append(seconds);
		sb.append(" SEGUNDOS ");

		return (sb.toString());

	}
	public void limpaPasta(String pasta){
		File pastaTXT = new File(pasta);
		if (pastaTXT.exists()) {
			logger.info("Limpando pasta: " + pasta);
			
			remover(new File(pasta));

		} else {
			logger.info("Não Existe pasta TXT a ser excluída!");
		}
	}
	public String formataDuracaoResumida(long duracaoParaFormatar) {
		String duracaoFormatada=String.format("%02d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(duracaoParaFormatar),
			    TimeUnit.MILLISECONDS.toMinutes(duracaoParaFormatar) - 
			    TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toHours(duracaoParaFormatar)),
			    TimeUnit.MILLISECONDS.toSeconds(duracaoParaFormatar) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duracaoParaFormatar)));   
		return duracaoFormatada;
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
}
