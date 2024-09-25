package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;

public class Repositorio {

	//Correntista e Conta
	private ArrayList<Correntista> correntistas = new ArrayList<>(); 
	private ArrayList<Conta> contas = new ArrayList<>();

	//Repositorio
	public Repositorio() {
		carregarObjetos();
		salvarObjetos();
	}
	
	//Getters e Setters
	public void addCorrentista(Correntista c) {
		correntistas.add(c);
	}
	
	public void addConta(Conta c) {
		contas.add(c);
	}
	
	public void removeConta(Conta c) {
		contas.remove(c);
	}
	
	public ArrayList<Correntista> getCorrentistas() {
		return this.correntistas;
	}
	
	public ArrayList<Conta> getContas() {
		return this.contas;
	}
	


	//SALVAR OBJETOS E CARREGAR OBJETOS do arquivo .csv
	public void carregarObjetos()  	{
		String linha;	
		String[] partes;	
		Conta c;
		Correntista ct;

		//Carregar contas
		try	{

			File f = new File( new File(".\\contas.csv").getCanonicalPath());
			if (!f.exists()) {
		        System.out.println("Arquivo 'contas.csv' não encontrado, criando um novo...");
		        f.createNewFile();  // Criar o arquivo se não existir
		    }
			//identificando se cada conta é normal ou especial e cria as instâncias das classes
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				if(partes[0].equals("Especial")) {
					c = new ContaEspecial(Integer.parseInt(partes[1]), partes[2], Double.parseDouble(partes[3]) , Double.parseDouble(partes[4]));
					this.addConta(c);
				} else {
					c = new Conta(Integer.parseInt(partes[1]), partes[2], Double.parseDouble(partes[3]));
					this.addConta(c);
				}
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de contas:"+ex.getMessage());
 		}
		
		//Carregar correntista
		try	{
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath() )  ;
			if (!f.exists()) {
		        System.out.println("Arquivo 'correntistas.csv' não encontrado, criando um novo...");
		        f.createNewFile();  // Criar o arquivo se não existir
		    }
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				boolean titular = partes[3].equals("Sim");
				//Sim -> é titular de uma conta | Nao -> não é titular de uma conta

				ct = new Correntista(partes[0], partes[1], partes[2]);
				ct.setTitular(titular);
				
				//se o correntista tiver conta associada a ele, a quinta coluna
				//é tratada como lista de IDs de contas, separando por vírgula
				if(partes.length > 4) {
					for (String idConta : partes[4].split(",")) {
						int id = Integer.parseInt(idConta);
						for(Conta conta : this.getContas()){
							if(id == conta.getId()) {
							ct.addConta(conta);
							conta.addCorrentista(ct);
							
							}
						}
					}
				} 
				//se corresponder com o id da conta, o correntista é associado
				this.addCorrentista(ct);
				
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
		}	
	}

	//--------------------------------------------------------------------
	public void salvarObjetos()  {
		
	    // Remover duplicatas nas contas
	    ArrayList<Conta> contasUnicas = new ArrayList<>();
	    //Percorre todas as contas
	    for (Conta c : contas) {
	        boolean duplicada = false;
	        //Verifica se o Id da conta é único
	        for (Conta contaUnica : contasUnicas) {
	            if (contaUnica.getId() == c.getId()) {
	                duplicada = true;
	                break;
	            }
	        }
	        if (!duplicada) {
	            contasUnicas.add(c);
	        }
	    }
	    
	    // Gravar contas no arquivo CSV, agora sem duplicatas
	    try {
	        File f = new File(new File(".\\contas.csv").getCanonicalPath());
	        FileWriter arquivo1 = new FileWriter(f);
	        for (Conta e : contasUnicas) {
	            if (e instanceof ContaEspecial) { //ver se é ContaEspecial
	                ContaEspecial contaEspecial = (ContaEspecial) e;  // Casting para ContaEspecial
	                arquivo1.write("Especial;" + contaEspecial.getId() + ";" + contaEspecial.getData() + ";" 
	                        + contaEspecial.getSaldo() + ";" + contaEspecial.getLimite() + "\n");
	            } else { //Se não é especial, é Normal
	                arquivo1.write("Normal;" + e.getId() + ";" + e.getData() + ";" + e.getSaldo() + "\n");
	            }
	        }
	        arquivo1.close();
	    } catch (Exception e) {
	        throw new RuntimeException("Problema na criação do arquivo de contas " + e.getMessage());
	    }

	    // Remover duplicatas nos correntistas
	    ArrayList<Correntista> correntistasUnicos = new ArrayList<>();
	    for (Correntista c : correntistas) {
	        boolean duplicado = false;
	        for (Correntista correntistaUnico : correntistasUnicos) {
	            if (correntistaUnico.getCpf().equals(c.getCpf())) {
	                duplicado = true;
	                break;
	            }
	        }
	        if (!duplicado) {
	            correntistasUnicos.add(c);
	        }
	    }

	    // Gravar os correntistas no arquivo CSV, agora sem duplicatas
	    try {
	        File f = new File(new File(".\\correntistas.csv").getCanonicalPath());
	        FileWriter arquivo2 = new FileWriter(f);
	        ArrayList<String> lista; //armazenar ids das contas associadas
	        String listaId; //armazenar ids das contas formatado pro .csv
	        for (Correntista ct : correntistasUnicos) {
	            // Montar uma lista com os IDs das contas do correntista
	            lista = new ArrayList<>();
	            for (Conta c : ct.getContas()) {
	                lista.add(c.getId() + "");
	            }
	            listaId = String.join(",", lista);
	            
	            arquivo2.write(ct.getCpf() + ";" + ct.getNome() + ";" + ct.getSenha() + ";" 
	                    + (ct.isTitular() ? "Sim" : "Nao") + ";" + listaId + "\n");
	        }
	        arquivo2.close();
	    } catch (Exception e) {
	        throw new RuntimeException("Problema na criação do arquivo de correntistas " + e.getMessage());
	    }
	}

}
