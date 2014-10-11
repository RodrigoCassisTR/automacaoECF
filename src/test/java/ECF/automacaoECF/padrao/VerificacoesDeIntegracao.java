package ECF.automacaoECF.padrao;

import static org.junit.Assert.fail;
import static java.nio.file.StandardCopyOption.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class VerificacoesDeIntegracao {
	org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeIntegracao.class.getName());
	public String url = new RecebeParametros().url;
	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;
	public String navegador = new RecebeParametros().navegador;
	public String numerotentativas = new RecebeParametros().numerotentativas;
	int tentativas = Integer.parseInt(numerotentativas);
	public String tempoMedio = new RecebeParametros().tempoMedio;
	int tempoMedioAceitavel = Integer.parseInt(tempoMedio);

	public String caminhoIntegrador = new RecebeParametros().caminhoIntegrador;
	public String pastasIntegracao = new RecebeParametros().pastasIntegracao;

	VerificacoesDeTela selenium = new VerificacoesDeTela();

	public long geraNovoNumeroDeNota(WebDriver driver, String empresaEmissao, String serie, String nomeTeste, String linkTextModulo, String linkTextListagem, String xpathFiltroEspecificoModulo, String idCampoSerie, String idBotaoPesquisaAvancada, String idBotaoAvancada) throws InterruptedException, IOException {
		return tempoMedioAceitavel;

	}

	public void falhaIntegracao(String mensagem) {
		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");
		logger.info("FALHA: " + mensagem);
		logger.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§");
		fail(mensagem);

	}

	public void limpaPastasIntegracao() {

		File pastaTXT = new File(pastasIntegracao);
		if (pastaTXT.exists()) {
			logger.info("Limpando as pastas de integração no caminho: " + pastasIntegracao);
			VerificacoesDeIntegracao ld = new VerificacoesDeIntegracao();
			ld.remover(new File(pastasIntegracao));

		} else {
			logger.info("Não Existe pasta TXT a ser excluída!");
		}

	}

	public void limpaPasta(String enderecoPasta) {

		File pastaTXT = new File(enderecoPasta);
		if (pastaTXT.exists()) {
			logger.info("Limpando pasta: " + enderecoPasta);
			VerificacoesDeIntegracao ld = new VerificacoesDeIntegracao();
			ld.remover(new File(enderecoPasta));

		} else {
			logger.info("Não existe pasta!");
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

	public void encerraIntegrador(String processo) {
		logger.info("Encerrando Integrador...");
		try {
			String line;
			Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo)) {
						Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));

					}
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

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

	public void verificaRetornoIntegrador(String[] informacoesDocumento, String[] pastasIntegracao) {

		String caminhoDaPastaDeRetorno = pastasIntegracao[2];
		String nomeDoArquivoEsperado = informacoesDocumento[1] + ".txt";

		logger.info("Iniciando processo de verificação de retorno do Integrador...");
		logger.info("------------------------------------");
		logger.info("INFORMACOES: ");
		logger.info("PASTA DE RETORNO: " + caminhoDaPastaDeRetorno);
		logger.info("ARQUIVO RETORNO: " + nomeDoArquivoEsperado);
		logger.info("------------------------------------");

	}

	private void limpaPastas(String diretorio) throws IOException {
		File diretorioParaApagar = new File(diretorio);

		FileUtils.cleanDirectory(diretorioParaApagar);

	}

	@SuppressWarnings("unused")
	private String verificaSaidaDoDiretorio(String pasta, String arquivoAlterado) {

		File f = new File(pasta + arquivoAlterado);
		if (f.exists() && !f.isDirectory()) {
			return ("Cheio");
		} else {
			return ("Vazio");
		}

	}

	public boolean verficaServicoIntegracao(String serviceName) {
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set sh = CreateObject(\"Shell.Application\") \n" + "If sh.IsServiceRunning(\"" + serviceName + "\") Then \n" + "   wscript.Quit(1) \n" + "End If \n" + "wscript.Quit(0) \n";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("wscript " + file.getPath());
			p.waitFor();
			return (p.exitValue() == 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verificaPastaErro(String pastaErro, int tentativas) {
		String caminhoPastaErro = pastaErro;
		logger.info("Verificando se o arquivo foi movido para a pasta erro: " + caminhoPastaErro);
		boolean vazia = verificaSeDiretorioEstaVazio(caminhoPastaErro);
		return vazia;

	}

	private boolean verificaSeDiretorioEstaVazio(String caminhoPastaErro) {

		File file = new File(caminhoPastaErro);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			falhaIntegracao("O diretorio " + caminhoPastaErro + " não existe!");
		}
		return false;
	}

	public void verficaServicoIntegracaoIniciado(String nomeDoServicoIntegrador) throws Exception {

		logger.info("Verificando se o serviço do Integrador iniciado...");
		boolean servicoIniciado = verficaServicoIntegracao(nomeDoServicoIntegrador);
		if (servicoIniciado == false) {
			logger.info("O servico do integrador " + nomeDoServicoIntegrador + " não está iniciado!");
			logger.info("iniciando servico " + nomeDoServicoIntegrador + "...");
			IniciaServicoDoWindows(nomeDoServicoIntegrador);

		} else {
			logger.info("O servico do integrador esta iniciado!");

		}

	}

	private boolean verificaSeFoiParaAPastaErro(String pasta_erro, String nomeArquivoIntegracao, String arquivoEnvio, String nomeTeste) throws IOException {
		File file = new File(pasta_erro);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				logger.info("O arquivo  foi movido para a pasta 'erro'!");
				copiaArquivos(pasta_erro + nomeArquivoIntegracao, "./evidencias/integracao/" + nomeTeste.toLowerCase().trim() + "_retorno.xml");
				copiaArquivos(arquivoEnvio, "./evidencias/integracao/" + nomeTeste.toLowerCase().trim() + "_envio.xml");
				falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
				return true;
			} else {
				logger.info("O arquivo não foi movido para a pasta 'erro'!");
				return false;
			}
		} else {
			falhaIntegracao("O diretorio " + pasta_erro + " não existe!");
		}
		return true;
	}

	@SuppressWarnings("unused")
	private String verificaSaidaDoDiretorioEnviado(String pasta_enviado) {
		/*
		 * Retornos: 0 - Cheio 1 - Vazio 2 - Erro
		 */
		File file = new File(pasta_enviado);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				return ("Cheio");
			} else {
				return ("Vazio");
			}
		} else {
			return ("Erro");
		}
	}

	public void integraRegistro(String arquivoIntegracaoExclui, String[] pastasIntegracao, String[] camposRegistro, String[] informacoesRegistro, String nomeTeste, String formatoIntegracao) throws IOException, InterruptedException {

		if (formatoIntegracao.contentEquals("XML")) {

			// COPIA O ARQUIVO MODELO PARA UMA PASTA TEMPORARIA...
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
			Date date = new Date();
			logger.info("Copiando arquivo modelo '" + arquivoIntegracaoExclui + "'...");
			String diretorioTemporario = "./files/temp/xmlUp";
			String prefixoNome = "/arquivo_integracao_";
			String nomeArquivo = dateFormat.format(date) + ".xml";
			String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
			copiaArquivos(arquivoIntegracaoExclui, arquivoTemporario);
			logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

			String nomeArquivoIntegracao = prefixoNome + nomeArquivo;

			logger.info("APAGAR ESSA PARADA DEPOSI..." + arquivoTemporario);

			// EDITA O ARQUIVO...
			substituiInformacoesNoArquivo(arquivoTemporario, camposRegistro, informacoesRegistro);

			// LIMPA PASTAS DE INTEGRACAO...
			logger.info("Limpando as pastas de integração...");
			limpaArrayDePastas(pastasIntegracao);

			// INFORMA PASTAS DE INTEGRAÇÃO...
			String pasta_entrada = pastasIntegracao[0] + "/";
			String pasta_enviado = pastasIntegracao[1] + "/";
			String pasta_erro = pastasIntegracao[2] + "/";
			@SuppressWarnings("unused")
			String pasta_recebido = pastasIntegracao[3] + "/";
			String pasta_backup = pasta_entrada + "/.ecf-backup";

			// MOVENDO PARA A PASTA ENTRADA DA INTEGRACAO...
			logger.info("Movendo o arquivo alterado para a pasta de entrada do integrador...");
			copiaArquivos(arquivoTemporario, pasta_entrada + prefixoNome + nomeArquivo);

			// AGUARDA SAIR DA PASTA DE ENTRADA...
			aguardaSairDaPastaEntrada(prefixoNome, nomeArquivo, pasta_entrada, pasta_backup);

			// AGUARDA SAIR DA PASTA DE ENVIADO...
			aguardaSairDaPastaEnviado(prefixoNome, nomeArquivo, pasta_enviado, pasta_backup);

			// VERIFICA SE O ARQUIVO FOI MOVIDO PARA A PASTA ERRO
			logger.info("Verificando se o arquivo foi movido para a pasta 'erro'");
			verificaSeFoiParaAPastaErro(pasta_erro, nomeArquivoIntegracao, arquivoTemporario, nomeTeste);

			if (verificaPastaErro(pasta_erro, tentativas) == true) {
				logger.info("O arquivo  foi movido para a pasta 'erro'!");
				copiaArquivos(pasta_erro + "/*.*", "./evidencias/integracao/" + nomeArquivo);
				falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
			}

			logger.info("O arquivo não foi movido para a pasta 'erro'!");

			logger.info("Limpando a pasta temporaria " + diretorioTemporario + "...");
			limpaPastas(diretorioTemporario);
			logger.info("Pastas temporaria " + diretorioTemporario + " limpa com sucesso!");
		} else if (formatoIntegracao.contentEquals("TXT")) {

			// COPIA O ARQUIVO MODELO PARA UMA PASTA TEMPORARIA...
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
			Date date = new Date();
			logger.info("Copiando arquivo modelo '" + arquivoIntegracaoExclui + "'...");
			String diretorioTemporario = "./files/temp/xmlUp";
			String prefixoNome = "/arquivo_integracao_";
			String nomeArquivo = dateFormat.format(date) + ".txt";
			String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
			copiaArquivos(arquivoIntegracaoExclui, arquivoTemporario);
			logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

			String nomeArquivoIntegracao = prefixoNome + nomeArquivo;

			logger.info("APAGAR ESSA PARADA DEPOSI..." + arquivoTemporario);

			// EDITA O ARQUIVO...
			substituiInformacoesNoArquivo(arquivoTemporario, camposRegistro, informacoesRegistro);

			// LIMPA PASTAS DE INTEGRACAO...
			logger.info("Limpando as pastas de integração...");
			limpaArrayDePastas(pastasIntegracao);

			// INFORMA PASTAS DE INTEGRAÇÃO...
			String pasta_entrada = pastasIntegracao[0] + "/";
			String pasta_enviado = pastasIntegracao[1] + "/";
			String pasta_erro = pastasIntegracao[2] + "/";
			@SuppressWarnings("unused")
			String pasta_recebido = pastasIntegracao[3] + "/";
			String pasta_backup = pasta_entrada + "/.ecf-backup";

			// MOVENDO PARA A PASTA ENTRADA DA INTEGRACAO...
			logger.info("Movendo o arquivo alterado para a pasta de entrada do integrador...");
			copiaArquivos(arquivoTemporario, pasta_entrada + prefixoNome + nomeArquivo);

			// AGUARDA SAIR DA PASTA DE ENTRADA...
			aguardaSairDaPastaEntrada(prefixoNome, nomeArquivo, pasta_entrada, pasta_backup);

			// AGUARDA SAIR DA PASTA DE ENVIADO...
			aguardaSairDaPastaEnviado(prefixoNome, nomeArquivo, pasta_enviado, pasta_backup);

			// VERIFICA SE O ARQUIVO FOI MOVIDO PARA A PASTA ERRO
			logger.info("Verificando se o arquivo foi movido para a pasta 'erro'");
			verificaSeFoiParaAPastaErro(pasta_erro, nomeArquivoIntegracao, arquivoTemporario, nomeTeste);

			if (verificaPastaErro(pasta_erro, tentativas) == true) {
				logger.info("O arquivo  foi movido para a pasta 'erro'!");
				copiaArquivos(pasta_erro + "/*.*", "./evidencias/integracao/" + nomeArquivo);
				falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
			}

			logger.info("O arquivo não foi movido para a pasta 'erro'!");

			logger.info("Limpando a pasta temporaria " + diretorioTemporario + "...");
			limpaPastas(diretorioTemporario);
			logger.info("Pastas temporaria " + diretorioTemporario + " limpa com sucesso!");

		}

	}

	private void aguardaSairDaPastaEnviado(String prefixoNome, String nomeArquivo, String pasta_enviado, String pasta_backup) throws IOException, InterruptedException {
		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'enviado': " + pasta_enviado + "...");
		String arquivoAlterado = prefixoNome + nomeArquivo;
		File backup = new File(pasta_backup);

		boolean vazia = verificaSeEstaVazia(pasta_enviado);
		int timer = 0;
		while (vazia == true) {
			vazia = verificaSeEstaVazia(pasta_enviado);
			Thread.sleep(1000);
			timer++;

			if (backup.isDirectory() == true) {
				limpaPastas(pasta_backup);
			}

			if (timer > tentativas) {
				logger.info("Arquivo " + arquivoAlterado + " não saiu da pasta de enviado: " + pasta_enviado);
				falhaIntegracao("Arquivo " + arquivoAlterado + " não saiu da pasta de enviado: " + pasta_enviado);
			}
		}
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de enviado: " + pasta_enviado + " com sucesso!");
	}

	private void aguardaSairDaPastaEntrada(String prefixoNome, String nomeArquivo, String pasta_entrada, String pasta_backup) throws InterruptedException, IOException {
		String arquivoAlterado = prefixoNome + nomeArquivo;

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'entrada': " + pasta_entrada + "...");
		File backup = new File(pasta_backup);
		boolean vazia = verificaSaidaDoDiretorioDeIntegracao(pasta_entrada, arquivoAlterado);
		int timer = 0;
		while (vazia == true) {
			vazia = verificaSaidaDoDiretorioDeIntegracao(pasta_entrada, arquivoAlterado);
			Thread.sleep(1000);
			timer++;

			if (backup.isDirectory() == true)
				limpaPastas(pasta_backup);

			if (timer > tentativas)
				falhaIntegracao("O Arquivo não foi movido pelo integrador!");
		}

		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

	}

	private void substituiInformacoesNoArquivo(String arquivoTemporario, String[] camposRegistro, String[] informacoesRegistro) throws IOException {
		logger.info("Editando o arquivo com as informações de envio...");
		Path path = Paths.get(arquivoTemporario);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);

		logger.info("Preenchendo o arquivo com as informações corretas...");
		for (int count = 0; count < camposRegistro.length; count++) {
			content = content.replaceAll(camposRegistro[count], informacoesRegistro[count]);
			Files.write(path, content.getBytes(charset));
		}

	}

	private void limpaArrayDePastas(String[] pastas) throws IOException {
		for (int count = 0; count < pastas.length; count++) {
			logger.info("Limpando pasta " + pastas[count]);
			limpaPastas(pastas[count]);
		}
		logger.info("Pastas limpas com sucesso!");

	}

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

	private boolean verificaSaidaDoDiretorioDeIntegracao(String pasta, String arquivo) {

		File f = new File(pasta + arquivo);
		if (f.exists() && !f.isDirectory()) {
			return true;
		} else {
			return false;
		}

	}

	public void verificaUrlWS(String wsclientHost, String wsclientReturnHost, String urlIntegracao, String diretorioPadraoIntegracao, String arquivoCfg, String nomeDoServicoIntegrador, String routeImportFilePayloadType, String formatoIntegracao) throws IOException {
		logger.info("Verificando as WS de config do integrador...");

		if (wsclientHost.contentEquals(urlIntegracao) || wsclientReturnHost.contentEquals(urlIntegracao) || routeImportFilePayloadType.contentEquals(formatoIntegracao)) {
			logger.info("Integrador configurado com o endereço de WS correto...");
			logger.info("wsclient.host= " + wsclientHost);
			logger.info("wsclient.return.host= " + wsclientReturnHost);
			logger.info("route.import.file.payload.type=" + routeImportFilePayloadType);

		} else {
			logger.info("O endereço WS não está configurado corretamente, alterando para o endereço WS correto...");

			logger.info("wsclient.host= " + wsclientHost + " esperado: " + urlIntegracao);
			logger.info("wsclient.return.host= " + wsclientReturnHost + " esperado: " + urlIntegracao);
			logger.info("route.import.file.payload.type=" + routeImportFilePayloadType + " esperado: " + formatoIntegracao);

			logger.info("Editando o arquivo cfg " + arquivoCfg + " ...");
			Path path = Paths.get(arquivoCfg);
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);

			String[] urlWsParaAlterar = {wsclientHost, wsclientReturnHost, routeImportFilePayloadType};
			String[] urlWsEsperado = {urlIntegracao, urlIntegracao, formatoIntegracao};

			for (int count = 0; count < urlWsParaAlterar.length; count++) {
				content = content.replaceAll(urlWsParaAlterar[count], urlWsEsperado[count]);
				Files.write(path, content.getBytes(charset));
			}

			logger.info("URL do WS alterado com sucesso no arquivo .cfg!");
			logger.info("Reiniciando o serviço do integrador...");

			reiniciaServicoWindows(nomeDoServicoIntegrador);
		}
	}

	private void reiniciaServicoWindows(String nomeDoServicoIntegrador) {

		try {

			ParaServicoDoWindows(nomeDoServicoIntegrador);
			IniciaServicoDoWindows(nomeDoServicoIntegrador);
			verificaSeOServicoEstaIniciado(nomeDoServicoIntegrador);

		} catch (Exception e) {
			logger.info("Erro de IO:" + e.getLocalizedMessage());
		}

	}

	private void verificaSeOServicoEstaIniciado(String nomeDoServicoIntegrador) {
		logger.info("verificando se o servico está iniciado " + nomeDoServicoIntegrador);
		String[] statusService = {"cmd.exe", "/c", "sc \\remoteserver", "query", nomeDoServicoIntegrador, "|", "find", "/C", "\"RUNNING\""};

	}

	private void IniciaServicoDoWindows(String nomeDoServicoIntegrador) throws Exception {
		String HOST = "\\\\127.0.0.1";
		logger.info("Iniciando o serviço " + nomeDoServicoIntegrador);
		String[] startService = {"cmd.exe", "/c", "sc " + HOST, "start", nomeDoServicoIntegrador};
		Runtime.getRuntime().exec(startService);
		int statusServico = stateService(nomeDoServicoIntegrador);

		int count = 0;
		logger.info("Aguardando o servico " + nomeDoServicoIntegrador + " iniciar...");
		while (statusServico != 4) {
			statusServico = stateService(nomeDoServicoIntegrador);
			count++;
			Thread.sleep(1000);
			if (count >= tentativas) {
				falhaIntegracao("Não foi possível iniciar o serviço do integrador " + nomeDoServicoIntegrador);
			}

		}
		logger.info("Serviço " + nomeDoServicoIntegrador + " iniciado com sucesso!");

	}

	private void ParaServicoDoWindows(String nomeDoServicoIntegrador) throws Exception {
		String HOST = "\\\\127.0.0.1";
		logger.info("Parando o serviço " + nomeDoServicoIntegrador);
		String[] stopService = {"cmd.exe", "/c", "sc " + HOST, "stop", nomeDoServicoIntegrador};
		Runtime.getRuntime().exec(stopService);

		int statusServico = stateService(nomeDoServicoIntegrador);

		int count = 0;
		logger.info("Aguardando o servico " + nomeDoServicoIntegrador + " parar...");
		while (statusServico != 1) {
			statusServico = stateService(nomeDoServicoIntegrador);
			count++;
			Thread.sleep(1000);
			if (count >= tentativas) {
				falhaIntegracao("Não foi possível parar o serviço do integrador " + nomeDoServicoIntegrador);
			}

		}
		logger.info("Serviço " + nomeDoServicoIntegrador + " parado com sucesso!");

	}

	public int stateService(String serviceName) throws Exception {
		return controleDeServicosDoWindows("query", serviceName);
	}

	private int controleDeServicosDoWindows(String cmd, String serviceName) throws Exception {
		final int STATE_UNKNOWN = -1;
		final int STATE_STOPPED = 1;
		final int STATE_START_PENDING = 2;
		final int STATE_STOP_PENDING = 3;
		final int STATE_RUNNING = 4;

		final Process proc = Runtime.getRuntime().exec("sc " + cmd + " " + serviceName);
		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		int state = STATE_UNKNOWN;

		while ((line = br.readLine()) != null) // searches for state in the
												// child process output
		{
			int p;

			if ((p = line.indexOf(" STATE ")) != -1) {
				if ((p = line.indexOf(" : ", p)) != -1)
					state = Integer.parseInt(line.substring(p + 3, p + 4));
			}
		}

		int retCode = proc.waitFor();

		if (retCode != 0)
			throw new Exception("Error code of 'sc' is : " + retCode);

		return state;
	}

	public void copiaModeloCfg(String diretorioIntegrador, String nomeIntegracao) throws IOException {

		ManipuladorDeArquivos manipuladorDeArquivos = new ManipuladorDeArquivos();
		manipuladorDeArquivos.copiaArquivos("./files/modeloCfg/taxbr.ecf.integrator." + nomeIntegracao + ".cfg", diretorioIntegrador + "/etc/taxbr.ecf.integrator." + nomeIntegracao + ".cfg");

	}

}
