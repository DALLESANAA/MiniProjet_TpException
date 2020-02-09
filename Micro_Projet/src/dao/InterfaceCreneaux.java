package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import presentation.Creneaux;

public interface InterfaceCreneaux {
	abstract void AjouterCreneau(Creneaux leCreneau) throws Exception;
	abstract void ModifierCreneau(Creneaux leCreneau) throws Exception;
	abstract void SupprimerCreneau(int CreneauId) throws SQLException;
	abstract List<Creneaux> getAllCreneaux() throws Exception ;
	abstract List<Creneaux> ChercherCreneau(int version) throws Exception;
	abstract Creneaux LigneCreneau(ResultSet myRs) throws SQLException, Exception;
}
