package appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import regras_negocio.Fachada;
import modelo.Correntista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCorrentistas {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel label;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtSenha;
    private JButton btnCriar;

    
    public TelaCorrentistas() {
        inicializar();
    }
    
    //Inicializando TelaCorrentistas
    private void inicializar() {
        frame = new JFrame();
        frame.setTitle("Gerenciamento de Correntistas");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(30, 30, 80, 25);
        frame.getContentPane().add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(100, 30, 150, 25);
        frame.getContentPane().add(txtCpf);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 70, 80, 25);
        frame.getContentPane().add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 70, 150, 25);
        frame.getContentPane().add(txtNome);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(30, 110, 80, 25);
        frame.getContentPane().add(lblSenha);

        txtSenha = new JTextField();
        txtSenha.setBounds(100, 110, 150, 25);
        frame.getContentPane().add(txtSenha);

        btnCriar = new JButton("Criar Correntista");
        btnCriar.setBounds(300, 30, 150, 30);
        frame.getContentPane().add(btnCriar);

        label = new JLabel("");
        label.setBounds(30, 150, 400, 25);
        frame.getContentPane().add(label);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 190, 500, 150);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        listarCorrentistas();
        
        //Botão pra Criar um Correntista
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarCorrentista();
            }
        });


        frame.setVisible(true);
    }

  //Criar um Correntista chamando método da Fachada: Fachada.criarCorrentista()
    private void criarCorrentista() {
        try {
            String cpf = txtCpf.getText();
            String nome = txtNome.getText();
            String senha = txtSenha.getText();
            
            // Chama o método da fachada para criar o correntista
            Fachada.criarCorrentista(cpf, nome, senha);
            label.setText("Correntista criado com sucesso.");
            listarCorrentistas(); // Atualiza a listagem após criar
        } catch (Exception e) {
            label.setText("Erro ao criar correntista: " + e.getMessage());
        }
    }

    //Listar Correnstistas chamando método da Fachada: Fachada.listarCorrentistas()
    private void listarCorrentistas() {
        try {
        	//Criando lista pra armazenar dados
            List<Correntista> lista = Fachada.listarCorrentistas();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("CPF");
            model.addColumn("Nome");
            model.addColumn("Senha"); 
            model.addColumn("Titular de Conta");
            model.addColumn("IDs das Contas");
    
            //Loop for para percorrer a lista de correntistas
            for (Correntista c : lista) {
                StringBuilder idsContas = new StringBuilder();
                if (c.getContas().isEmpty()) {
                    idsContas.append("Nenhuma");
                } else {
                    // Adiciona apenas os IDs das contas associadas ao correntista
                    c.getContas().forEach(conta -> idsContas.append(conta.getId()).append(", "));
                    // Remove a última vírgula e espaço
                    if (idsContas.length() > 0) {
                        idsContas.setLength(idsContas.length() - 2);
                    }
                }
                model.addRow(new Object[]{c.getCpf(), c.getNome(), c.getSenha(), c.isTitular(), idsContas.toString()});
            }
    
            table.setModel(model);
            label.setText("Listagem atualizada.");
        } catch (Exception e) {
            label.setText("Erro ao listar correntistas: " + e.getMessage());
        }
    }
    
}
