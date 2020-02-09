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
import presentation.Client;

public class ImpClient implements InterfaceClient {

	
	public ImpClient()  throws Exception {			
		ConnectionDB.getConnectionInstance();
	}
	
	public void AjouterClient(Client leclient) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("insert into client (version,titre,nom,prenom) values (?,?,?,?)");
			
			myStmt.setInt(1,leclient.getVersion());
			myStmt.setString(2,leclient.getTitre());
			myStmt.setString(3,leclient.getNom());
			myStmt.setString(4,leclient.getPrenom());

			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void ModifierClient(Client leclient) throws Exception{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("update client set version=? , titre=? , nom=? ,prenom=?  where id=? ");
			
			myStmt.setInt(1,leclient.getVersion());
			myStmt.setString(2,leclient.getTitre());
			myStmt.setString(3,leclient.getNom());
			myStmt.setString(4,leclient.getPrenom());
			myStmt.setInt(5,leclient.getId());
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt);
		}
	}
	
	public void SupprimerClient(int clientId) throws SQLException{
		PreparedStatement myStmt=null;
		try {
			myStmt=ConnectionDB.getConnectionInstance().prepareStatement("delete from client where id=?");
			
			myStmt.setInt(1, clientId);
			
			myStmt.executeUpdate();
		}
		finally {
			close(myStmt);
		}
	}
	
	public List<Client> getAllClients() throws Exception {
		List<Client> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = ConnectionDB.getConnectionInstance().createStatement();
			myRs = myStmt.executeQuery("select * from client");
			
			while (myRs.next()) {
				Client clientTmp = LigneEnClient(myRs);
				list.add(clientTmp);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Client> ChercherClient(String nom) throws Exception {
		List<Client> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			nom += "%";
			myStmt = ConnectionDB.getConnectionInstance().prepareStatement("select * from client where nom like ?");
			
			myStmt.setString(1, nom);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Client clientTmp = LigneEnClient(myRs);
				list.add(clientTmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Client LigneEnClient(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		int version= myRs.getInt("version");
		String titre = myRs.getString("titre");
		String nom = myRs.getString("nom");
		String prenom = myRs.getString("prenom");
		
		Client ClientTmp = new Client(id, version, titre, nom, prenom);
		
		return ClientTmp;
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
