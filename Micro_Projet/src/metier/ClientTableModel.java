package metier;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import presentation.Client;

class ClientTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int VERSION_COL = 0;
	private static final int TITRE_COL = 1;
	private static final int NOM_COL = 2;
	private static final int PRENOM_COL = 3;

	
	private String[] columnNames = { "Version", "Titre", "Nom","Prenom" };
	private List<Client> clients;

	public ClientTableModel (List<Client> lesclients) {
		clients = lesclients;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return clients.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override

	public Object getValueAt(int row, int col) {

		Client ClientTmp = clients.get(row);

		switch (col) {
		case VERSION_COL:
			return ClientTmp.getVersion();
		case TITRE_COL:
			return ClientTmp.getTitre();
		case NOM_COL:
			return ClientTmp.getNom();
		case PRENOM_COL:
			return ClientTmp.getPrenom();
		default:
			return ClientTmp;
		}
	}

}

