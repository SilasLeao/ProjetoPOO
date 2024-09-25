package modelo;
import java.util.ArrayList;
public class Correntista {
	protected String cpf;
	protected String nome;
	protected String senha;
	protected boolean titular = false;
	protected ArrayList<Conta> contas = new ArrayList<>();
	
	public Correntista(String cpf, String nome, String senha) throws Exception {
	    // Regex para garantir que a senha tenha 4 dígitos numéricos
	    if (senha == null || !senha.matches("\\d{4}")) {
	        throw new Exception("A senha deve ser composta por 4 dígitos numéricos.");
	    }
	    
	    this.cpf = cpf;
	    this.nome = nome;
	    this.senha = senha;
	}
	
	public double getSaldoTotal() {
		double saldoTotal = 0;
		for(Conta c : contas) {
			saldoTotal += c.getSaldo();
		}
		return saldoTotal;
	};
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ArrayList<Conta> getContas() {
		return contas;
	}

	public void addConta(Conta contaNova) {
		this.contas.add(contaNova);
	}
	
	public void removeConta(Conta conta) {
	    this.contas.remove(conta);
	}
	
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	

	public boolean isTitular() {
		return this.titular;
	}
	
	public void setTitular(boolean alteracaoTitular) {
		this.titular = alteracaoTitular;
	}
	
	@Override
	public String toString() {
		return "Correntista [cpf=" + cpf + ", nome=" + nome + ", senha=" + senha + ", titular=" + titular + "]";
	}
}