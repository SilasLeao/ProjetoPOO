package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import modelo.Evento;
import modelo.Participante;
import regras_negocio.Fachada;

public class Listar {

	public Listar() {
		try {
			System.out.println("\n---------listagem de participantes (arquivo)-----");
			for(Participante p : Fachada.listarParticipantes("")) 
				System.out.println(p);

			System.out.println("\n---------listagem de eventos (arquivo) ----");
			for(Evento e : Fachada.listarEventos()) 
				System.out.println(e);
			
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}	
	}

	public static void main (String[] args) 
	{
		new Listar();
	}
}


