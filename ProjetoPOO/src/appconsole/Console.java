package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Console {

	public Console() {
		try {
			Fachada.criarCorrentista("987654321", "Thayna", "1234");
			Fachada.criarConta("987654321");
			Fachada.criarCorrentista("123456789", "Silas", "1234");
			Fachada.criarConta("123456789");	
			Fachada.criarCorrentista("123456788", "Lucas", "1234");
//			Fachada.criarContaEspecial("123456788", 100);	
//			Fachada.debitarValor(0, "987654321", "1234", 50);
//			Fachada.creditarValor(0, "987654321", "1234", 500);
//			Fachada.inserirCorrentistaConta(0, "123456789");
//			Fachada.removerCorrentistaConta(0, "987654321");
//			Fachada.transferirValor(0, "123456788", "1234", 100, 2);
//			Fachada.debitarValor(0, "987654321", "1234", 300);
//			Fachada.apagarConta(0);
			System.out.println(Fachada.listarCorrentistas());
			System.out.println(Fachada.listarContas());
			


		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}		
	}

	public static void main (String[] args) 
	{
		new Console();
	}
}


