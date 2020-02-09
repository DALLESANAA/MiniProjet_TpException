package metier;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import presentation.Medecin;

public class MedecinTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int VERSION_COL = 0;
	private static final int TITRE_COL = 1;
	private static final int NOM_COL = 2;
	private static final int PRENOM_COL = 3;

	
	private String[] columnNames = { "Version", "Titre", "Nom","Prenom" };
	private List<Medecin> medecins;

	public MedecinTableModel (List<Medecin> lesmedecins) {
		medecins = lesmedecins;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return medecins.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override

	public Object getValueAt(int row, int col) {

		Medecin MedicinTmp = medecins.get(row);

		switch (col) {
		case VERSION_COL:
			return MedicinTmp.getVersion();
		case TITRE_COL:
			return MedicinTmp.getTitre();
		case NOM_COL:
			return MedicinTmp.getNom();
		case PRENOM_COL:
			return MedicinTmp.getPrenom();
		default:
			return MedicinTmp;
		}
	}

}

