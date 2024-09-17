package appconsole;

/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Alterar {

	public Alterar() {
		try {
			Fachada.adicionarParticipanteEvento("joao", 1);
			Fachada.adicionarParticipanteEvento("maria", 1);
			Fachada.adicionarParticipanteEvento("jose", 1);
			Fachada.adicionarParticipanteEvento("ana", 1);
			Fachada.removerParticipanteEvento("ana", 1);
			
			Fachada.adicionarParticipanteEvento("joao", 2);
			Fachada.adicionarParticipanteEvento("maria", 2);
			System.out.println("adicionou participantes aos eventos");
		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}

		try {
			Fachada.adiarEvento("24/10/2024", "30/10/2024");
			System.out.println("adiou evento");

		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
