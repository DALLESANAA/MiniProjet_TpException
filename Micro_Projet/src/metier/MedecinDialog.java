package metier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ImplMedecin;
import presentation.Medecin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MedecinDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField versionTextField;
	private JTextField titreTextField;
	private JTextField nomTextField;
	private JTextField prenomTextField;

	private ImplMedecin impMedecin;

	private MedecinGestion medecinGestion;
	
	private Medecin precedMedecin=null;
	private boolean modif=false;
	

	public MedecinDialog(MedecinGestion _medecinGestion,  ImplMedecin _impMedecin, Medecin _precedMedecin,boolean _modif) {
		this();
		impMedecin = _impMedecin;
		medecinGestion = _medecinGestion;
		precedMedecin=_precedMedecin;
		modif=_modif;
		
		if(modif) {
			setTitle("Modifier Medecin");
			TextFieldRemplis(precedMedecin);
		}
	}
	private void TextFieldRemplis(Medecin lemedecin) {

		versionTextField.setText(String.valueOf(lemedecin.getVersion()));
		titreTextField.setText(lemedecin.getTitre());
		nomTextField.setText(lemedecin.getNom());
		prenomTextField.setText(lemedecin.getPrenom().toString());

	}

	
	public MedecinDialog(MedecinGestion medecinGestion,
			 ImplMedecin lemedecintmp) {
		this(medecinGestion, lemedecintmp, null, false);
	}
	
	/**
	 * Create the dialog.
	 */
	public MedecinDialog() {
		setTitle("Ajouter Medecin");
		setBounds(320, 180, 630, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblVersion = new JLabel("Version :");
			lblVersion.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblVersion.setBounds(289, 91, 81, 14);
			contentPanel.add(lblVersion);
		}
		{
			versionTextField = new JTextField();
			versionTextField.setBounds(395, 85, 96, 20);
			contentPanel.add(versionTextField);
			versionTextField.setColumns(10);
		}
		{
			JLabel lblTitre = new JLabel("Titre :");
			lblTitre.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblTitre.setBounds(289, 128, 70, 14);
			contentPanel.add(lblTitre);
		}
		{
			titreTextField = new JTextField();
			titreTextField.setBounds(395, 122, 96, 20);
			contentPanel.add(titreTextField);
			titreTextField.setColumns(10);
		}
		{
			JLabel lblNom = new JLabel("Nom :");
			lblNom.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblNom.setBounds(292, 167, 48, 14);
			contentPanel.add(lblNom);
		}
		{
			nomTextField = new JTextField();
			nomTextField.setBounds(395, 165, 96, 20);
			contentPanel.add(nomTextField);
			nomTextField.setColumns(10);
		}
		{
			JLabel lblPrenom = new JLabel("Prenom :");
			lblPrenom.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblPrenom.setBounds(289, 212, 70, 14);
			contentPanel.add(lblPrenom);
		}
		{
			prenomTextField = new JTextField();
			prenomTextField .setBounds(395, 210, 96, 20);
			contentPanel.add(prenomTextField );
			prenomTextField .setColumns(10);
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(30, 144, 255));
			panel.setBounds(0, 0, 215, 288);
			contentPanel.add(panel);
		}
		{
			JLabel lblRemplirLesChamps = new JLabel("Remplir les champs");
			lblRemplirLesChamps.setForeground(new Color(30, 144, 255));
			lblRemplirLesChamps.setFont(new Font("Times New Roman", Font.BOLD, 25));
			lblRemplirLesChamps.setBounds(276, 11, 281, 35);
			contentPanel.add(lblRemplirLesChamps);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(30, 144, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Enregistrer");
				okButton.setBackground(new Color(175, 238, 238));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveMedecin();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBackground(new Color(245, 255, 250));
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
	
	
	protected void saveMedecin() {
				int version = Integer.parseInt(versionTextField.getText());
				String titre = titreTextField.getText();
				String nom = nomTextField.getText();
				String prenom= prenomTextField.getText();
	
				Medecin MedecinTmp=null;
				if(modif) {
					MedecinTmp=precedMedecin;
					
					MedecinTmp.setVersion(version);
					MedecinTmp.setTitre(titre);
					MedecinTmp.setNom(nom);
					MedecinTmp.setPrenom(prenom);
				}else {
					MedecinTmp=new Medecin(version,titre,nom,prenom);
				}
				try {
					//save to the database
					if(modif) {
						impMedecin.ModifierMedecin(MedecinTmp);
					}else {
						impMedecin.AjouterMedecin(MedecinTmp);
					}
				
				setVisible(false);
				dispose();

				medecinGestion.RefrechMedecin();

				JOptionPane.showMessageDialog(medecinGestion,
						"Medecin enregistré avec succés.", "Medecin enregistré",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(medecinGestion,
						"Erreur d'enregistrement du medecin: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
	}

}
