package appswing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import regras_negocio.Fachada;

public class TelaCaixa {
    private JDialog frame;
    private JTextField tfValor;
    private JTextField tfId;
    private JTextField tfCpf;
    private JTextField tfSenha;
    private JButton btnCreditar;
    private JButton btnDebitar;
    private JButton btnTransferir;

    public TelaCaixa() {
        initialize();
        frame.setVisible(true);
    }

    //Inicializando TelaCaixa
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Caixa");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        //ID
        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblId.setBounds(30, 30, 80, 25);
        frame.getContentPane().add(lblId);

        tfId = new JTextField();
        tfId.setFont(new Font("Dialog", Font.PLAIN, 14));
        tfId.setBounds(100, 30, 150, 25);
        frame.getContentPane().add(tfId);

        //CPF
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCpf.setBounds(30, 70, 80, 25);
        frame.getContentPane().add(lblCpf);

        tfCpf = new JTextField();
        tfCpf.setFont(new Font("Dialog", Font.PLAIN, 14));
        tfCpf.setBounds(100, 70, 150, 25);
        frame.getContentPane().add(tfCpf);

        //SENHA
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSenha.setBounds(30, 110, 80, 25);
        frame.getContentPane().add(lblSenha);

        tfSenha = new JTextField();
        tfSenha.setFont(new Font("Dialog", Font.PLAIN, 14));
        tfSenha.setBounds(100, 110, 150, 25);
        frame.getContentPane().add(tfSenha);

        //VALOR
        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblValor.setBounds(30, 150, 80, 25);
        frame.getContentPane().add(lblValor);

        tfValor = new JTextField();
        tfValor.setFont(new Font("Dialog", Font.PLAIN, 14));
        tfValor.setBounds(100, 150, 150, 25);
        frame.getContentPane().add(tfValor);

        //CREDITAR
        btnCreditar = new JButton("Creditar");
        btnCreditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCreditar.setBounds(30, 200, 100, 30);
        frame.getContentPane().add(btnCreditar);

        //DEBITAR
        btnDebitar = new JButton("Debitar");
        btnDebitar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDebitar.setBounds(150, 200, 100, 30);
        frame.getContentPane().add(btnDebitar);

        //TRANSFERIR
        btnTransferir = new JButton("Transferir");
        btnTransferir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTransferir.setBounds(270, 200, 100, 30);
        frame.getContentPane().add(btnTransferir);

        // Ação para creditar
        btnCreditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tfId.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    double valor = Double.parseDouble(tfValor.getText());

                    Fachada.creditarValor(id, cpf, senha, valor);
                    JOptionPane.showMessageDialog(frame, "Valor R$ " + valor + " creditado com sucesso.");
                    limparCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
                }
            }
        });

        // Ação para debitar
        btnDebitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tfId.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    double valor = Double.parseDouble(tfValor.getText());

                    Fachada.debitarValor(id, cpf, senha, valor);
                    JOptionPane.showMessageDialog(frame, "Valor R$ " + valor + " debitado com sucesso.");
                    limparCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
                }
            }
        });

        // Ação para transferir
        btnTransferir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idOrigem = Integer.parseInt(tfId.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    double valor = Double.parseDouble(tfValor.getText());

                    String idDestinoStr = JOptionPane.showInputDialog(frame, "Digite o ID da conta destino:");
                    if (idDestinoStr != null && !idDestinoStr.isEmpty()) {
                        int idDestino = Integer.parseInt(idDestinoStr);
                        Fachada.transferirValor(idOrigem, cpf, senha, valor, idDestino);
                        JOptionPane.showMessageDialog(frame, "Valor R$ " + valor + " transferido para a conta " + idDestino + " com sucesso.");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(frame, "ID da conta destino não pode ser vazio.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
                }
            }
        });
    }

    private void limparCampos() {
        tfId.setText("");
        tfCpf.setText("");
        tfSenha.setText("");
        tfValor.setText("");
    }
}
