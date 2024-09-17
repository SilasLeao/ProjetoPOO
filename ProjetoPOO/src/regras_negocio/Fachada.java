package regras_negocio;
/**********************************
 * POO - Fausto Ayres
 **********************************/

import java.util.ArrayList;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Convidado;
import modelo.Correntista;
import modelo.Evento;
import modelo.Participante;
import repositorio.Repositorio;
import java.util.Collections;
import java.util.Comparator;

public class Fachada {
	private Fachada() {}		
	private static Repositorio repositorio = new Repositorio();	
	
	public static ArrayList<Correntista> listarCorrentistas() {
		// Criar uma cópia da lista para não modificar a original
		ArrayList<Correntista> listaOrdenada = new ArrayList<>(repositorio.getCorrentistas());

        // Ordenar a lista pelo CPF usando Comparator
        Collections.sort(listaOrdenada, new Comparator<Correntista>() {
            @Override
            public int compare(Correntista c1, Correntista c2) {
                return c1.getCpf().compareTo(c2.getCpf());
            }
        });

        return listaOrdenada;
	}
	
	public static ArrayList<Conta> listarContas() {
		return repositorio.getContas();
	}
	
	public static void criarCorrentista(String cpf, String nome, String senha) throws Exception {
		Correntista c = new Correntista(cpf, nome, senha);
		repositorio.addCorrentista(c);
		repositorio.salvarObjetos();
	}
	
	public static void criarConta(String cpf) throws Exception{   
		boolean correntistaEncontrado = false;
		// Percorrer a lista de correntistas para verificar se existe algum com o CPF fornecido
		for (Correntista correntista : repositorio.getCorrentistas()) {
		    if (correntista.getCpf().equals(cpf)) {
		    	if(correntista.isTitular()) {
		    		throw new Exception("Esse correntista já é titular de uma conta.");
		    	}
		    	correntistaEncontrado = true;
		    	Conta c = new Conta();
		    	c.addCorrentista(correntista);
				repositorio.addConta(c);
				correntista.addConta(c);
				correntista.setTitular(true);
				repositorio.salvarObjetos();
		        break;  // Se encontrar o correntista, não é necessário continuar verificando
		    }
		}
		if (!correntistaEncontrado) {
			// Se o correntista não existe, lançar uma exceção
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");

		}
		
		
	}
	
	public static void criarContaEspecial(String cpf, double limite)  throws Exception{
		if(limite < 50) {
			throw new Exception("Limite tem que ser no mínimo 50.");
		}
		boolean correntistaEncontrado = false;
		// Percorrer a lista de correntistas para verificar se existe algum com o CPF fornecido
		for (Correntista correntista : repositorio.getCorrentistas()) {
			if (correntista.getCpf().equals(cpf)) {
		    	if(correntista.isTitular()) {
		    		throw new Exception("Esse correntista já é titular de uma conta.");
		    	}
		    	correntistaEncontrado = true;
		    	ContaEspecial c = new ContaEspecial(limite);
		    	c.addCorrentista(correntista);
				repositorio.addConta(c);
				correntista.addConta(c);
				correntista.setTitular(true);
				repositorio.salvarObjetos();
		        break;  // Se encontrar o correntista, não é necessário continuar verificando
		    }
		}
		if (!correntistaEncontrado) {
			// Se o correntista não existe, lançar uma exceção
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");

		}
	}
	
