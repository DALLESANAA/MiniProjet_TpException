package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import presentation.Client;
import presentation.Creneaux;
import presentation.Rv;

public class ImplRv implements InterfaceRv {

	public ImplRv()  throws Exception {			
		ConnectionDB.getConnectionInstance();
	}
	private ImplCreneaux implcreneau;
	private Creneaux leCreneau;
	
	public void AjouterRv(Rv leRv) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("insert into rv (jour,id_client,id_creneau) values (?,?,?)");
			myStmt.setDate(1,(Date) leRv.getJour());
			myStmt.setInt(2,leRv.getId_Client());
			myStmt.setInt(3,leRv.getId_Creneau());

			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void ModifierRv(Rv leRv) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("update rv set jour=? , id_client=? , id_creneau=?  where id=? ");
			
			myStmt.setDate(1,(Date) leRv.getJour());
			myStmt.setInt(2,leRv.getId_Client());
			myStmt.setInt(3,leRv.getId_Creneau());
			myStmt.setInt(4,leRv.getId());
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void SupprimerRv(int RvId) throws SQLException{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("delete from rv where id=?");
			
			myStmt.setInt(1, RvId);
			
			myStmt.executeUpdate();
		}
		finally {
			close(myStmt);
		}
	}
	
	public List<Rv> getAllRvs() throws Exception {
		List<Rv> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = ConnectionDB.getConnectionInstance().createStatement();
			myRs = myStmt.executeQuery("select * from rv");
			
			while (myRs.next()) {
				Rv RvTmp = LigneRv(myRs);
				list.add(RvTmp);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Rv> ChercherRv(int id_client) throws Exception {
		List<Rv> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			//nom += "%";
			myStmt = ConnectionDB.getConnectionInstance().prepareStatement("select * from rv where id_client =?");
			
			myStmt.setInt(1, id_client);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Rv RvTmp = LigneRv(myRs);
				list.add(RvTmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Rv LigneRv(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		Date jour= myRs.getDate("jour");
		int id_client = myRs.getInt("id_client");
		int id_creneau = myRs.getInt("id_creneau");
		
		Rv RvTmp = new Rv(id, jour,id_client,id_creneau);
		
		return RvTmp;
	}

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
	private void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}
	
	public static void main(String [] args) throws Exception {
	//Rv r=new Rv();
		ImplRv r=new ImplRv();
		System.out.println(r.getAllRvs());
	}
}



