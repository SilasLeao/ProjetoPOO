package regras_negocio;

import java.util.ArrayList;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;
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
	
	public static void criarCorrentista(String cpf, String nome, String senha) throws Exception{
		// Verificação de CPF ja existente
	    for (Correntista correntista : repositorio.getCorrentistas()) {
	        if (correntista.getCpf().equals(cpf)) {
	            throw new Exception("Correntista com CPF " + cpf + " já existe.");
	        }
	    }
	    Correntista c = new Correntista(cpf, nome, senha);
		repositorio.addCorrentista(c);
		repositorio.salvarObjetos();
	
	}
	
	public static void criarConta(String cpf) throws Exception{   
		boolean correntistaEncontrado = false;
		for (Correntista correntista : repositorio.getCorrentistas()) {
			// Verifica se o correntista existe
		    if (correntista.getCpf().equals(cpf)) {
		    	// Verificar se o correntista ja é titular de alguma conta
		    	if(correntista.isTitular()) {
		    		throw new Exception("Esse correntista já é titular de uma conta.");
		    	}
		    	correntistaEncontrado = true;
		    	Conta c = new Conta();
		    	c.addCorrentista(correntista);
				repositorio.addConta(c);
				correntista.addConta(c);
				// Muda o status de titularidade do correntista
				correntista.setTitular(true);
				repositorio.salvarObjetos();
		        break;
		    }
		}
		if (!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");

		}
		
		
	}
	
	public static void criarContaEspecial(String cpf, double limite)  throws Exception{
		if(limite < 50) {
			throw new Exception("Limite tem que ser no mínimo 50.");
		}
		boolean correntistaEncontrado = false;
		// Verifica se o correntista existe
		for (Correntista correntista : repositorio.getCorrentistas()) {
			if (correntista.getCpf().equals(cpf)) {
				// Verificar se o correntista ja é titular de alguma conta
		    	if(correntista.isTitular()) {
		    		throw new Exception("Esse correntista já é titular de uma conta.");
		    	}
		    	correntistaEncontrado = true;
		    	ContaEspecial c = new ContaEspecial(limite);
		    	c.addCorrentista(correntista);
				repositorio.addConta(c);
				correntista.addConta(c);
				// Muda o status de titularidade do correntista
				correntista.setTitular(true);
				repositorio.salvarObjetos();
		        break;
		    }
		}
		if (!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado.");

		}
	}
	
	public static void inserirCorrentistaConta(int id, String cpf) throws Exception{
		boolean contaEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			// Verifica se a conta existe
			if(conta.getId() == id) {
				contaEncontrada = true;
				// Verifica se o correntista existe
				for(Correntista correntista : repositorio.getCorrentistas()) {
					if(correntista.getCpf().equals(cpf)) {
						// Verifica se o correntista ja faz parte da conta
						for(Correntista correntistaDaConta : conta.getCorrentistas()) {
							if(correntistaDaConta.getCpf().equals(cpf)) {
								throw new Exception("O correntista de CPF " + cpf + " já faz parte dessa conta.");
							}
						}
						correntistaEncontrado = true;
						conta.addCorrentista(correntista);
						correntista.addConta(conta);
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
			// Verifica se a conta existe
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					// Verifica se o correntista faz parte da conta
					if(correntista.getCpf().equals(cpf)) {
						// Verifica se o correntista é o titular
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
			// Verifica se a conta existe
			if(conta.getId() == id) {
				contaEncontrada = true;
				// Verifica se a conta está zerada
				if(conta.getSaldo() == 0) {
					for(Correntista correntista : conta.getCorrentistas()) {
						// Muda o status de titularidade do correntista titular
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
			// Verifica se a conta existe
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					// Verifica se o correntista existe
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						// Verifica se a senha está correta
						if(correntista.getSenha().equals(senha)) {
							conta.creditar(valor);
							repositorio.salvarObjetos();
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
			// Verifica se a conta existe
			if(conta.getId() == id) {
				contaEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					// Verifica se o correntista existe
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						// Verifica se a senha está correta
						if(correntista.getSenha().equals(senha)) {
							conta.debitar(valor);
							repositorio.salvarObjetos();
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
			throw new Exception("Correntista com CPF " + cpf + " não encontrado na conta " + id + ".");
		}
	}
	
	public static void transferirValor(int id1, String cpf, String senha, double valor, int id2) throws Exception{
		boolean contaOrigemEncontrada = false;
		boolean contaDestinoEncontrada = false;
		boolean correntistaEncontrado = false;
		for(Conta conta : repositorio.getContas()) {
			// Verifica se a conta origem existe
			if(conta.getId() == id1) {
				contaOrigemEncontrada = true;
				for(Correntista correntista : conta.getCorrentistas()) {
					// Verifica se o correntista existe
					if(correntista.getCpf().equals(cpf)) {
						correntistaEncontrado = true;
						// Verifica se a senha está correta
						if(correntista.getSenha().equals(senha)) {
							for(Conta conta2 : repositorio.getContas()) {
								// Verifica se a conta destino existe
								if(conta2.getId() == id2) {
									contaDestinoEncontrada = true;
									conta.debitar(valor);
									conta2.creditar(valor);
									repositorio.salvarObjetos();
									break;
								}
							}
						} else {
							throw new Exception("Senha incorreta");										
						}				
					}
				}
			}
		}
		if(!contaOrigemEncontrada) {
			throw new Exception("Conta com id " + id1 + " não encontrada.");
		}
		if(!correntistaEncontrado) {
			throw new Exception("Correntista com CPF " + cpf + " não encontrado na conta " + id1 + ".");
		}
		if(!contaDestinoEncontrada) {
			throw new Exception("Conta com id " + id2 + " não encontrada.");
		}
		
	}
}
	
	
	
	