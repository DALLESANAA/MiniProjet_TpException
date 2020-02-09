package metier;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Accueil_CabinetMedical extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accueil_CabinetMedical frame = new Accueil_CabinetMedical();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Accueil_CabinetMedical() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 180, 630, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Gestion des creneaux");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreneauxGestion cg=new CreneauxGestion();
				cg.setVisible(true);
			}
		});
		btnNewButton.setBounds(305, 217, 165, 61);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("G Rendez-vous");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RvGestion rg=new RvGestion();
				rg.setVisible(true);
			}
			
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(305, 107, 165, 61);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Gestion Medecins");
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MedecinGestion gm=new MedecinGestion();
				gm.setVisible(true);
			}
		});
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBounds(126, 107, 165, 61);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Gestion des clients");
		btnNewButton_3.setBackground(new Color(255, 255, 255));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientGestion cg=new ClientGestion();
				cg.setVisible(true);
			}
		});
		btnNewButton_3.setForeground(Color.BLACK);
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBounds(126, 217, 169, 61);
		contentPane.add(btnNewButton_3);
		
		JLabel medecin = new JLabel("");
		medecin.setIcon(new ImageIcon("C:\\eclipse_workspace_ISIL\\JDBC\\3\\Images\\med.png"));
		medecin.setBounds(10, 58, 89, 122);
		contentPane.add(medecin);
		
		JLabel client = new JLabel("");
		client.setIcon(new ImageIcon("C:\\eclipse_workspace_ISIL\\JDBC\\3\\Images\\client.png"));
		client.setBounds(6, 191, 100, 127);
		contentPane.add(client);
		
		JLabel rv = new JLabel("");
		rv.setIcon(new ImageIcon("C:\\eclipse_workspace_ISIL\\JDBC\\3\\Images\\rv1.png"));
		rv.setBounds(485, 58, 129, 138);
		contentPane.add(rv);
		
		JLabel cren = new JLabel("");
		cren.setIcon(new ImageIcon("C:\\eclipse_workspace_ISIL\\JDBC\\3\\Images\\cren.png"));
		cren.setBounds(480, 191, 110, 127);
		contentPane.add(cren);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 614, 51);
		contentPane.add(panel);
		
		JLabel lblGestionDeCabinet = new JLabel("Gestion de cabinet medicale");
		panel.add(lblGestionDeCabinet);
		lblGestionDeCabinet.setForeground(Color.BLACK);
		lblGestionDeCabinet.setFont(new Font("Segoe Print", Font.BOLD, 22));
		lblGestionDeCabinet.setBackground(Color.WHITE);
		setUndecorated(false);
	}
}
