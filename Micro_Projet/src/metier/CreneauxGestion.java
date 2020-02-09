package metier;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.ImplCreneaux;
import presentation.Creneaux;
import java.awt.Color;
import java.awt.Font;

public class CreneauxGestion extends JFrame {
	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private ImplCreneaux _impCreneau;
	private JPanel panel_1;
	private JButton btnAddCreneau;
	private JButton btnUpdateCreneau;
	private JButton btnDeleteCreneau;
	private JButton btnRetour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreneauxGestion frame = new CreneauxGestion();
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
	public CreneauxGestion() {
		setBackground(Color.WHITE);
		
		// create the DAO
		try {
			 _impCreneau = new ImplCreneaux();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Gestion des creneaux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 180, 630, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterLastName = new JLabel("Entrer la version du Creneau :");
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblEnterLastName.setForeground(new Color(100, 149, 237));
		lblEnterLastName.setBackground(new Color(0, 0, 255));
		panel.add(lblEnterLastName);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		btnSearch = new JButton("Chercher");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(new Color(100, 149, 237));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String version=String.valueOf(lastNameTextField.getText());
					List<Creneaux> Creneaux = null;

					if (version != null && version .trim().length() > 0) {
						Creneaux =  _impCreneau.ChercherCreneau(Integer.parseInt(version) );
					} else {
						Creneaux =  _impCreneau.getAllCreneaux();
					}
					
					CreneauxTableModel  model = new CreneauxTableModel (Creneaux);
					
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(CreneauxGestion.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
		panel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btnAddCreneau= new JButton("Ajouter Creneau");
		btnAddCreneau.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddCreneau.setForeground(Color.WHITE);
		btnAddCreneau.setBackground(new Color(100, 149, 237));
		btnAddCreneau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				CreneauDialog dialog = new CreneauDialog(CreneauxGestion.this,  _impCreneau);

				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddCreneau);
		
		btnUpdateCreneau = new JButton("Modifier Creneau");
		btnUpdateCreneau.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpdateCreneau.setForeground(Color.WHITE);
		btnUpdateCreneau.setBackground(new Color(100, 149, 237));
		btnUpdateCreneau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(CreneauxGestion.this, "Vous devez choisir un Creneau", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}

				Creneaux CreneauTmp = (Creneaux) table.getValueAt(row, CreneauxTableModel .OBJECT_COL);
				
				CreneauDialog dialog = new CreneauDialog(CreneauxGestion.this,  _impCreneau, CreneauTmp, true);
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnUpdateCreneau);
		
		btnDeleteCreneau = new JButton("Supprimer Creneau");
		btnDeleteCreneau.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteCreneau.setForeground(Color.WHITE);
		btnDeleteCreneau.setBackground(new Color(100, 149, 237));
		btnDeleteCreneau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int row=table.getSelectedRow();
					
					if(row <0) {
						JOptionPane.showMessageDialog(CreneauxGestion.this,"Vous devez choisir un Creneau","ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int response=JOptionPane.showConfirmDialog(
							CreneauxGestion.this,"Voulez vous vraiment supprimer ce Creneau?","Confirmer",
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
					if(response !=JOptionPane.YES_OPTION) {
						return;
					}
					
					Creneaux CreneauTmp=(Creneaux) table.getValueAt(row, CreneauxTableModel .OBJECT_COL);
				
					_impCreneau.SupprimerCreneau(CreneauTmp.getId());
				
					RefrechCreneau();
				
					JOptionPane.showMessageDialog(CreneauxGestion.this,
							"Creneau supprimé avec succés", "Creneau Supprimé",
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(CreneauxGestion.this,
							"Erreur de suppression du Creneau: "+exc.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnDeleteCreneau);
		
		btnRetour = new JButton("Retour");
		btnRetour.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRetour.setBackground(new Color(224, 255, 255));
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accueil_CabinetMedical ac=new Accueil_CabinetMedical();
				ac.setVisible(true);
			}
		});
		panel_1.add(btnRetour);
	}

	public void RefrechCreneau() {

		try {
			List<Creneaux> Creneaux =  _impCreneau.getAllCreneaux();

			// create the model and update the "table"
			CreneauxTableModel  model = new CreneauxTableModel (Creneaux);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}


}