	public static void inserirCorrentistaConta(int id, String cpf) throws Exception{
		boolean contaEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : repositorio.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						conta.addCorrentista(correntista);
						correntista.addConta(conta);
						repositorio.addCorrentista(correntista);
						repositorio.addConta(conta);
						repositorio.salvarObjetos();
						break;
					}
				}
			}
		}
		if(!contaEncontrada) {
			throw new Exception("Conta com id " + id + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");
		}
	}
	
	public static void removerCorrentistaConta(int id, String cpf) throws Exception{
		boolean contaEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						if(conta.isTitular(cpf)) {
							throw new Exception("Um correntista titular não pode ser removido da conta");
						}
						correntistaEncontrado = true;
						conta.removeCorrentista(correntista);
						correntista.removeConta(conta);
						repositorio.salvarObjetos();
						break;
					}
				}
			}
		}
		if(!contaEncontrada) {
			throw new Exception("Conta com id " + id + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");
		}
	}
	
	public static void apagarConta(int id) throws Exception{
		boolean contaEncontrada = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id) {
				contaEncontrada = true;
				if(conta.getSaldo() == 0) {
					for(Correntista correntista : conta.getCorrentistas()) {
						if(correntista.isTitular()) {
							correntista.setTitular(false);
						}
						correntista.removeConta(conta);
						
					}
					repositorio.removeConta(conta);
					repositorio.salvarObjetos();
				} else {
					throw new Exception("A conta não pode ser apagada a menos que esteja com seu saldo zerado.");
				}
				
			}
		}
		
		if(!contaEncontrada) {
			throw new Exception("Conta com id " + id + " não encontrada.");
		}
	}
	
	public static void creditarValor(int id, String cpf, String senha, double valor) throws Exception{
		boolean contaEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						if(correntista.getSenha().equals(senha)) {
							conta.creditar(valor);
							break;
						}						
						throw new Exception("Senha incorreta");			
					}
				}
			}
		}
		if(!contaEncontrada) {
			throw new Exception("Conta com id " + id + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");
		}
	}
	
	public static void debitarValor(int id, String cpf, String senha, double valor) throws Exception{
		boolean contaEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						if(correntista.getSenha().equals(senha)) {
							conta.debitar(valor);
							break;
						}						
						throw new Exception("Senha incorreta");			
					}
				}
			}
		}
		if(!contaEncontrada) {
			throw new Exception("Conta com id " + id + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");
		}
	}
	
	public static void transferirValor(int id1, String cpf, String senha, double valor, int id2) throws Exception{
		boolean contaOrigemEncontrada = false;
		boolean contaDestinoEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			if(conta.getId() == id1) {
				contaOrigemEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						if(correntista.getSenha().equals(senha)) {
							for(Conta conta2 : repositorio.getContas()) {
								if(conta2.getId() == id2) {
									contaDestinoEncontrada = true;
									conta.debitar(valor);
									conta2.creditar(valor);
									break;
								}
							}
						}						
						throw new Exception("Senha incorreta");			
					}
				}
			}
		}
		if(!contaOrigemEncontrada) {
			throw new Exception("Conta com id " + id1 + " não encontrada.");
		}
		if(!contaDestinoEncontrada) {
			throw new Exception("Conta com id " + id2 + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");
		}
	}
	
	
	
	
	
	// a partir daqui é do projeto antigo
	
	
	
	
	
	
	
	public static ArrayList<Evento> listarEventos() 	{
		return repositorio.getEventos();
	}
	public static ArrayList<Participante> listarParticipantes(String nome) {
		//lista os participantes que contem o nome ou
		//lista todos participantes, caso nome for vazio, 
		ArrayList<Participante> lista = new ArrayList<>();
		for(Participante p : repositorio.getParticipantes())
			if(p.getNome().contains(nome))
				lista.add(p);
		return lista;
	}
	public static Participante localizarParticipante(String nome) {
		return repositorio.localizarParticipante(nome);
	}
	public static Evento localizarEvento(int id) 	{
		return repositorio.localizarEvento(id);
	}
	public static Evento localizarEvento(String data) 	{
		return repositorio.localizarEvento(data);
	}
	
	public static void criarParticipante(String email, String nome, int idade) throws Exception {
		email = email.trim();
		nome = nome.trim();

		//localizar participante no repositorio, usando o nome 
		Participante p = repositorio.localizarParticipante(nome);
		if (p!=null)
			throw new Exception("N�o criou participante: " + nome + " ja cadastrado(a)");

		//criar objeto Participante 
		p = new Participante (email, nome, idade);

		//adicionar participante no reposit�rio
		repositorio.adicionar(p);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}	

	public static void criarConvidado(String email,String nome, int idade, String empresa) throws Exception {
		email = email.trim();
		nome = nome.trim();
		empresa = empresa.trim();

		//localizar participante no repositorio, usando o nome 
		Participante p = repositorio.localizarParticipante(nome);
		if (p!=null)
			throw new Exception("criar convidado: " + nome + " ja cadastrado(a)");

		//a empresa � obrigatoria 
		if (empresa.isEmpty())
			throw new Exception("criar convidado: " + nome + " empresa � obrigatoria");

		//criar objeto Convidado 
		Convidado c = new Convidado (email, nome, idade, empresa);

		//adicionar convidado no reposit�rio
		repositorio.adicionar(c);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}
	
	public static void criarEvento (String data, String descricao, double preco) throws Exception {
		data = data.trim();
		descricao = descricao.trim();

		//localizar Evento no repositorio, usando a data 
		Evento ev = repositorio.localizarEvento(data);
		if (ev!=null)
			throw new Exception("criar evento: " + descricao + " ja existe nesta data "+data);
		
		if (preco <0)
			throw new Exception("criar evento: " + descricao + " preco nao pode ser negativo " + preco);

		//gerar id no repositorio
		int id = repositorio.gerarIdEvento();
		ev = new Evento(id, descricao, data, preco);	
		
		//adicionar evento no reposit�rio
		repositorio.adicionar(ev);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void 	adicionarParticipanteEvento(String nome, int id) throws Exception {
		nome = nome.trim();

		//localizar participante no repositorio, usando o nome 
		Participante p = repositorio.localizarParticipante(nome);
		if(p == null) 
			throw new Exception("adicionar participante:  " + nome + " inexistente");

		//localizar evento no repositorio, usando id 
		Evento ev = repositorio.localizarEvento(id);
		if(ev == null) 
			throw new Exception("adicionar participante: evento " + id + " inexistente");

		//localizar o participante no evento, usando o nome
		Participante paux = ev.localizar(nome);
		if(paux != null) 
			throw new Exception("N�o adicionou participante: " + nome + " j� participa do evento " + id);

		//adicionar o participante ao evento
		ev.adicionar(p);
		//adicionar o evento ao participante
		p.adicionar(ev);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void 	removerParticipanteEvento(String nome, int id) throws Exception {
		nome = nome.trim();

		//localizar participante no repositorio, usando o nome 
		Participante p = repositorio.localizarParticipante(nome);
		if(p == null) 
			throw new Exception("remover participante: participante " + nome + " inexistente");


		//localizar evento no repositorio, usando id 
		Evento ev = repositorio.localizarEvento(id);
		if(ev == null) 
			throw new Exception("remover participante: evento " + id + " inexistente");


		//localizar o participante no evento, usando o nome
		Participante paux = ev.localizar(nome);
		if(paux == null) 
			throw new Exception("remover participante: " + nome + " nao participa do evento " + id);

		//remover o participante do evento
		ev.remover(p);
		//remover o evento do participante
		p.remover(ev);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void apagarEvento(String data) throws Exception	{
		//localizar evento no repositorio, usando id 
		Evento ev = repositorio.localizarEvento(data);
		if (ev == null)
			throw new Exception("apagar evento: data " + data + " inexistente");

		//Remover todos os participantes deste evento
		for(Participante p : ev.getParticipantes()) {
			p.remover(ev);
		}
		ev.getParticipantes().clear();
		
		//remover evento do reposit�rio
		repositorio.remover(ev);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void adiarEvento(String data, String novadata) throws Exception	{
		//localizar evento no repositorio, usando data 
		Evento ev = repositorio.localizarEvento(data);
		if (ev == null)
			throw new Exception("adiar evento: data inexistente " + data);

		//localizar evento no repositorio, usando novadata
		Evento ev2 = repositorio.localizarEvento(novadata);
		if (ev2 != null)
			throw new Exception("adiar evento: ja tem evento na nova data " + novadata);

		//alterar a data do evento
		ev.setData(novadata);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}
	
	public static void 	apagarParticipante(String nome) throws Exception {
		nome = nome.trim();

		//localizar participante no repositorio, usando o nome 
		Participante p = repositorio.localizarParticipante(nome);
		if(p == null) 
			throw new Exception("apagar participante: participante " + nome + " inexistente");

		//participante nao pode ser deletado caso participe de algum evento
		if(!p.getEventos().isEmpty()) 
			throw new Exception("apagar participante: participante " + nome + " ainda tem evento");

		//remover o participante do repositorio
		repositorio.remover(p);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

}
