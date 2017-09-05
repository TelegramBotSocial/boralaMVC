package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocaisDAO {

	public ResultSet read(String condicao){
		String sql = "SELECT *, ST_astext(geom) as geometria FROM tb_corporation ";
		sql+=condicao;
		PreparedStatement pst = ConnectionsDB.getPreparedStatement(sql);

		ResultSet resultado = null;
		try{
			ResultSet res = pst.executeQuery();
			resultado = res;
		}catch(SQLException ex){
			System.out.println("Erro no SQL: ");
			ex.getMessage();
		}

		return resultado;	
	}
}
