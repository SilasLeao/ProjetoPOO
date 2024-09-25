package appswing;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class TelaPrincipal {
	JFrame frame;
	private JMenu mnCorrentista;
	private JMenu mnConta;
	private JMenu mnCaixa;
	private JLabel label;

	// Inicializar a tela
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Criar a tela
	public TelaPrincipal() {
		initialize();
		frame.setVisible(true);
	}

	//Inicializar os contents da tela
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Banko");
		frame.setBounds(100, 100, 450, 363);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 450, 313);
		frame.getContentPane().add(label);
		frame.setResizable(false);
		
		//Menu do Correntista
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnCorrentista = new JMenu("Correntista");
		mnCorrentista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaCorrentistas tela = new TelaCorrentistas();
			}
		});
		menuBar.add(mnCorrentista);

		//Menu da Conta
		mnConta = new JMenu("Conta");
		mnConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaConta tela = new TelaConta();
			}
		});
		menuBar.add(mnConta);

		//Menu do Caixa
		mnCaixa = new JMenu("Caixa");
        mnCaixa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaCaixa tela = new TelaCaixa();
            }
        });
        menuBar.add(mnCaixa);
	}

}