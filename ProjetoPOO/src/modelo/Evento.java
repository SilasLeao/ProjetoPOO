/**
 * SI - POO - Prof. Fausto Ayres
 *
 */
package modelo;

import java.util.ArrayList;

public class Evento {
	private int id;
	private String descricao;
	private String data;
	private double preco;
	private ArrayList<Participante> participantes = new ArrayList<>();

	public Evento(int id, String descricao, String data, double preco) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.data = data;
		this.preco = preco;
	}
	public void adicionar(Participante p){
		participantes.add(p);
	}
	public void remover(Participante p){
		participantes.remove(p);
	}
	public Participante localizar(String nome){
		for(Participante p : participantes){
			if(p.getNome().equals(nome))
				return p;
		}
		return null;
	}
	@Override
	public String toString() {
		String texto= "Evento [id=" + id + ", data=" + data + ", descricao=" + descricao +", preco=" + preco + "]";
		
		texto += ", participantes:";
		for(Participante p : participantes)
			texto += p.getNome() + ",";
		return texto;
	}

	public double getTotalPago() {
		double total = 0;
		for(Participante p : participantes) {
			total += p.getPago(this.preco);
		}
		return total;
	}

	public int getIdadeMedia() {
		if (participantes.size()==0)
			return 0;

		double soma=0;
		for(Participante p : participantes) {
			soma += p.getIdade();
		}
		return (int) Math.round(soma/participantes.size());
	}

	public ArrayList<Participante> getParticipantesPorIdade(int idade) {
		ArrayList<Participante> lista = new ArrayList<>();

		for(Participante p : participantes) {
			if (p.getIdade()==idade)
				lista.add(p);
		}
		return lista;
	}
	public int contarConvidados() {
		int cont=0;
		for(Participante p : participantes) {
			if (p instanceof Convidado)
				cont++;
		}
		return cont;
	}

	public ArrayList<Convidado> getConvidados() {
		ArrayList<Convidado> convidados = new ArrayList<>();
		for(Participante p : participantes) {
			if (p instanceof Convidado c)
				convidados.add(c);
		}
		return convidados;
	}

	public ArrayList<Convidado> getConvidados(String empresa) {
		ArrayList<Convidado> convidados = new ArrayList<>();

		for(Participante p : participantes) {
			if (p instanceof Convidado c && c.getEmpresa().equals(empresa))
				convidados.add(c);
		}
		return convidados;
	}

	public int contarGratuidades() {
		int cont=0;
		for(Participante p : participantes) {
			if (p.getPago(this.preco) == 0)
				cont++;
		}
		return cont;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public ArrayList<Participante> getParticipantes() {
		return participantes;
	}



}
