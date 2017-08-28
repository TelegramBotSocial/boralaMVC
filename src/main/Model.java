package main;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
	
	private static Model uniqueInstance;
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId, String resp, String typeResult){
		for(Observer observer:observers){
			observer.update(chatId, resp, typeResult);
		}
	}
	
	public void searchBase(Update update, int status){
		//tratamento de strings
		if(update.message().text().equals("/start")){
			this.notifyObservers(update.message().chat().id(), "", "Seja Bem-vindo ao <strong>Borala</strong> bot :) Para utilizar nosso bot � necess�rio: enviar sua <b>LOCALIZA��O</b>; posteriormente navegar em nossos menus de categorias para encontrar o lugar <b>IDEAL</b> mais pr�ximo. E caso precise entrar em contato conosco, digite <code>'/suporte'</code> !");
			
		} else if(update.message().text().equals("/suporte")){
			this.notifyObservers(update.message().chat().id(), "", "Caso n�o tenhamos conseguido te <b>ajudar</b> ou tenha alguma <b>sugest�o</b>, entre em contato com nosso <i>Suporte</i> na nossa central de atendimentos: www.boralabot.com.br/suporte ... <strong>At� mais</strong>");
			
		} else {
			this.notifyObservers(update.message().chat().id(), "", "Infelizmente n�o conseguimos entender. Navegue em nosso <b>menu</b>!");
			
		}
		
		if(status==0){
			this.notifyObservers(update.message().chat().id(), "buttonLoc", "Envie sua Localiza��o.");
		}else if(status==1){
			this.notifyObservers(update.message().chat().id(), "buttonCat", "Selecione uma categoria.");			
		}else if(status==2){
			this.notifyObservers(update.message().chat().id(), "buttonEsc", "Deseja enviar outra localiza��o ou categoria?");
		}
	}
	
	public void searchLocation(Update update, View view){
		System.out.println("tratando a localiza��o enviada");
		Location location = update.message().location();
				
		String lat = location.latitude().toString();
		String lon = location.longitude().toString();
		
		String locationString= lon+" "+lat;
		
		view.location = locationString;
		
		view.setStatus(1);
	}
	
	public void searchResult(Update update, View view){
		//pegar o resultado no banco de dados
	}
	
	
}