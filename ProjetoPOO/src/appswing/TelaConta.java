package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Conta;
import modelo.ContaEspecial;
import regras_negocio.Fachada;

public class TelaConta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriarContaNormal;
    private JButton btnCriarContaEspecial;
    private JButton btnApagarConta;
    private JButton btnAdicionarCotitular;
    private JButton btnRemoverCotitular;
    private JTextField textFieldCpf;
    private JTextField textFieldLimite;
    private JLabel label;
    private JLabel labelCpf;
    private JLabel labelLimite;
    private JLabel labelSaldoTotal;

    public TelaConta() {
        initialize();
        frame.setVisible(true);
    }

    //Inicialiando TelaConta
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciar Contas");
        frame.setBounds(100, 100, 912, 351);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(26, 42, 844, 120);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBounds(26, 287, 830, 14);
        frame.getContentPane().add(label);

        //digitar CPF
        labelCpf = new JLabel("CPF:");
        labelCpf.setFont(new Font("Dialog", Font.PLAIN, 12));
        labelCpf.setBounds(26, 180, 60, 14);
        frame.getContentPane().add(labelCpf);

        textFieldCpf = new JTextField();
        textFieldCpf.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCpf.setBounds(90, 180, 120, 20);
        frame.getContentPane().add(textFieldCpf);

        //digitar LIMITE
        labelLimite = new JLabel("Limite:");
        labelLimite.setFont(new Font("Dialog", Font.PLAIN, 12));
        labelLimite.setBounds(220, 180, 60, 14);
        frame.getContentPane().add(labelLimite);

        textFieldLimite = new JTextField();
        textFieldLimite.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldLimite.setBounds(280, 180, 120, 20);
        frame.getContentPane().add(textFieldLimite);

        //botão CRIAR CONTA NORMAL
        btnCriarContaNormal = new JButton("Criar conta normal");
        btnCriarContaNormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCriarContaNormal.setBounds(420, 180, 150, 23);
        frame.getContentPane().add(btnCriarContaNormal);
        btnCriarContaNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarContaNormal();
            }
        });
        
        //botão CRIAR CONTA ESPECIAL
        btnCriarContaEspecial = new JButton("Criar conta especial");
        btnCriarContaEspecial.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCriarContaEspecial.setBounds(580, 180, 150, 23);
        frame.getContentPane().add(btnCriarContaEspecial);
        btnCriarContaEspecial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarContaEspecial();
            }
        });
        
        //botão APAGAR CONTA
        btnApagarConta = new JButton("Apagar conta");
        btnApagarConta.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnApagarConta.setBounds(420, 220, 150, 23);
        frame.getContentPane().add(btnApagarConta);
        btnApagarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarConta();
            }
        });

        //botão ADICIONAR COTITULAR
        btnAdicionarCotitular = new JButton("Adicionar cotitular");
        btnAdicionarCotitular.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAdicionarCotitular.setBounds(580, 220, 150, 23);
        frame.getContentPane().add(btnAdicionarCotitular);
        btnAdicionarCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarCotitular();
            }
        });

        // botão REMOVER COTITULAR
        btnRemoverCotitular = new JButton("Remover cotitular");
        btnRemoverCotitular.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRemoverCotitular.setBounds(420, 260, 150, 23);
        frame.getContentPane().add(btnRemoverCotitular);
        btnRemoverCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerCotitular();
            }
        });

        //SALDO TOTAL
        labelSaldoTotal = new JLabel("Saldo Total: ");
        labelSaldoTotal.setFont(new Font("Dialog", Font.BOLD, 12));
        labelSaldoTotal.setBounds(26, 220, 200, 14);
        frame.getContentPane().add(labelSaldoTotal);

        // Carregar a listagem das contas ao abrir a janela
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });
    }

    //Criar Conta Normal chamando método da Fachada: Fachada.criarConta()
    private void criarContaNormal() {
        String cpf = textFieldCpf.getText();
        if (cpf.isEmpty()) {
            label.setText("CPF não pode estar vazio");
            return;
        }
        try {
            Fachada.criarConta(cpf);
            label.setText("Conta normal criada com sucesso!");
            listagem();
        } catch (Exception e) {
            label.setText("Erro ao criar conta: " + e.getMessage());
        }
    }
    //Criar Conta Especial chamando método da Fachada: Fachada.criarContaEspecial()
    private void criarContaEspecial() {
        String cpf = textFieldCpf.getText();
        String limite = textFieldLimite.getText();
        if (cpf.isEmpty() || limite.isEmpty()) {
            label.setText("CPF e limite não podem estar vazios");
            return;
        }
        try {
            double limiteValor = Double.parseDouble(limite);
            Fachada.criarContaEspecial(cpf, limiteValor);
            label.setText("Conta especial criada com sucesso!");
            listagem();
        } catch (Exception e) {
            label.setText("Erro ao criar conta especial: " + e.getMessage());
        }
    }
    
    //Apagar Conta chamando método da Fachada: Fachada.apagarConta()
    private void apagarConta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            label.setText("Selecione uma conta para apagar");
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 1);
        try {
            Fachada.apagarConta(id);
            label.setText("Conta apagada com sucesso!");
            listagem();
        } catch (Exception e) {
            label.setText("Erro ao apagar conta: " + e.getMessage());
        }
    }
    
    //Adicionar Cotitular chamando método da Fachada: Fachada.inserirCorrentistaConta()
    private void adicionarCotitular() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            label.setText("Selecione uma conta para adicionar cotitular");
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 1);
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cotitular:");
        try {
            Fachada.inserirCorrentistaConta(id, cpf);
            label.setText("Cotitular adicionado com sucesso!");
        } catch (Exception e) {
            label.setText("Erro ao adicionar cotitular: " + e.getMessage());
        }
    }
    
    //Remover Cotitular chamando método da Fachada: Fachada.removerCorrentistaConta()
    private void removerCotitular() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            label.setText("Selecione uma conta para remover cotitular");
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 1);
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cotitular a ser removido:");
        try {
            Fachada.removerCorrentistaConta(id, cpf);
            label.setText("Cotitular removido com sucesso!");
        } catch (Exception e) {
            label.setText("Erro ao remover cotitular: " + e.getMessage());
        }
    }

    //Listagem -> listagem com os dados: Tipo da conta, Id, Saldo, Limite
    private void listagem() {
        try {
            List<Conta> lista = Fachada.listarContas();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tipo da Conta");
            model.addColumn("Id");
            model.addColumn("Saldo");
            model.addColumn("Limite");
            for (Conta c : lista) {
                if (c instanceof ContaEspecial) {
                    ContaEspecial ce = (ContaEspecial) c; //casting
                    model.addRow(new Object[] { "Especial", ce.getId(), ce.getSaldo(), ce.getLimite() });
                } else {
                    model.addRow(new Object[] { "Normal", c.getId(), c.getSaldo(), "" });
                }
            }
            table.setModel(model);
            label.setText("Listagem atualizada.");
            //atualizarSaldoTotal();
        } catch (Exception e) {
            label.setText("Erro na listagem: " + e.getMessage());
        }
    }

}
