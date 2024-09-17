/**
 * SI - POO - Prof. Fausto Ayres
 *
 */

package modelo;

import java.util.ArrayList;

public class Participante { 
	protected String email;
	protected String nome;
	protected int idade;
	private ArrayList<Evento> eventos = new ArrayList<>();
	
	public Participante(String email, String nome, int idade) {
		super();
		this.email = email;
		this.nome = nome;
		this.idade = idade;
	}

	public void adicionar(Evento e){
		eventos.add(e);
	}
	public void remover(Evento e){
		eventos.remove(e);
	}
	public Evento localizar(int id){
		for(Evento e : eventos){
			if(e.getId() == id)
				return e;
		}
		return null;
	}
	
	public int getPercentual() {
		//percentual de desconto de acordo com a idade
		if(idade<18)			//criança
			return 50;			//50% de desconto
		else
			if(idade>=60)		//idoso
				return 20;		//20% de desconto
			else
				return 0;		//adulto nao tem desconto
	}

	public double getPago(double preco) {
		//preco do evento - desconto calculado
		return preco - preco*getPercentual()/100;
	}

	@Override
	public String toString() {
		String texto =  "email=" + email + ", nome=" + nome + ", idade=" + idade + ", percentual="+getPercentual() ;
		
		texto += ", eventos:";
		for(Evento e : eventos)
			texto += e.getId() + ",";
		return texto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public ArrayList<Evento> getEventos() {
		return eventos;
	}

}
