package metier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ConnectionDB;
import dao.ImplCreneaux;
import presentation.Creneaux;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Font;

public class CreneauDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField versionTextField;
	private JTextField hdebutTextField;
	private JTextField mdebutTextField;
	private JTextField hfineTextField;
	private JTextField mfinTextField;
	private JComboBox<String> id_MedecinComboBox;


	private ImplCreneaux impCreneau;

	private CreneauxGestion CreneauGestion;
	
	private Creneaux precedCreneau=null;
	private boolean modif=false;
	
	

	public CreneauDialog(CreneauxGestion _CreneauGestion,  ImplCreneaux _impCreneau, Creneaux _precedCreneau,boolean _modif) {
		this();
		impCreneau = _impCreneau;
		CreneauGestion = _CreneauGestion;
		precedCreneau=_precedCreneau;
		modif=_modif;
		
		if(modif) {
			setTitle("Modifier Creneau");
			TextFieldRemplis(precedCreneau);
		}
	}
	private void TextFieldRemplis(Creneaux leCreneau) {

		versionTextField.setText(String.valueOf(leCreneau.getVersion()));
		hdebutTextField.setText(String.valueOf(leCreneau.getHdebut()));
		mdebutTextField.setText(String.valueOf(leCreneau.getMdebut()));
		hfineTextField.setText(String.valueOf(leCreneau.getHfin()));
		mfinTextField.setText(String.valueOf(leCreneau.getMfin()));
		
	}

	
	public CreneauDialog (CreneauxGestion CreneauGestion,
			ImplCreneaux leCreneauimp) {
		this(CreneauGestion, leCreneauimp, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public CreneauDialog() {
		setBounds(320, 180, 630, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Version");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(295, 76, 73, 14);
		contentPanel.add(lblNewLabel);
		
		versionTextField = new JTextField();
		versionTextField.setBounds(415, 70, 96, 20);
		contentPanel.add(versionTextField);
		versionTextField.setColumns(10);
		
		JLabel lblHDbut = new JLabel("Heure d\u00E9but:");
		lblHDbut.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblHDbut.setBounds(295, 111, 95, 14);
		contentPanel.add(lblHDbut);
		
		hdebutTextField = new JTextField();
		hdebutTextField.setBounds(415, 108, 96, 20);
		contentPanel.add(hdebutTextField);
		hdebutTextField.setColumns(10);
		
		JLabel lblMDbut = new JLabel("Min d\u00E9but :");
		lblMDbut.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMDbut.setBounds(295, 142, 95, 14);
		contentPanel.add(lblMDbut);
		
		mdebutTextField = new JTextField();
		mdebutTextField.setBounds(415, 139, 96, 20);
		contentPanel.add(mdebutTextField);
		mdebutTextField.setColumns(10);
		
		JLabel lblHFin = new JLabel("Heure fin :");
		lblHFin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblHFin.setBounds(295, 173, 75, 14);
		contentPanel.add(lblHFin);
		
		hfineTextField = new JTextField();
		hfineTextField.setBounds(415, 170, 96, 20);
		contentPanel.add(hfineTextField);
		hfineTextField.setColumns(10);
		
		JLabel lblMFin = new JLabel("Min fin:");
		lblMFin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblMFin.setBounds(295, 204, 75, 14);
		contentPanel.add(lblMFin);
		
		mfinTextField = new JTextField();
		mfinTextField.setBounds(415, 201, 96, 20);
		contentPanel.add(mfinTextField);
		mfinTextField.setColumns(10);
		
		JLabel lblIdMedecin = new JLabel("Id medecin :");
		lblIdMedecin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblIdMedecin.setBounds(295, 236, 95, 14);
		contentPanel.add(lblIdMedecin);
		
	    id_MedecinComboBox = new JComboBox<String>();
		id_MedecinComboBox.setBounds(415, 232, 96, 22);
		contentPanel.add(id_MedecinComboBox);
		rempliircombobox("medecins",id_MedecinComboBox);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setBounds(0, 0, 223, 288);
		contentPanel.add(panel);
		
		JLabel lblRemplirLesChamps = new JLabel("Remplir les champs");
		lblRemplirLesChamps.setForeground(new Color(0, 0, 255));
		lblRemplirLesChamps.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblRemplirLesChamps.setBounds(271, 11, 273, 35);
		contentPanel.add(lblRemplirLesChamps);
		

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(100, 149, 237));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Enregistrer");
				okButton.setBackground(new Color(176, 224, 230));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveCreneau();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	protected void saveCreneau() {
		int version = Integer.parseInt(versionTextField.getText());
		int hdebut = Integer.parseInt(hdebutTextField.getText());
		int mdebut = Integer.parseInt(mdebutTextField.getText());
		int hfin= Integer.parseInt(hfineTextField.getText());
		int mfin = Integer.parseInt(mfinTextField.getText());
		
		/*String sid_medecin =id_MedecinComboBox.getSelectedItem().toString();	
		int id_medecin=Integer.parseInt(sid_medecin);*/
		
		//String x = String.valueOf(id_MedecinComboBox.getSelectedItem());
		//Integer id_medecin=(Integer)(id_MedecinComboBox.getSelectedItem());
		//Integer id_medecin=Integer.parseInt(x);
		
		int id_medecin=Integer.parseInt((String)id_MedecinComboBox.getSelectedItem());

		

		Creneaux CreneauTmp=null;
		if(modif) {
			
			CreneauTmp=precedCreneau;
			
			CreneauTmp.setVersion(version);
			CreneauTmp.setHdebut(hdebut);
			CreneauTmp.setMdebut(mdebut);
			CreneauTmp.setHfin(hfin);
			CreneauTmp.setMfin(mfin);
			CreneauTmp.seId_medecin(id_medecin);
			CreneauTmp=new Creneaux(version,hdebut,mdebut,hfin,mfin,id_medecin);

		}else {
			CreneauTmp=new Creneaux(version,hdebut,mdebut,hfin,mfin,id_medecin);
			//CreneauTmp=new Creneaux(version,hdebut,mdebut,hfin,mfin,6);
		}
		try {
			//save to the database
			if(modif) {
				impCreneau.ModifierCreneau(CreneauTmp);
			}else {
				impCreneau.AjouterCreneau(CreneauTmp);
			}
		
		setVisible(false);
		dispose();

		CreneauGestion.RefrechCreneau();

		JOptionPane.showMessageDialog(CreneauGestion,
				"Creneau enregistré avec succés.", "Creneau enregistré",
				JOptionPane.INFORMATION_MESSAGE);
	} catch (Exception exc) {
		JOptionPane.showMessageDialog(CreneauGestion,
				"Erreur d'enregistrement du Creneau: " + exc.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
	
	public void rempliircombobox(String table,JComboBox combobox) {
		try {
			Statement myStmt = ConnectionDB.getConnectionInstance().createStatement();
			ResultSet myRs = myStmt.executeQuery("select *  from "+table);
			
			while(myRs .next())
				combobox.addItem(myRs.getString(1));
				//combobox.addItem(myRs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
