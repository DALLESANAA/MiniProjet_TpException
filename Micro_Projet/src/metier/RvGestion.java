package metier;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentation.Rv;
import dao.ImplRv;

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

public class RvGestion extends JFrame {

	private JPanel contentPane;
	private JTextField id_clientTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private ImplRv _impRv;
	private JPanel panel_1;
	private JButton btnAddRv;
	private JButton btnUpdateRv;
	private JButton btnDeleteRv;
	private JButton btnRetour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RvGestion frame = new RvGestion();
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
	public RvGestion() {
		
		// create the DAO
		try {
			 _impRv = new ImplRv();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Gestion des Rendez-vous");
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
		
		JLabel lblEnterLastName = new JLabel("Entrer le id du client :");
		lblEnterLastName.setBackground(new Color(0, 0, 0));
		lblEnterLastName.setForeground(new Color(0, 0, 255));
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel.add(lblEnterLastName);
		
		id_clientTextField = new JTextField();
		panel.add(id_clientTextField);
		id_clientTextField.setColumns(16);
		
		btnSearch = new JButton("Afficher");
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBackground(new Color(30, 144, 255));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Rv> Rvs = null;
					Rvs = _impRv.getAllRvs();
					
					RvTableModel  model = new RvTableModel (Rvs);
					
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(RvGestion.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
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
		
		btnAddRv = new JButton("Ajouter Rendez-vous");
		btnAddRv.setBackground(new Color(30, 144, 255));
		btnAddRv.setForeground(new Color(255, 255, 255));
		btnAddRv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				RvDialog dialog = new RvDialog(RvGestion.this,  _impRv);

				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddRv);
		
		btnUpdateRv = new JButton("Modifier Rendez-vous");
		btnUpdateRv.setBackground(new Color(30, 144, 255));
		btnUpdateRv.setForeground(new Color(255, 255, 255));
		btnUpdateRv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(RvGestion.this, "Vous devez choisir un Rv", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}

				Rv RvTmp = (Rv) table.getValueAt(row, RvTableModel .OBJECT_COL);
				
				RvDialog dialog = new RvDialog(RvGestion.this,  _impRv, RvTmp, true);
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnUpdateRv);
		
		btnDeleteRv = new JButton("Supprimer Rendez-vous");
		btnDeleteRv.setBackground(new Color(30, 144, 255));
		btnDeleteRv.setForeground(new Color(255, 255, 255));
		btnDeleteRv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int row=table.getSelectedRow();
					
					if(row <0) {
						JOptionPane.showMessageDialog(RvGestion.this,"Vous devez choisir un Rv","ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int response=JOptionPane.showConfirmDialog(
							RvGestion.this,"Voulez vous vraiment supprimer ce Rv?","Confirmer",
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
					if(response !=JOptionPane.YES_OPTION) {
						return;
					}
					
					Rv RvTmp=(Rv) table.getValueAt(row, RvTableModel .OBJECT_COL);
				
					_impRv.SupprimerRv(RvTmp.getId());
				
					RefrechRv();
				
					JOptionPane.showMessageDialog(RvGestion.this,
							"Rv supprimé avec succés", "Rv Supprimé",
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(RvGestion.this,
							"Erreur de suppression du Rv: "+exc.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnDeleteRv);
		
		btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accueil_CabinetMedical ac=new Accueil_CabinetMedical();
				ac.setVisible(true);
			}
		});
		panel_1.add(btnRetour);
	}

	public void RefrechRv() {

		try {
			List<Rv> Rvs =  _impRv.getAllRvs();

			// create the model and update the "table"
			RvTableModel  model = new RvTableModel (Rvs);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
