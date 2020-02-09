package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import presentation.Rv;

public interface InterfaceRv {
	abstract void AjouterRv(Rv leRv) throws Exception;
	abstract void ModifierRv(Rv leRv) throws Exception;
	abstract void SupprimerRv(int RvId) throws SQLException;
	abstract List<Rv> getAllRvs() throws Exception ;
	abstract List<Rv> ChercherRv(int id_client) throws Exception;
	abstract Rv LigneRv(ResultSet myRs) throws SQLException;
}
