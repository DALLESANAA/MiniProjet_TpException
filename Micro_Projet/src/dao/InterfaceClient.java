package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import presentation.Client;

public interface InterfaceClient {
	abstract void AjouterClient(Client leclient) throws Exception;
	abstract void ModifierClient(Client leclient) throws Exception;
	abstract void SupprimerClient(int clientId) throws SQLException;
	abstract List<Client> getAllClients() throws Exception ;
	abstract List<Client> ChercherClient(String nom) throws Exception;
	abstract Client LigneEnClient(ResultSet myRs) throws SQLException;
}
