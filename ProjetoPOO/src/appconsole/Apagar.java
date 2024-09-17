package appconsole;

/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Apagar {

	public Apagar() {
		try {
			Fachada.apagarParticipante("maria");
			System.out.println("apagou maria");
			
			Fachada.apagarEvento("30/10/2024");
			System.out.println("apagou evento na data 30/10/2024");

		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Apagar();
	}
}
