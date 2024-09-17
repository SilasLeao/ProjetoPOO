package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Conta {
	private static int contadorId = 0;
	protected int id;
	protected String data;
	protected double saldo;
	protected ArrayList<Correntista> correntistas = new ArrayList<>();
	
	public Conta() {
		this.id = contadorId++;
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    this.data = LocalDate.now().format(formato);
		this.saldo = 0;
	}
	
	public void creditar(double valor) {
		this.saldo += valor;
	}
	
	public void debitar(double valor) throws Exception{
		if (valor > this.saldo) {
			throw new Exception("Saldo insuficiente");
		}
		this.saldo -= valor;
	}
	
	public void transferir(double valor, Conta destino) throws Exception{
		this.debitar(valor);
		destino.creditar(valor);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getSaldo() {
		return this.saldo;
	}
	
	public void setSaldo(double valor) {
		this.saldo = valor;
	}
	
	public ArrayList<Correntista> getCorrentistas() {
		return this.correntistas;
	}
	
	public void addCorrentista(Correntista correntista) {
		this.correntistas.add(correntista);
	}
	
	public void removeCorrentista(Correntista correntista) {
	    this.correntistas.remove(correntista);
	}
	
	public boolean isTitular(String cpf) {
		if(correntistas.get(0).getCpf().equals(cpf)) {
			return true;
		}
		return false;
	}
};