package metier;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import presentation.Creneaux;

public class CreneauxTableModel extends AbstractTableModel{

	public static final int OBJECT_COL = -1;
	private static final int VERSION_COL = 0;
	private static final int HDEBUT_COL = 1;
	private static final int MDEBUT_COL = 2;
	private static final int HFIN_COL = 3;
	private static final int MFIN_COL = 4;
	private static final int IDMEDECIN_COL =5;
	
	private String[] columnNames = { "Version", "Hdebut", "Mdebut","Hfin","Mfin" ,"id_Medecin"};
	private List<Creneaux> creneaux;

	public CreneauxTableModel (List<Creneaux> lescreneaux) {
		creneaux = lescreneaux;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return creneaux.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override

	public Object getValueAt(int row, int col) {

		Creneaux CreneauTmp = creneaux.get(row);

		switch (col) {
		case VERSION_COL:
			return CreneauTmp.getVersion();
		case HDEBUT_COL:
			return CreneauTmp.getHdebut();
		case MDEBUT_COL:
			return CreneauTmp.getMdebut();
		case HFIN_COL:
			return CreneauTmp.getHfin();
		case MFIN_COL:
			return CreneauTmp.getMfin();
		case IDMEDECIN_COL:
			return CreneauTmp.getId_medecin();

		default:
			return CreneauTmp;
		}
	}

}
