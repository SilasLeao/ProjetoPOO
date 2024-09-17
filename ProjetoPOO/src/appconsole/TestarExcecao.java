package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 *
 */

import regras_negocio.Fachada;

public class TestarExcecao {

	public static void main (String[] args) {

		System.out.println("\n-------TESTE DE EXCEÇÕES LANÇADAS PELOS METODOS DA FACHADA--------");
		try {
			Fachada.criarParticipante("p1@gmail.com","p1", 10);
			Fachada.criarParticipante("p1@gmail.com","p1", 10);
			System.out.println("*************1--->Nao lançou exceção para: criar participante existente "); 
		}catch (Exception e) {System.out.println("1ok--->"+e.getMessage());}

		try {
			Fachada.criarConvidado("c1@gmail.com","c1", 30, "empresa");
			Fachada.criarConvidado("c1@gmail.com","c1", 30, "empresa");
			System.out.println("*************2--->Nao lançou exceção para: criar convidado existente "); 
		}catch (Exception e) {System.out.println("2ok--->"+e.getMessage());}

		try {
			Fachada.criarEvento("01/01/2030","e1", 100);
			Fachada.criarEvento("01/01/2030","e1", 100);
			System.out.println("*************3--->Nao lançou exceção para: criar evento existente "); 
		}catch (Exception e) {System.out.println("3ok--->"+e.getMessage());}

		try {
			Fachada.criarEvento("02/01/2030","e2", -10.0);
			System.out.println("*************4--->Nao lançou exceção para: criar evento preco negativo "); 
		}catch (Exception e) {System.out.println("4ok--->"+e.getMessage());}

		try {
			Fachada.criarEvento("03/01/2030","e3", 0.0);
			Fachada.adiarEvento("03/01/2030","01/01/2030");
			System.out.println("*************5--->Nao lançou exceção para: adiar evento data existente"); 
		}catch (Exception e) {System.out.println("5ok--->"+e.getMessage());}

		try 
		{
			Fachada.adicionarParticipanteEvento("p1", 1);	
			Fachada.adicionarParticipanteEvento("p1", 1);	
			System.out.println("*************6--->Nao lançou exceção: adicionar participante evento que participa"); 
		}catch (Exception e) {System.out.println("6ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerParticipanteEvento("p1", 1);	
			Fachada.removerParticipanteEvento("p1", 1);	
			System.out.println("*************7--->Nao lançou exceção: remover participante evento que nao participa"); 
		}catch (Exception e) {System.out.println("7ok--->"+e.getMessage());}

		try 
		{
			Fachada.adicionarParticipanteEvento("p2", 1);	
			System.out.println("*************8--->Nao lançou exceção: adicionar participante inexistente"); 
		}catch (Exception e) {System.out.println("8ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerParticipanteEvento("p2", 1);	
			System.out.println("*************9--->Nao lançou exceção: remover participante inexistente "); 
		}catch (Exception e) {System.out.println("9ok--->"+e.getMessage());}

		try 
		{
			Fachada.apagarEvento("03/01/2030");	
			Fachada.apagarEvento("03/01/2030");	
			System.out.println("*************10--->Nao lançou exceção: apagar evento inexistente"); 
		}catch (Exception e) {System.out.println("10ok--->"+e.getMessage());}

		try 
		{
			Fachada.apagarParticipante("p2");	
			System.out.println("*************11--->Nao lançou exceção: apagar participante inexistente"); 
		}catch (Exception e) {System.out.println("11ok--->"+e.getMessage());}

		//apagar dados usados no teste
		try {Fachada.apagarEvento("01/01/2030");}	catch (Exception e){}		
		try {Fachada.apagarEvento("02/01/2030");}	catch (Exception e){}		
		try {Fachada.apagarEvento("03/01/2030");}	catch (Exception e) {}		
		try {Fachada.apagarParticipante("p1");} catch (Exception e){}
		try {Fachada.apagarParticipante("p2");} catch (Exception e){}
		try {Fachada.apagarParticipante("c1");} catch (Exception e){}
	}
}


