package presentation;

import java.util.Date;

public class Rv {
	private int id;
	private Date jour;
	private int id_client;
	private int id_creneau;
	
	public Rv(Date jour, int id_client,int id_creneau) {

		this(0,jour,id_client,id_creneau);
	}

	public Rv(int id, Date jour, int id_client,int id_creneau) {
		super();
		this.id = id;
		this.jour = jour;
		this.id_client =id_client;
		this.id_creneau = id_creneau;
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id=id;
	}
	
	public Date getJour() {
		return this.jour;
	}
	public void setJour(Date jour) {
		this.jour=jour;
	}
	
	public int getId_Client() {
		return this.id_client;
	}
	public void setId_Client(int id_client) {
		this.id_client=id_client;
	}
	
	public int getId_Creneau() {
		return this.id_creneau;
	}
	public void setId_Creneau(int id_creneau) {
		this.id_creneau=id_creneau;
	}
	public String toString()  {
		return String
				.format("Rendez vous [id=%s, Jour=%s, Id_Client=%s, Id_Creneau=%s]",
						id, jour, id_client,id_creneau);
	}
	
	
}

