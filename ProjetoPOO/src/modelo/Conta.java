package modelo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Conta {
    protected static int contadorId = carregarContadorId(); // Carregar o contador ao iniciar
    protected int id;
    protected String data;
    protected double saldo;
    protected ArrayList<Correntista> correntistas = new ArrayList<>();

    // Construtor sem parâmetros
    public Conta() throws IOException {
        this.id = contadorId++;
        // Data ja formatada
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.now().format(formato);
        
        this.saldo = 0;
        salvarContadorId(); // Salva o contador a cada criação de conta
    }

    // Construtor com parâmetros para leitura
    public Conta(int id, String data, double saldo) {
        this.id = id;
        this.data = data;
        this.saldo = saldo;
    }

    private static int carregarContadorId() {
        try {
            File f = new File(new File(".\\contadorId.csv").getCanonicalPath());
            if (!f.exists()) {
                return 0; // Retorna 0 se o arquivo não existir
            }

            try (Scanner scanner = new Scanner(new FileReader(f))) {
                if (scanner.hasNextLine()) {
                    return Integer.parseInt(scanner.nextLine());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contador de ID: " + e.getMessage());
        }
        return 0; // Retorna 0 em caso de erro
    }

    public static void salvarContadorId() {
        try {
            File f = new File(new File(".\\contadorId.csv").getCanonicalPath());
            try (FileWriter fw = new FileWriter(f)) {
                fw.write(String.valueOf(contadorId));
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o contador de ID: " + e.getMessage());
        }
    }

    public void creditar(double valor) {
        this.saldo += valor;
    }

    public void debitar(double valor) throws Exception {
        if (valor > this.saldo) {
            throw new Exception("Saldo insuficiente");
        }
        this.saldo -= valor;
    }

    public void transferir(double valor, Conta destino) throws Exception {
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
        if (correntistas.get(0).getCpf().equals(cpf)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Conta [id=" + id + ", data=" + data + ", saldo=" + saldo + "]";
    }
}
