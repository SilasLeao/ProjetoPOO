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
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import modelo.Convidado;
import modelo.Evento;
import modelo.Participante;
import regras_negocio.Fachada;

public class TelaEventos {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JButton button_4;
	private JButton button_2;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_5;
	private JLabel label_3;
	private JLabel label_8;
	private JLabel label_6;
	private JButton button_3;
	private JButton button_5;
	private JFormattedTextField formattedTextField;

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
	public TelaEventos() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		frame.setTitle("Eventos");
		frame.setBounds(100, 100, 912, 351);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 42, 844, 120);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		button = new JButton("Criar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(formattedTextField.getText().isEmpty() || 
							textField_1.getText().isEmpty() ||
							textField_2.getText().isEmpty()) 
					{
						label.setText("campo vazio");
						return;
					}

					String data = formattedTextField.getText();
					String descricao = textField_1.getText();
					String preco = textField_2.getText();
					Fachada.criarEvento(data, descricao, Double.parseDouble(preco));
					label.setText("evento criado: ");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(281, 238, 95, 23);
		frame.getContentPane().add(button);

		button_1 = new JButton("Apagar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String data = (String) table.getValueAt( table.getSelectedRow(), 1);

						Object[] options = { "Confirmar", "Cancelar" };
						int escolha = JOptionPane.showOptionDialog(null, "Confirma cancelamento do evento "+data, "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.apagarEvento(data);
							label.setText("evento cancelado e participantes removidos: "+ data);
							listagem();
						}
						else
							label.setText("nao cancelou evento " +data );
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(670, 180, 95, 23);
		frame.getContentPane().add(button_1);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		label.setBounds(26, 287, 830, 14);
		frame.getContentPane().add(label);

		label_1 = new JLabel("data");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_1.setBounds(26, 214, 71, 14);
		frame.getContentPane().add(label_1);

		label_5 = new JLabel("descricao");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_5.setBounds(26, 242, 71, 14);
		frame.getContentPane().add(label_5);

		try {
			formattedTextField = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} 
		catch (ParseException e1) {}
		formattedTextField.setBounds(67, 212, 80, 20);
		frame.getContentPane().add(formattedTextField);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(92, 239, 169, 20);
		frame.getContentPane().add(textField_1);

		label_3 = new JLabel("(dd/mm/aaaa)");
		label_3.setBounds(158, 215, 88, 14);
		frame.getContentPane().add(label_3);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 163, 561, 14);
		frame.getContentPane().add(label_8);

		label_6 = new JLabel("preco");
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_6.setBounds(26, 266, 43, 14);
		frame.getContentPane().add(label_6);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(67, 263, 71, 20);
		frame.getContentPane().add(textField_2);

		button_4 = new JButton("Listar");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_4.setBounds(410, 8, 95, 23);
		frame.getContentPane().add(button_4);

		button_2 = new JButton("Adiar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String data = (String) table.getValueAt( table.getSelectedRow(), 1);
						String novadata = JOptionPane.showInputDialog(frame,"Digite a nova data (dd/mm/aaaa)");
						Fachada.adiarEvento(data,novadata);
						label.setText("evento adiado para : "+novadata);
						listagem();
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(775, 180, 95, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Ver convidados");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String id = (String) table.getValueAt( table.getSelectedRow(), 0);
						Evento ev = Fachada.localizarEvento(Integer.parseInt(id));
						String nomes= "Nomes dos convidados:";
						for(Convidado c : ev.getConvidados())
							nomes+="\n"+c.getNome();

						JOptionPane.showMessageDialog(frame, nomes);
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.setBounds(467, 180, 135, 23);
		frame.getContentPane().add(button_3);

		button_5 = new JButton("Ver participantes");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String id = (String) table.getValueAt( table.getSelectedRow(), 0);
						Evento ev = Fachada.localizarEvento(Integer.parseInt(id));
						String nomes= "Nomes dos participantes:";
						for(Participante p : ev.getParticipantes())
							nomes+="\n"+p.getNome();

						JOptionPane.showMessageDialog(frame, nomes);
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_5.setBounds(322, 180, 135, 23);
		frame.getContentPane().add(button_5);

	

	}

	//*****************************
	public void listagem () {
		try{
			List<Evento> lista = Fachada.listarEventos();

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("descricao");
			model.addColumn("preco");
			model.addColumn("total pago");
			model.addColumn("med.idade");
			model.addColumn("gratuidades");
			model.addColumn("convidados");
			//linhas
			for(Evento ev : lista) {
				model.addRow(new Object[]{ev.getId()+"", ev.getData(), ev.getDescricao(), ev.getPreco(),ev.getTotalPago(), ev.getIdadeMedia(), ev.contarGratuidades(), ev.contarConvidados()});
			}

			table.setModel(model);
			label_8.setText("resultados: "+lista.size()+ " eventos  - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}

	}
}
