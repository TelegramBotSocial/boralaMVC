package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Result {

	public String location;
	public String categoria;
	
	private String urlClient;
	private String urlGoogle;
	private ArrayList<String> res;
	
	public Result(String loc, String cat){
		this.location = loc;
		this.categoria = cat;
		
		setRes(searchDB());
	}
	
	public ArrayList<String> searchDB(){
		if(this.location!=null){
			System.out.println("INCIANDO A BUSCA");
			ArrayList<String> result = new ArrayList<String>();

			LocaisDAO crud = new LocaisDAO();
			ResultSet res = crud.read("WHERE st_dwithin(geom, ST_GeomFromText('POINT("+this.location+")',4326), 0.15) AND"
					+ " upper(t_first) = upper('"+this.categoria+"') OR upper(t_second) = upper('"+this.categoria+"') OR upper(t_three) = upper('"+this.categoria+"')");

			String msgFinal = "";
			try{
				while(res.next()){
					/* ORGANIZAR RESPOSTA AO USUÁRIO
					* Nome do local: 
					* info:
					* Promoção da semana: 
					* btn com o GPS:
					* btn da url do local: 
					* */
					msgFinal = "Local: <strong>"+res.getString("name")+"</strong>. ";
					msgFinal += res.getString("info")+"! ";
					msgFinal += "Localizado em: "+res.getString("city");
					if(!res.getString("promo").equals("")){
						msgFinal += " <strong>PROMOÇÃO:</strong> "+res.getString("promo");
					}
					
					setUrlGoogle(res.getString("geometria"));						
					setUrlClient(res.getString("url"));		
					result.add(msgFinal);
				}
			}catch(SQLException ex){
				System.out.println("ERRO: "+ex.getMessage());
			}

			return result;
		}
		return null;
	}
	
	public ArrayList<String> getRes() {
		return res;
	}

	public void setRes(ArrayList<String> arrayList) {
		this.res = arrayList;
	}
	
	public String getUrlClient() {
		return urlClient;
	}

	public void setUrlClient(String urlClient) {
		this.urlClient = urlClient;
	}
	
	private void setUrlGoogle(String googleMaps) {
		String res = googleMaps.substring(googleMaps.indexOf("(")+1, googleMaps.indexOf(")"));
		String laglon[] = res.split(" ");
		
		this.urlGoogle = "https://www.google.com.br/maps/@"+laglon[1]+","+laglon[0]+",16z";
	}

	public String getUrlGoogle() {
		return this.urlGoogle;
	}

}
