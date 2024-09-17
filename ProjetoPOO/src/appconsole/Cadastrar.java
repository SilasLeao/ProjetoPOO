package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		try {
			Fachada.criarEvento("24/10/2024","festa de sao joao",100.0);
			Fachada.criarEvento("01/11/2024","show da pisadinha",200.0);

			Fachada.criarParticipante("joao@gmail.com", "joao",  150);
			Fachada.criarParticipante("maria@gmail.com", "maria",  30);

			Fachada.criarConvidado("jose@gmail.com", "jose",  70, "governo");
			Fachada.criarConvidado("ana@gmail.com", "ana",  50, "prefeitura");
			
			System.out.println("Cadastrou");
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}		
	}

	public static void main (String[] args) 
	{
		new Cadastrar();
	}
}


