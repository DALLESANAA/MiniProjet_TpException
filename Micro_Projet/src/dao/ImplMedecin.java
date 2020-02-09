
package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionDB;
import presentation.Medecin;

public class ImplMedecin implements InterfaceMedecin {
	
	public ImplMedecin()  throws Exception {			
		ConnectionDB.getConnectionInstance();
	}
	
	public Medecin getMedecin1(int idm) {
		Medecin med=null;
		
		String req="SELECT * from medecins where id=?";
		try {
			PreparedStatement ps=ConnectionDB.getConnectionInstance().prepareStatement(req);
			ps.setInt(1, idm);
			ResultSet res=ps.executeQuery();
			if(res.next()) {
				med=new Medecin(idm,res.getInt("version"),res.getString("titre"),res.getString("nom"),res.getString("prenom"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return med;	
	}
	
	
	public void AjouterMedecin(Medecin lemedecin) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("insert into medecins (version,titre,nom,prenom) values (?,?,?,?)");
			
			myStmt.setInt(1,lemedecin.getVersion());
			myStmt.setString(2,lemedecin.getTitre());
			myStmt.setString(3,lemedecin.getNom());
			myStmt.setString(4,lemedecin.getPrenom());

			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	
	public void ModifierMedecin(Medecin lemedecin) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("update medecins set version=? , titre=? , nom=? ,prenom=?  where id=? ");
			
			myStmt.setInt(1,lemedecin.getVersion());
			myStmt.setString(2,lemedecin.getTitre());
			myStmt.setString(3,lemedecin.getNom());
			myStmt.setString(4,lemedecin.getPrenom());
			myStmt.setInt(5,lemedecin.getId());
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void SupprimerMedecin(int medecinId) throws SQLException{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("delete from medecins where id=?");
			
			myStmt.setInt(1, medecinId);
			
			myStmt.executeUpdate();
		}
		finally {
			close(myStmt);
		}
	}
	
	public List<Medecin> getAllMedecins() throws Exception {
		List<Medecin> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = ConnectionDB.getConnectionInstance().createStatement();
			myRs = myStmt.executeQuery("select * from medecins");
			
			while (myRs.next()) {
				Medecin MedecinTmp = LigneEnMedecin(myRs);
				list.add(MedecinTmp);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Medecin getMedecin(int id) throws Exception {
		List<Medecin> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = ConnectionDB.getConnectionInstance().createStatement();
			myRs = myStmt.executeQuery("select * from medecins");
			
			while (myRs.next()) {
				Medecin MedecinTmp = LigneEnMedecin(myRs);
				list.add(MedecinTmp);
			}

			return list.get(id);		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public List<Medecin> ChercherMedecin(String nom) throws Exception {
		List<Medecin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nom += "%";
			myStmt = ConnectionDB.getConnectionInstance().prepareStatement("select * from medecins where nom like ?");
			
			myStmt.setString(1, nom);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Medecin MedecinTmp = LigneEnMedecin(myRs);
				list.add(MedecinTmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Medecin LigneEnMedecin(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		int version= myRs.getInt("version");
		String titre = myRs.getString("titre");
		String nom = myRs.getString("nom");
		String prenom = myRs.getString("prenom");
		
		Medecin MedecinTmp = new Medecin(id, version, titre, nom, prenom);
		
		return MedecinTmp;
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

