package ECF.automacaoECF.padrao;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RecebeParametros {

	public String url;
	public String usuario;
	public String senha;
	public String navegador;
	public String numerotentativas;
	public String tempoMedio;
	public String moduloEcf;
	public String testaTela;
	public String testaCRUD;
	public String testaIntegracao;
	public String enviaEmail;
	public String destinatariosSuite;
	public String xpathModulo;

	public RecebeParametros() {

		try {
			Properties properties = new Properties();
			FileInputStream file = new FileInputStream("./src/test/resources/automation.properties");
			properties.load(file);

			url = properties.getProperty("url");
			usuario = properties.getProperty("usuario");
			senha = properties.getProperty("senha");
			navegador = properties.getProperty("navegador");
			numerotentativas = properties.getProperty("numerotentativas");
			tempoMedio = properties.getProperty("tempoMedio");
			moduloEcf = properties.getProperty("moduloEcf");
			
			testaTela = properties.getProperty("testaTela");
			testaCRUD = properties.getProperty("testaCRUD");
			testaIntegracao = properties.getProperty("testaIntegracao");
			enviaEmail = properties.getProperty("enviaEmail");
			destinatariosSuite = properties.getProperty("destinatariosSuite");
			
			xpathModulo = properties.getProperty("xpathModulo");
			


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
