package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import presentation.Medecin;

public interface InterfaceMedecin {
	abstract void AjouterMedecin(Medecin lemedecin) throws Exception;
	abstract void ModifierMedecin(Medecin lemedecin) throws Exception;
	abstract void SupprimerMedecin(int medecinId) throws SQLException;
	abstract List<Medecin> getAllMedecins() throws Exception ;
	abstract List<Medecin> ChercherMedecin(String nom) throws Exception;
	abstract Medecin LigneEnMedecin(ResultSet myRs) throws SQLException;
}
