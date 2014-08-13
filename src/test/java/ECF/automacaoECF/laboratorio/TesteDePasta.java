package ECF.automacaoECF.laboratorio;

public class TesteDePasta {

	public static void main(String[] args) throws InterruptedException {

		FuncionalidadesDePasta func = new FuncionalidadesDePasta();

		String pasta = "C:/temp/testes";
		boolean vazia = func.verificaSeEstaVazia(pasta);
		int timer = 0;
		while (vazia == true) {
			vazia = func.verificaSeEstaVazia(pasta);
			Thread.sleep(1000);
			timer++;

			if (timer > 20)
				break;
		}
	}

}
