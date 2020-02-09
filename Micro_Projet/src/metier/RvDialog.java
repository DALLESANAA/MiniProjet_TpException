package metier;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import presentation.Rv;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

import dao.ConnectionDB;
import dao.ImplRv;
import java.awt.Font;

public class RvDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField jourTextField;
	
	private ImplRv implRv;

	private RvGestion RvGestion;
	
	private Rv precedRv=null;
	private boolean modif=false;
	private JFormattedTextField dtjour;
	private JComboBox<String> comboBoxclient;
	private JComboBox<String>  comboBoxcreneau ;
	

	public RvDialog(RvGestion _RvGestion,  ImplRv _implRv, Rv _precedRv,boolean _modif) {
		this();
		implRv = _implRv;
		RvGestion = _RvGestion;
		precedRv=_precedRv;
		modif=_modif;
		
		if(modif) {
			setTitle("Modifier Rendez-vous");
			//TextFieldRemplis(precedRv);
		}
	}
	/*private void TextFieldRemplis(Rv leRv) {

		dateChooser.setDate(leRv.getJour());

	}*/

	
	public RvDialog(RvGestion RvGestion, ImplRv leRvimp) {
		this(RvGestion, leRvimp, null, false);
	}
	/**
	 * Create the dialog.
	 */
	public RvDialog() {
		setBounds(320, 180, 630, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\eclipse_workspace_ISIL\\JDBC\\3\\Images\\rv1.png"));
			label.setBounds(499, 11, 105, 120);
			contentPanel.add(label);
		}
		{
			JLabel lblIdentifiantDuClient = new JLabel("Identifiant du client :");
			lblIdentifiantDuClient.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			lblIdentifiantDuClient.setBounds(406, 163, 126, 14);
			contentPanel.add(lblIdentifiantDuClient);
		}
		{
			JLabel lblIdentifiantDuMedecin = new JLabel("Identifiant du creneau :");
			lblIdentifiantDuMedecin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblIdentifiantDuMedecin.setBounds(191, 162, 149, 14);
			contentPanel.add(lblIdentifiantDuMedecin);
		}
		{
			comboBoxclient = new JComboBox<String>();
			comboBoxclient.setBounds(448, 194, 126, 22);
			contentPanel.add(comboBoxclient);
			rempliircombobox("client",comboBoxclient);
		}
		{
			 comboBoxcreneau = new JComboBox<String>();
			comboBoxcreneau .setBounds(239, 195, 126, 22);
			contentPanel.add(comboBoxcreneau );
			rempliircombobox("creneaux",comboBoxcreneau);
		}
		{
			JLabel lblJour = new JLabel("Jour :");
			lblJour.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			lblJour.setBounds(269, 99, 48, 14);
			contentPanel.add(lblJour);
		}
		
		try {
			MaskFormatter mask=new MaskFormatter("####-##-##");
			mask.setPlaceholderCharacter('_');
			dtjour=new JFormattedTextField(mask);
			dtjour.setBounds(303,96,143,22);
			contentPanel.add(dtjour);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(100, 149, 237));
			panel.setBounds(0, 0, 167, 288);
			contentPanel.add(panel);
		}
		{
			JLabel lblRemplirLesChamps = new JLabel("Remplir les champs");
			lblRemplirLesChamps.setForeground(new Color(100, 149, 237));
			lblRemplirLesChamps.setFont(new Font("Times New Roman", Font.BOLD, 26));
			lblRemplirLesChamps.setBounds(227, 23, 276, 31);
			contentPanel.add(lblRemplirLesChamps);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(100, 149, 237));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Enregistrer");
				okButton.setForeground(new Color(0, 0, 128));
				okButton.setBackground(new Color(175, 238, 238));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveRv();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setBackground(new Color(245, 255, 250));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
		/*int version = Integer.parseInt(versionTextField.getText());
		int hdebut = Integer.parseInt(hdebutTextField.getText());
		int mdebut = Integer.parseInt(mdebutTextField.getText());
		int hfin = Integer.parseInt(hfintextField.getText());
		int mfin = Integer.parseInt(mfinTextField.getText());*/
		
		
		protected void saveRv() {
			
			java.util.Date dat=null;
			
			try {
				dat=new SimpleDateFormat("yyyy-MM-dd").parse(dtjour.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			java.sql.Date sqldate=new java.sql.Date(dat.getTime());
			int id_client=Integer.parseInt((String)comboBoxclient.getSelectedItem());
			int id_creneau=Integer.parseInt((String)comboBoxcreneau.getSelectedItem());
		
			Rv RvTmp=null;
			if(modif) {
				RvTmp=precedRv;
				
				RvTmp.setJour(sqldate);

			}else {
				RvTmp=new Rv(sqldate,id_client,id_creneau);
			}
			try {
				//save to the database
				if(modif) {
					implRv.ModifierRv(RvTmp);
				}else {
					implRv.AjouterRv(RvTmp);
				}
			
			setVisible(false);
			dispose();

			RvGestion.RefrechRv();

			JOptionPane.showMessageDialog(RvGestion,
					"Rendez vous enregistré avec succés.", "Rendez-vous enregistré",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(RvGestion,
					"Erreur d'enregistrement du Rendez-vous: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
}
		public void rempliircombobox(String table,JComboBox<String> combobox) {
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

