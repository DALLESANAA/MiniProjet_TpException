package metier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import presentation.Medecin;
import dao.ImplMedecin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class MedecinGestion extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private ImplMedecin _impMedecin;
	private JPanel panel_1;
	private JButton btnAddMedecin;
	private JButton btnUpdateMedecin;
	private JButton btnDeleteMedecin;
	private JButton btnRetour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedecinGestion frame = new MedecinGestion();
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
	public MedecinGestion() {
		
		// create the DAO
		try {
			_impMedecin = new ImplMedecin();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Gestion des medecins");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 180, 630, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterLastName = new JLabel("Entrer le nom du medecin :");
		lblEnterLastName.setBackground(new Color(0, 0, 0));
		lblEnterLastName.setForeground(new Color(0, 0, 255));
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel.add(lblEnterLastName);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		btnSearch = new JButton("Chercher");
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBackground(new Color(30, 144, 255));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nom = lastNameTextField.getText();

					List<Medecin> medecins = null;

					if (nom  != null && nom .trim().length() > 0) {
						medecins =  _impMedecin.ChercherMedecin(nom );
					} else {
						medecins =  _impMedecin.getAllMedecins();
					}
					
					MedecinTableModel  model = new MedecinTableModel (medecins);
					
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(MedecinGestion.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
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
		
		btnAddMedecin = new JButton("Ajouter Medecin");
		btnAddMedecin.setBackground(new Color(30, 144, 255));
		btnAddMedecin.setForeground(new Color(255, 255, 255));
		btnAddMedecin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				MedecinDialog dialog = new MedecinDialog(MedecinGestion.this,_impMedecin);

				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddMedecin);
		
		btnUpdateMedecin = new JButton("Modifier Medecin");
		btnUpdateMedecin.setBackground(new Color(30, 144, 255));
		btnUpdateMedecin.setForeground(new Color(255, 255, 255));
		btnUpdateMedecin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(MedecinGestion.this, "Vous devez choisir un medecin", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}

				Medecin medecinTmp = (Medecin) table.getValueAt(row, MedecinTableModel .OBJECT_COL);
				
				MedecinDialog dialog = new MedecinDialog(MedecinGestion.this,  _impMedecin, medecinTmp, true);
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnUpdateMedecin);
		
		btnDeleteMedecin = new JButton("Supprimer Medecin");
		btnDeleteMedecin.setBackground(new Color(30, 144, 255));
		btnDeleteMedecin.setForeground(new Color(255, 255, 255));
		btnDeleteMedecin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int row=table.getSelectedRow();
					
					if(row <0) {
						JOptionPane.showMessageDialog(MedecinGestion.this,"Vous devez choisir un medecin","ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int response=JOptionPane.showConfirmDialog(
							MedecinGestion.this,"Voulez vous vraiment supprimer ce medecin?","Confirmer",
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
					if(response !=JOptionPane.YES_OPTION) {
						return;
					}
					
					Medecin medecinTmp=(Medecin) table.getValueAt(row, MedecinTableModel .OBJECT_COL);
				
					_impMedecin.SupprimerMedecin(medecinTmp.getId());
				
					RefrechMedecin();
				
					JOptionPane.showMessageDialog(MedecinGestion.this,
							"Medecin supprimé avec succés", "Medecin Supprimé",
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(MedecinGestion.this,
							"Erreur de suppression du medecin: "+exc.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnDeleteMedecin);
		
		btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accueil_CabinetMedical ac=new Accueil_CabinetMedical();
				ac.setVisible(true);
			}
		});
		panel_1.add(btnRetour);
	}

	public void RefrechMedecin() {

		try {
			List<Medecin> medecins =  _impMedecin.getAllMedecins();

			// create the model and update the "table"
			MedecinTableModel  model = new MedecinTableModel (medecins);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
