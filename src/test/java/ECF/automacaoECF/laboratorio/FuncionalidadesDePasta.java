package ECF.automacaoECF.laboratorio;

import java.io.File;

public class FuncionalidadesDePasta {

	public boolean verificaSeEstaVazia(String enderecoDaPasta) {

		File file = new File(enderecoDaPasta);

		if (file.isDirectory()) {

			if (file.list().length > 0) {

				System.out.println("Directory is not empty!");
				return true;
			} else {

				System.out.println("Directory is empty!");

			}

		} else {

			System.out.println("This is not a directory");
			return false;

		}
		return false;

	}

}
