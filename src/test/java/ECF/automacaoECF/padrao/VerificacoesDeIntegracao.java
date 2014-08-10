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
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	private void limpaPastas(String diretorio) throws IOException {
		File diretorioParaApagar = new File(diretorio);

		FileUtils.cleanDirectory(diretorioParaApagar);

	}

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

	public void integraRegistro(String arquivoIntegracao, String[] pastasIntegracao, String[] camposRegistro, String[] informacoesRegistro) throws IOException, InterruptedException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();

		logger.info("Copiando arquivo modelo '" + arquivoIntegracao + "'...");
		String diretorioTemporario = "./files/temp/xmlUp";
		String prefixoNome = "/arquivo_integracao_";
		String nomeArquivo = dateFormat.format(date) + ".xml";
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
		aguardaPastaFicarVazia(pasta_entrada, arquivoAlterado, tentativas);
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'enviado': " + pasta_enviado + "...");
		aguardaPastaFicarVazia(pasta_enviado, arquivoAlterado, tentativas);
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de enviado: " + pasta_enviado + " com sucesso!");

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
	public void aguardaPastaFicarVazia(String pasta, String arquivo, int tentativas) throws InterruptedException {
		int count = 0;
		while (verificaSaidaDoDiretorio(pasta, arquivo).contentEquals("Cheio")) {
			count++;
			if (count > 9000000) {
				logger.info("O arquivo não saiu da pasta ou demorou mais tempo do que o aceitavel");
				falhaIntegracao("O arquivo não saiu da pasta ou demorou mais tempo do que o aceitavel");
			}
		}
	}

	public void integraRegistroDeExclusao(String arquivoIntegracaoExclui, String[] pastasIntegracao, String[] camposRegistro, String[] informacoesRegistro) throws IOException, InterruptedException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();

		logger.info("Copiando arquivo modelo '" + arquivoIntegracaoExclui + "'...");
		String diretorioTemporario = "./files/temp/xmlUp";
		String prefixoNome = "/arquivo_integracao_";
		String nomeArquivo = dateFormat.format(date) + ".xml";
		String arquivoTemporario = diretorioTemporario + prefixoNome + nomeArquivo;
		copiaArquivos(arquivoIntegracaoExclui, arquivoTemporario);
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
		aguardaPastaFicarVazia(pasta_entrada, arquivoAlterado, tentativas);
		logger.info("Arquivo " + arquivoAlterado + " saiu da pasta de entrada: " + pasta_entrada + " com sucesso!");

		logger.info("Aguardando o arquivo " + prefixoNome + nomeArquivo + " sair da pasta 'enviado': " + pasta_enviado + "...");
		aguardaPastaFicarVazia(pasta_enviado, arquivoAlterado, tentativas);
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
