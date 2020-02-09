package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionDB {
	
	String url="jdbc:mysql://localhost:3306/cabinet";
	String user="root";
	String password="";
	
	private static Connection connect;

	private ConnectionDB(){
	  try {
	    connect =  (Connection) DriverManager.getConnection(url, user, password);
	  } catch (SQLException e) {
	    e.printStackTrace();
	  }
	}
 
	 public static Connection getConnectionInstance(){
	  if(connect == null){
	    new ConnectionDB();
	    System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
	  }else {
		  System.out.println("CONNEXION SQL EXISTANTE ! ");
	  }
	  return connect;  
	}   
}
