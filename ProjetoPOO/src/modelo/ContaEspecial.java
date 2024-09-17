package modelo;

public class ContaEspecial extends Conta {
	protected double limite;
	
	public ContaEspecial(double limite){
		super();
		this.limite = limite;
	}
	
	public void debitar(double valor) throws Exception{
		if (valor > this.saldo + this.limite) {
			throw new Exception("Saldo insuficiente");
		}
		this.saldo -= valor;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
}