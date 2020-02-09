package metier;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentation.Client;
import dao.ImpClient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class ClientGestion extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private ImpClient _impClient;
	private JPanel panel_1;
	private JButton btnAddClient;
	private JButton btnUpdateClient;
	private JButton btnDeleteClient;
	private JButton btnRetour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGestion frame = new ClientGestion();
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
	public ClientGestion() {
		
		// create the DAO
		try {
			 _impClient = new ImpClient();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Gestion des clients");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 180, 630, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterLastName = new JLabel("Entrer le nom du client :");
		lblEnterLastName.setBackground(new Color(0, 0, 0));
		lblEnterLastName.setForeground(new Color(0, 0, 255));
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel.add(lblEnterLastName);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(16);
		
		btnSearch = new JButton("Chercher");
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBackground(new Color(30, 144, 255));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nom = lastNameTextField.getText();

					List<Client> clients = null;

					if (nom  != null && nom .trim().length() > 0) {
						clients =  _impClient.ChercherClient(nom );
					} else {
						clients =  _impClient.getAllClients();
					}
					
					ClientTableModel  model = new ClientTableModel (clients);
					
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(ClientGestion.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
		panel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btnAddClient = new JButton("Ajouter Client");
		btnAddClient.setBackground(new Color(30, 144, 255));
		btnAddClient.setForeground(new Color(255, 255, 255));
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				ClientDialog dialog = new ClientDialog(ClientGestion.this,  _impClient);

				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddClient);
		
		btnUpdateClient = new JButton("Modifier Client");
		btnUpdateClient.setBackground(new Color(30, 144, 255));
		btnUpdateClient.setForeground(new Color(255, 255, 255));
		btnUpdateClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(ClientGestion.this, "Vous devez choisir un client", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}

				Client clientTmp = (Client) table.getValueAt(row, ClientTableModel .OBJECT_COL);
				
				ClientDialog dialog = new ClientDialog(ClientGestion.this,  _impClient, clientTmp, true);
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnUpdateClient);
		
		btnDeleteClient = new JButton("Supprimer Client");
		btnDeleteClient.setBackground(new Color(30, 144, 255));
		btnDeleteClient.setForeground(new Color(255, 255, 255));
		btnDeleteClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int row=table.getSelectedRow();
					
					if(row <0) {
						JOptionPane.showMessageDialog(ClientGestion.this,"Vous devez choisir un client","ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int response=JOptionPane.showConfirmDialog(
							ClientGestion.this,"Voulez vous vraiment supprimer ce client?","Confirmer",
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
					if(response !=JOptionPane.YES_OPTION) {
						return;
					}
					
					Client clientTmp=(Client) table.getValueAt(row, ClientTableModel .OBJECT_COL);
				
					_impClient.SupprimerClient(clientTmp.getId());
				
					RefrechClient();
				
					JOptionPane.showMessageDialog(ClientGestion.this,
							"Client supprimé avec succés", "Client Supprimé",
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(ClientGestion.this,
							"Erreur de suppression du client: "+exc.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnDeleteClient);
		
		btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accueil_CabinetMedical ac=new Accueil_CabinetMedical();
				ac.setVisible(true);
			}
		});
		panel_1.add(btnRetour);
	}

	public void RefrechClient() {

		try {
			List<Client> clients =  _impClient.getAllClients();

			// create the model and update the "table"
			ClientTableModel  model = new ClientTableModel (clients);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
