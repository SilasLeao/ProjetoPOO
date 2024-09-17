/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranhão Ayres
 **********************************/

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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Convidado;
import modelo.Evento;
import modelo.Participante;
import regras_negocio.Fachada;

public class TelaParticipantes {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_6;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button;
	private JButton button_1;
	private JLabel label_3;
	private JTextField textField_2;
	private JLabel label_4;
	private JTextField textField_3;
	private JLabel label_5;
	private JTextField textField_4;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;



	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaEventos window = new TelaEventos();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaParticipantes() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Participante/Convidado");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("selecione");
		label_6.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_6);

		label_1 = new JLabel("Digite parte do nome");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(21, 14, 128, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(159, 11, 137, 20);
		frame.getContentPane().add(textField);

		label_2 = new JLabel("email:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(21, 269, 71, 14);
		frame.getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(68, 264, 195, 20);
		frame.getContentPane().add(textField_1);

		button_1 = new JButton("Criar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty() || textField_2.getText().isEmpty()
							|| textField_3.getText().isEmpty()) {
						label.setText("campo vazio");
						return;
					}
					String email = textField_1.getText();
					String nome = textField_2.getText();
					String idade = textField_3.getText();
					String empresa = textField_4.getText();
					if(empresa.isEmpty())
						Fachada.criarParticipante(email,nome,Integer.parseInt(idade));
					else
						Fachada.criarConvidado(email,nome,Integer.parseInt(idade),empresa);

					label.setText("participante criado: ");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(535, 273, 86, 23);
		frame.getContentPane().add(button_1);

		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(306, 9, 89, 23);
		frame.getContentPane().add(button);

		label_3 = new JLabel("nome:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(281, 269, 63, 14);
		frame.getContentPane().add(label_3);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(336, 264, 168, 20);
		frame.getContentPane().add(textField_2);

		label_4 = new JLabel("idade:");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(21, 293, 71, 14);
		frame.getContentPane().add(label_4);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(68, 290, 45, 20);
		frame.getContentPane().add(textField_3);

		label_5 = new JLabel("empresa (convidado):");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(207, 295, 119, 14);
		frame.getContentPane().add(label_5);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(336, 290, 168, 20);
		frame.getContentPane().add(textField_4);

		button_2 = new JButton("Apagar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nome = (String) table.getValueAt( table.getSelectedRow(), 1);
						Fachada.apagarParticipante(nome);
						label.setText("deletou participante " +nome);
						listagem();
					}
					else
						label.setText("participante nao selecionado");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(309, 213, 86, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Adicionar Evento");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nome = (String) table.getValueAt( table.getSelectedRow(), 1);
						String id = JOptionPane.showInputDialog(frame, "Digite o id");
						Participante p = Fachada.localizarParticipante(nome);
						Evento ev = Fachada.localizarEvento(Integer.parseInt(id));
						double valor = p.getPago(ev.getPreco());

						JOptionPane.showMessageDialog(frame, "Preco do evento="+ev.getPreco()+ " - Valor a ser pago =" +valor);

						Object[] options = { "Confirmar", "Desistir" };
						int escolha = JOptionPane.showOptionDialog(null, "Confirma inscricao do evento "+id, "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.adicionarParticipanteEvento(nome, Integer.parseInt(id));
							label.setText(p.getEmail()+ " recebera boleto de pagamento de "+valor);
							listagem();
						}
						else
							label.setText("nao adicionou evento " +id );

					}
					else
						label.setText("participante nao selecionado");
				}
				catch(NumberFormatException ex) {
					label.setText("formato do id invalido");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.setBounds(411, 213, 137, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("Remover Evento");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nome = (String) table.getValueAt( table.getSelectedRow(), 1);
						String id = JOptionPane.showInputDialog(frame, "Digite o id");
						Participante p = Fachada.localizarParticipante(nome);
						Evento ev = Fachada.localizarEvento(Integer.parseInt(id));
						double valor = p.getPago(ev.getPreco());

						JOptionPane.showMessageDialog(frame, "Preco do evento="+ev.getPreco()+ " - Valor que foi pago =" +valor);

						Object[] options = { "Confirmar", "Desistir" };
						int escolha = JOptionPane.showOptionDialog(null, "Confirma remocao do evento "+id, "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.removerParticipanteEvento(nome, Integer.parseInt(id));
							label.setText("vc recebera restituicao de "+valor);
							listagem();
						}
						else
							label.setText("nao removeu evento " +id );

					}
					else
						label.setText("participante nao selecionado");
				}
				catch(NumberFormatException ex) {
					label.setText("formato do id invalido");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.setBounds(558, 213, 137, 23);
		frame.getContentPane().add(button_4);

		button_5 = new JButton("Limpar");
		button_5.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) {
						textField.setText("");
						textField.requestFocus();
					}
				}
				);
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_5.setBounds(402, 10, 89, 23);
		frame.getContentPane().add(button_5);
	}

	public void listagem() {
		try{
			List<Participante> lista = Fachada.listarParticipantes(textField.getText());

			//			//***************************************************************
			//			
			//			//Alternativa de ordenacao 1 (por nome)
			//			Collections.sort(lista);
			//						
			//			//Alternativa de ordenacao 2
			//			Collections.sort(lista, new Comparator<Participante>() {
			//				public int compare(Participante p1, Participante p2) {
			//					int idade1 = p1.getIdade();
			//					int idade2 = p2.getIdade();
			//					return Integer.compare(idade1, idade2);
			//				}
			//			});

			//			//Alternativa de ordenacao 3
			//			Collections.sort(lista, new Comparator<Participante>() {
			//				public int compare(Participante p1, Participante p2) {
			//					String nome1 = p1.getNome();
			//					String nome2 = p2.getNome();
			//					return nome1.compareTo(nome2);
			//				}
			//			});
			//
			//			//Alternativa de ordenacao 4
			//			Collections.sort(lista, (p1,p2) -> p1.getNome().compareTo(p2.getNome()));
			//			
			//			//***************************************************************

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//colunas
			model.addColumn("emal");
			model.addColumn("nome");
			model.addColumn("idade");
			model.addColumn("empresa");
			model.addColumn("%desconto");
			model.addColumn("eventos");

			//linhas
			String texto;
			for(Participante p : lista) {

				if(p.getEventos().isEmpty())
					texto="sem eventos ";
				else {
					texto=" ";
					for(Evento ev : p.getEventos()) 
						texto += ev.getId()+ " " ;
				}

				if(p instanceof Convidado c)
					model.addRow(new Object[]{p.getEmail(), p.getNome(), p.getIdade(), c.getEmpresa(),p.getPercentual(), texto});
				else
					model.addRow(new Object[]{p.getEmail(), p.getNome(), p.getIdade(), "",p.getPercentual(), texto});

			}

			table.setModel(model);
			label_6.setText("resultados: "+lista.size()+ " participantes   - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}


}
