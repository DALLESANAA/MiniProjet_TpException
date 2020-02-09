package metier;

import java.util.List;

import javax.swing.event.TableModelListener;

import presentation.Rv;

public class RvTableModel extends AbstractTableModel{
	public static final int OBJECT_COL = -1;
	private static final int JOUR_COL = 0;
	private static final int IDCLIENT_COL = 1;
	private static final int IDCRENEAU_COL = 2;

	
	private String[] columnNames = { "Jour", "Id Client", "Id Creneau" };
	private List<Rv> Rvs;

	public  RvTableModel (List<Rv> lesrv) {
		Rvs = lesrv;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return Rvs.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override

	public Object getValueAt(int row, int col) {

		Rv RvsTmp = Rvs.get(row);

		switch (col) {
		case JOUR_COL:
			return RvsTmp.getJour();
		case IDCLIENT_COL:
			return RvsTmp.getId_Client();
		case  IDCRENEAU_COL:
			return RvsTmp.getId_Creneau();
		default:
			return RvsTmp;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class getColumnClass(int c) {
		// TODO Auto-generated method stub
		return null;
	}

}
