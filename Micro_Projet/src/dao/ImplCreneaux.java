package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import presentation.Creneaux;
import presentation.Medecin;

 public class ImplCreneaux implements InterfaceCreneaux{

	public ImplCreneaux()  throws Exception {			
		ConnectionDB.getConnectionInstance();
	}
	
	public void AjouterCreneau(Creneaux leCreneau) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("insert into 	creneaux (version,hdebut,mdebut,hfin,mfin,id_medecin) values (?,?,?,?,?,?)");
			
			myStmt.setInt(1,leCreneau.getVersion());
			myStmt.setInt(2,leCreneau.getHdebut());
			myStmt.setInt(3,leCreneau.getMdebut());
			myStmt.setInt(4,leCreneau.getHfin());
			myStmt.setInt(5,leCreneau.getMfin());
			myStmt.setInt(6,leCreneau.getId_medecin());

			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void ModifierCreneau(Creneaux leCreneau) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("update 	creneaux set version=? , hdebut=? , mdebut=? ,hfin=? ,mfin=?,id_medecin=?  where id=? ");
			
			myStmt.setInt(1,leCreneau.getVersion());
			myStmt.setInt(2,leCreneau.getHdebut());
			myStmt.setInt(3,leCreneau.getMdebut());
			myStmt.setInt(4,leCreneau.getHfin());
			myStmt.setInt(5,leCreneau.getMfin());
			myStmt.setInt(5,leCreneau.getId_medecin());
			myStmt.setInt(6,leCreneau.getId());
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void SupprimerCreneau(int CreneauId) throws SQLException{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("delete from 	creneaux where id=?");
			
			myStmt.setInt(1, CreneauId);
			
			myStmt.executeUpdate();
		}
		finally {
			close(myStmt);
		}
	}
	
	public List<Creneaux> getAllCreneaux() throws Exception {
		List<Creneaux> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = ConnectionDB.getConnectionInstance().createStatement();
			myRs = myStmt.executeQuery("select * from creneaux");
			
			while (myRs.next()) {
				Creneaux CreneauTmp = LigneCreneau(myRs);
				list.add(CreneauTmp);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Creneaux> ChercherCreneau(int version) throws Exception {
		List<Creneaux> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = ConnectionDB.getConnectionInstance().prepareStatement("select * from creneaux where version=?");
			
			myStmt.setInt(1, version);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Creneaux CreneauTmp =LigneCreneau(myRs);
				list.add(CreneauTmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Creneaux LigneCreneau(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		int version= myRs.getInt("version");
		int hdebut = myRs.getInt("hdebut");
		int mdebut= myRs.getInt("mdebut");
		int hfin = myRs.getInt("hfin");
		int mfin= myRs.getInt("mfin");
		int id_medecin = myRs.getInt("id_medecin");
		
		Creneaux CreneauTmp = new Creneaux(id, version, hdebut, mdebut, hfin,mfin,id_medecin);
		
		return CreneauTmp;
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
}

