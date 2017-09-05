package main;

import java.sql.*;

public class ConnectionsDB {

	public static Connection getConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_borala1", "postgres", "postgres");
			return con;
		}catch(ClassNotFoundException ex){
			System.out.println("ERRO ENCONTRADO NA CONEX�O (servidor): ");
			ex.printStackTrace();
			return null;
		}catch(SQLException ex){
			System.out.println("ERRO ENCONTRADO NO SQL: ");
			ex.printStackTrace();
			return null;
		}
	}
	
	public static PreparedStatement getPreparedStatement(String sql){
		Connection con = getConnection();
		if(con!=null){
			try{
				return con.prepareStatement(sql);		
			}catch (SQLException e){
				System.out.println("ERRO NO SQL: ");
				e.getMessage();
			}
		}
		return null;
	}

}