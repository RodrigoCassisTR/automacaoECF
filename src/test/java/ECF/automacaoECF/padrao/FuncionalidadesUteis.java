package ECF.automacaoECF.padrao;



import java.util.concurrent.TimeUnit;

public class FuncionalidadesUteis {

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

	public String formataDuracaoResumida(long duracaoParaFormatar) {
		String duracaoFormatada=String.format("%02d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(duracaoParaFormatar),
			    TimeUnit.MILLISECONDS.toMinutes(duracaoParaFormatar) - 
			    TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toHours(duracaoParaFormatar)),
			    TimeUnit.MILLISECONDS.toSeconds(duracaoParaFormatar) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duracaoParaFormatar)));   
		return duracaoFormatada;
	}
}
