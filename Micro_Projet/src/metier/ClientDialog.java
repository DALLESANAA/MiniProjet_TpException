package metier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ImpClient;
import presentation.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class ClientDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField versionTextField;
	private JTextField titreTextField;
	private JTextField nomTextField;
	private JTextField prenomTextField;

	private ImpClient impClient;

	private ClientGestion clientGestion;
	
	private Client precedClient=null;
	private boolean modif=false;
	

	public ClientDialog(ClientGestion _clientGestion,  ImpClient _impClient, Client _precedClient,boolean _modif) {
		this();
		impClient = _impClient;
		clientGestion = _clientGestion;
		precedClient=_precedClient;
		modif=_modif;
		
		if(modif) {
			setTitle("Modifier Client");
			TextFieldRemplis(precedClient);
		}
	}
	private void TextFieldRemplis(Client leclient) {

		versionTextField.setText(String.valueOf(leclient.getVersion()));
		titreTextField.setText(leclient.getTitre());
		nomTextField.setText(leclient.getNom());
		prenomTextField.setText(leclient.getPrenom().toString());

	}

	
	public ClientDialog(ClientGestion clientGestion,
			 ImpClient leclientimp) {
		this(clientGestion, leclientimp, null, false);
	}
	
	/**
	 * Create the dialog.
	 */
	public ClientDialog() {
		setTitle("Ajouter Client");
		setBounds(320, 180, 630, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblVersion = new JLabel("Version :");
			lblVersion.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblVersion.setBounds(260, 76, 102, 14);
			contentPanel.add(lblVersion);
		}
		{
			versionTextField = new JTextField();
			versionTextField.setBounds(372, 74, 96, 20);
			contentPanel.add(versionTextField);
			versionTextField.setColumns(20);
		}
		{
			JLabel lblTitre = new JLabel("Titre :");
			lblTitre.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblTitre.setBounds(260, 112, 84, 14);
			contentPanel.add(lblTitre);
		}
		{
			titreTextField = new JTextField();
			titreTextField.setBounds(372, 110, 96, 20);
			contentPanel.add(titreTextField);
			titreTextField.setColumns(20);
		}
		{
			JLabel lblNom = new JLabel("Nom :");
			lblNom.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblNom.setBounds(260, 150, 48, 14);
			contentPanel.add(lblNom);
		}
		{
			nomTextField = new JTextField();
			nomTextField.setBounds(372, 148, 96, 20);
			contentPanel.add(nomTextField);
			nomTextField.setColumns(20);
		}
		{
			JLabel lblPrenom = new JLabel("Prenom :");
			lblPrenom.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblPrenom.setBounds(260, 192, 70, 14);
			contentPanel.add(lblPrenom);
		}
		{
			prenomTextField = new JTextField();
			prenomTextField .setBounds(372, 190, 96, 20);
			contentPanel.add(prenomTextField );
			prenomTextField .setColumns(20);
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(30, 144, 255));
			panel.setBounds(0, 0, 211, 323);
			contentPanel.add(panel);
		}
		
		JLabel lblAjouterClient = new JLabel("Remplir les champs");
		lblAjouterClient.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblAjouterClient.setForeground(new Color(30, 144, 255));
		lblAjouterClient.setBounds(273, 11, 250, 26);
		contentPanel.add(lblAjouterClient);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(30, 144, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Enregistrer");
				okButton.setForeground(new Color(0, 0, 128));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
				okButton.setBackground(new Color(175, 238, 238));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveClient();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setForeground(new Color(0, 0, 128));
				cancelButton.setBackground(new Color(240, 248, 255));
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
	
	
	protected void saveClient() {
				int version = Integer.parseInt(versionTextField.getText());
				String titre = titreTextField.getText();
				String nom = nomTextField.getText();
				String prenom= prenomTextField.getText();
	
				Client ClientTmp=null;
				if(modif) {
					ClientTmp=precedClient;
					
					ClientTmp.setVersion(version);
					ClientTmp.setTitre(titre);
					ClientTmp.setNom(nom);
					ClientTmp.setPrenom(prenom);
				}else {
					ClientTmp=new Client(version,titre,nom,prenom);
				}
				try {
					//save to the database
					if(modif) {
						impClient.ModifierClient(ClientTmp);
					}else {
						impClient.AjouterClient(ClientTmp);
					}
				
				setVisible(false);
				dispose();

				clientGestion.RefrechClient();

				JOptionPane.showMessageDialog(clientGestion,
						"Client enregistré avec succés.", "Client enregistré",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(clientGestion,
						"Erreur d'enregistrement du client: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
	}
}
