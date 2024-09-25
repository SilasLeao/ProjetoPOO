package modelo;

import java.io.IOException;

public class ContaEspecial extends Conta {
    protected double limite;

    // Construtor sem parâmetros de Conta
    public ContaEspecial(double limite) throws IOException {
        super(); 
        this.limite = limite;
    }

    // Construtor com parâmetros de Conta para leitura
    public ContaEspecial(int id, String data, double saldo, double limite) {
        super(id, data, saldo);
        this.limite = limite;
    }


    public void debitar(double valor) throws Exception {
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

    @Override
    public String toString() {
        return "Conta Especial [limite =" + this.limite + ", id=" + super.id + ", data=" + super.data + ", saldo=" + super.saldo + "]";
    }

    
}
