/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Conta;
import modelo.Convidado;
import modelo.Correntista;
import modelo.Evento;
import modelo.Participante;

public class Repositorio {
	// esses dois arrays são do projeto antigo
	private ArrayList<Participante> participantes = new ArrayList<>();
	private ArrayList<Evento> eventos = new ArrayList<>(); 
	
	private ArrayList<Correntista> correntistas = new ArrayList<>(); 
	private ArrayList<Conta> contas = new ArrayList<>();

	public Repositorio() {
		carregarObjetos();
	}
	
	public void addCorrentista(Correntista c) {
		correntistas.add(c);
	}
	
	public void addConta(Conta c) {
		contas.add(c);
	}
	
	public void removeConta(Conta c) {
		contas.remove(c);
	}
	
	
	public void adicionar(Participante p)	{
		participantes.add(p);
	}

	public void remover(Participante p)	{
		participantes.remove(p);
	}
	
	
	public ArrayList<Correntista> getCorrentistas() {
		return this.correntistas;
	}
	
	public ArrayList<Conta> getContas() {
		return this.contas;
	}
	
	// parte de arquivo que tem q adaptar ao nosos projeto novo
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\eventos.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\participantes.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	
		Evento ev;
		Participante p;

		try	{
			String data, descricao, id, preco ;
			File f = new File( new File(".\\eventos.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				id = partes[0];
				data = partes[1];
				descricao = partes[2];
				preco = partes[3];
				ev = new Evento(Integer.parseInt(id), descricao, data, Double.parseDouble(preco));
				this.adicionar(ev);
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
		}

		try	{
			String tipo,nome, email, empresa, idade, ids;
			File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				tipo = partes[0];
				email = partes[1];
				nome = partes[2];
				idade = partes[3];
				ids="";
				if(tipo.equals("PARTICIPANTE")) {
					p = new Participante(email,nome,Integer.parseInt(idade));
					this.adicionar(p);
					if(partes.length>4)
						ids = partes[4];		//ids dos eventos separados por ","
				}
				else {
					empresa = partes[4];
					p = new Convidado(email,nome,Integer.parseInt(idade),empresa);
					this.adicionar(p);
					if(partes.length>5)
						ids = partes[5];		//ids dos eventos separados por ","
				}

				//relacionar participante com os seus eventos
				if(!ids.isEmpty()) {	
					for(String idevento : ids.split(",")){	//converter string em array
						ev = this.localizarEvento(Integer.parseInt(idevento));
						ev.adicionar(p);
						p.adicionar(ev);
					}
				}
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de participantes:"+ex.getMessage());
		}
	}

	//--------------------------------------------------------------------
	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que est�o no reposit�rio
		try	{
			File f = new File( new File(".\\eventos.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Evento e : eventos) 	{
				arquivo1.write(e.getId()+";"+e.getData()+";"+e.getDescricao()+";"+e.getPreco()+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na cria��o do arquivo  eventos "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			ArrayList<String> lista ;
			String listaId;
			for(Participante p : participantes) {
				//montar uma lista com os id dos eventos do participante
				lista = new ArrayList<>();
				for(Evento e : p.getEventos()) {
					lista.add(e.getId()+"");
				}
				listaId = String.join(",", lista);

				if(p instanceof Convidado c )
					arquivo2.write("CONVIDADO;" +p.getEmail() +";" + p.getNome() +";" 
							+ p.getIdade() +";"+ c.getEmpresa() +";"+ listaId +"\n");	
				else
					arquivo2.write("PARTICIPANTE;" +p.getEmail() +";" + p.getNome() +";" 
							+ p.getIdade() +";"+ listaId +"\n");	

			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo  participantes "+e.getMessage());
		}

	}
	
	
	// A partir daqui é do projeto antigo

	public Participante localizarParticipante(String nome)	{
		for(Participante p : participantes)
			if(p.getNome().equals(nome))
				return p;
		return null;
	}

	public void adicionar(Evento e)	{
		eventos.add(e);
	}
	public void remover(Evento e)	{
		eventos.remove(e);
	}

	public Evento localizarEvento(int id)	{
		for(Evento e : eventos)
			if(e.getId() == id)
				return e;
		return null;
	}
	public Evento localizarEvento(String data)	{
		for(Evento e : eventos)
			if(e.getData().equals(data))
				return e;
		return null;
	}
	
	


	public ArrayList<Participante> getParticipantes() 	{
		return participantes;
	}
	
	public ArrayList<Evento> getEventos() 	{
		return eventos;
	}

	public int getTotalParticipante()	{
		return participantes.size();
	}

	public int getTotalEventos()	{
		return eventos.size();
	}

	public int gerarIdEvento() {
		if (eventos.isEmpty())
			return 1;
		else {
			Evento ultimo = eventos.get(eventos.size()-1);
			return ultimo.getId() + 1;
		}
	}
	
}

