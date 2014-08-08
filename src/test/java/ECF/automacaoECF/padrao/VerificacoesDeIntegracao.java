package ECF.automacaoECF.padrao;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

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

	/*
	 * @rodrigocassis Método que cria o txt com os dados informados e move para
	 * a pasta de integração
	 */
	public void integraNotaTxt(String[] camposNfe, String[] dadosEmissao, long numeroNota, String arquivoNfeModelo, int resultadoEsperado) throws IOException {

		/*
		 * resultadoEsperado = 1 - Autorizada resultadoEsperado = 2 - Rejeitada
		 * resultadoEsperado = 3 - Erro de XML
		 */

		// C:/Users/u0155902/Downloads/testJavaTXT/entrada/copia.txt
		// TODO criar rotina de montar as notas com as variaveis

		Calendar c = Calendar.getInstance();
		String nomeDoArquivo = "C:/Users/u0155902/Downloads/testJavaTXT/entrada/arquivo_integracao_" + c.getTimeInMillis() + ".txt";

		try {

			BufferedReader streamIn = new BufferedReader(new FileReader(arquivoNfeModelo));
			BufferedWriter streamOut = new BufferedWriter(new FileWriter(nomeDoArquivo));

			String line = streamIn.readLine();
			while (line != null) {
				if (line.contentEquals("2.00")) {
					streamOut.write("!!!!!!!TROQUEI AQUI!!!!");
				} else {
					streamOut.write(line);
					line = streamIn.readLine();
				}

			}
			logger.info("Gravado arquivo " + nomeDoArquivo);

			// while (streamIn.ready()) {
			// streamOut.write(streamIn.readLine());
			// }

			streamIn.close();
			streamOut.close();

		} catch (FileNotFoundException e) {
			System.err.println("FileStreamsTest: " + e);
		} catch (IOException e) {
			System.err.println("FileStreamsTest: " + e);
		}

	}

	private void copiaArquivos(String origem, String destino) throws IOException {
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

	public void integraNotaTxtTeste(String[] pastasIntegracao, String[] camposNota, String[] dadosEmissao, long numeroNota, String arquivoModelo, int n, String pasta_entrada) throws IOException {

		Calendar c = Calendar.getInstance();

		logger.info("Copiando arquivo modelo '" + arquivoModelo + "'...");
		String diretorioTemporario = "./files/temp/txtUp";
		String prefixoNome = "/arquivo_integracao_";
		String nomeArquivo = c.getTimeInMillis() + ".txt";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
		copiaArquivos(arquivoModelo, arquivoTemporario);
		logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

		logger.info("Editando o arquivo com as informações de envio...");
		Path path = Paths.get(arquivoTemporario);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);

		logger.info("Limpando as pastas de integração...");
		for (int count = 0; count < pastasIntegracao.length; count++) {
			logger.info("Limpando pasta " + pastasIntegracao[count]);
			limpaPastas(pastasIntegracao[count]);
		}
		logger.info("Pastas limpas com sucesso!");

		for (int count = 0; count < camposNota.length; count++) {
			content = content.replaceAll(camposNota[count], dadosEmissao[count]);
			Files.write(path, content.getBytes(charset));
		}

		logger.info("Arquivo editado com sucesso! Alterado os seguintes campos:");
		for (int count = 0; count < camposNota.length; count++) {
			logger.info("#### " + camposNota[count] + ": " + dadosEmissao[count]);
		}

		pasta_entrada = pastasIntegracao[0] + "/";

		logger.info("Movendo o arquivo alterado para a pasta de entrada do integrador...");
		copiaArquivos(arquivoTemporario, pasta_entrada + prefixoNome + nomeArquivo);

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta de entrada: " + pasta_entrada + "...");
		while (verificaSaidaDoDiretorioEntrada(pasta_entrada, content).contentEquals("Cheio")) {
		}
		logger.info("Arquivo " + prefixoNome + nomeArquivo + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

		logger.info("Limpando a pasta temporaria " + diretorioTemporario + "...");
		limpaPastas(diretorioTemporario);
		logger.info("Pastas temporaria " + diretorioTemporario + " limpa com sucesso!");

	}

	private void limpaPastas(String diretorio) throws IOException {
		File diretorioParaApagar = new File(diretorio);

		FileUtils.cleanDirectory(diretorioParaApagar);

	}

	private String verificaSaidaDoDiretorioEntrada(String pasta_entrada, String arquivoAlterado) {

		File f = new File(pasta_entrada + arquivoAlterado);
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

	public void verficaServicoIntegracaoIniciado(String nomeDoServicoIntegrador) {

		logger.info("Verificando se o serviço do Integrador iniciado...");
		boolean servicoIniciado = verficaServicoIntegracao(nomeDoServicoIntegrador);
		if (servicoIniciado == false) {
			logger.info("O servico do integrador " + nomeDoServicoIntegrador + " não está iniciado, encerrando o teste!");
			falhaIntegracao("O servico do integrador nao esta iniciado, encerando teste com falha!");
		} else {
			logger.info("O servico do integrador esta iniciado!");

		}

	}

	public void integraLedgerAccount(String[] campos, String[] informacoesRegistro, String[] pastasIntegracao, String arquivoModelo) throws IOException {
		Calendar c = Calendar.getInstance();

		logger.info("Copiando arquivo modelo '" + arquivoModelo + "'...");
		String diretorioTemporario = "./files/temp/xmlUp";
		String prefixoNome = "/arquivo_ledgeraccount_";
		String nomeArquivo = c.getTimeInMillis() + ".xml";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
		copiaArquivos(arquivoModelo, arquivoTemporario);
		logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

		logger.info("Editando o arquivo com as informações de envio...");
		Path path = Paths.get(arquivoTemporario);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);

		logger.info("Limpando as pastas de integração...");
		for (int count = 0; count < pastasIntegracao.length; count++) {
			logger.info("Limpando pasta " + pastasIntegracao[count]);
			limpaPastas(pastasIntegracao[count]);
		}
		logger.info("Pastas limpas com sucesso!");

		for (int count = 0; count < campos.length; count++) {
			content = content.replaceAll(campos[count], informacoesRegistro[count]);
			Files.write(path, content.getBytes(charset));
		}

		String pasta_entrada = pastasIntegracao[0] + "/";
		String pasta_enviado = pastasIntegracao[1] + "/";
		String pasta_erro = pastasIntegracao[2] + "/";
		String pasta_recebido = pastasIntegracao[3] + "/";

		logger.info("Movendo o arquivo alterado para a pasta de entrada do integrador...");
		copiaArquivos(arquivoTemporario, pasta_entrada + prefixoNome + nomeArquivo);

		String arquivoAlterado = prefixoNome + nomeArquivo;

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'entrada': " + pasta_entrada + "...");
		while (verificaSaidaDoDiretorioEntrada(pasta_entrada, arquivoAlterado).contentEquals("Cheio")) {
		}
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'enviado': " + pasta_enviado + "...");
		while (verificaSaidaDoDiretorioEnviado(pasta_enviado).contentEquals("Cheio")) {
		}
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_enviado + " com sucesso!");

		logger.info("Verificando se o arquivo foi movido para a pasta 'erro'");

		verificaSeFoiParaAPastaErro(pasta_erro);

		if (verificaPastaErro(pasta_erro, tentativas) == true) {
			logger.info("O arquivo  foi movido para a pasta 'erro'!");
			falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
		}

		logger.info("O arquivo não foi movido para a pasta 'erro'!");

		logger.info("Limpando a pasta temporaria " + diretorioTemporario + "...");
		limpaPastas(diretorioTemporario);
		logger.info("Pastas temporaria " + diretorioTemporario + " limpa com sucesso!");

	}

	private void verificaSeFoiParaAPastaErro(String pasta_erro) {
		File file = new File(pasta_erro);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				logger.info("O arquivo  foi movido para a pasta 'erro'!");
				falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
			} else {
				logger.info("O arquivo não foi movido para a pasta 'erro'!");
			}
		} else {
			falhaIntegracao("O diretorio " + pasta_erro + " não existe!");
		}

	}

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

	public void integraRegistro(String arquivoIntegracao, String[] pastasIntegracao, String[] camposRegistro, String[] informacoesRegistro) throws IOException {
		Calendar c = Calendar.getInstance();

		logger.info("Copiando arquivo modelo '" + arquivoIntegracao + "'...");
		String diretorioTemporario = "./files/temp/xmlUp";
		String prefixoNome = "/arquivo_integracao_";
		String nomeArquivo = c.getTimeInMillis() + ".xml";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
		copiaArquivos(arquivoIntegracao, arquivoTemporario);
		logger.info("Copiando arquivo modelo, com nome " + arquivoTemporario + " com sucesso!");

		logger.info("Editando o arquivo com as informações de envio...");
		Path path = Paths.get(arquivoTemporario);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);

		logger.info("Limpando as pastas de integração...");
		for (int count = 0; count < pastasIntegracao.length; count++) {
			logger.info("Limpando pasta " + pastasIntegracao[count]);
			limpaPastas(pastasIntegracao[count]);
		}
		logger.info("Pastas limpas com sucesso!");

		logger.info("Preenchendo o arquivo com as informações corretas...");
		for (int count = 0; count < camposRegistro.length; count++) {
			content = content.replaceAll(camposRegistro[count], informacoesRegistro[count]);
			Files.write(path, content.getBytes(charset));
		}

		String pasta_entrada = pastasIntegracao[0] + "/";
		String pasta_enviado = pastasIntegracao[1] + "/";
		String pasta_erro = pastasIntegracao[2] + "/";
		String pasta_recebido = pastasIntegracao[3] + "/";

		logger.info("Movendo o arquivo alterado para a pasta de entrada do integrador...");
		copiaArquivos(arquivoTemporario, pasta_entrada + prefixoNome + nomeArquivo);

		String arquivoAlterado = prefixoNome + nomeArquivo;

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'entrada': " + pasta_entrada + "...");

		for (int countEntrada = 0; countEntrada <= tentativas; countEntrada++) {
			while (verificaSaidaDoDiretorioEntrada(pasta_entrada, arquivoAlterado).contentEquals("Cheio")) {

			}
		}

		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'enviado': " + pasta_enviado + "...");
		for (int countSaida = 0; countSaida <= tentativas; countSaida++) {
			while (verificaSaidaDoDiretorioEnviado(pasta_enviado).contentEquals("Cheio")) {
			}
		}
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_enviado + " com sucesso!");

		logger.info("Verificando se o arquivo foi movido para a pasta 'erro'");

		verificaSeFoiParaAPastaErro(pasta_erro);

		if (verificaPastaErro(pasta_erro, tentativas) == true) {
			logger.info("O arquivo  foi movido para a pasta 'erro'!");
			falhaIntegracao("O arquivo foi movido para a pasta 'erro'!");
		}

		logger.info("O arquivo não foi movido para a pasta 'erro'!");

		logger.info("Limpando a pasta temporaria " + diretorioTemporario + "...");
		limpaPastas(diretorioTemporario);
		logger.info("Pastas temporaria " + diretorioTemporario + " limpa com sucesso!");

	}

}
