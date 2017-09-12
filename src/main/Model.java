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
	
	public void notifyObservers(long chatId, String resp,String[] inlineBtn){
		for(Observer observer:observers){
			observer.update(chatId, resp, inlineBtn);
		}
	}
	
	public void searchBase(Update update,  View view){
		int status = view.getStatus();
		//tratamento de strings / mensagem de base
		if(update.message().text().equals("/start")){
			this.notifyObservers(update.message().chat().id(), "", "Seja Bem-vindo ao <strong>Borala</strong> bot :) Para utilizar nosso bot é necessário: enviar sua <b>LOCALIZAÇÃO</b>; posteriormente navegar em nossos menus de categorias para encontrar o lugar <b>IDEAL</b> mais próximo. E caso precise entrar em contato conosco, digite <code>'/suporte'</code> !");
			
		} else if(update.message().text().equals("/suporte")){
			this.notifyObservers(update.message().chat().id(), "", "Caso não tenhamos conseguido te <b>ajudar</b> ou tenha alguma <b>sugestão</b>, entre em contato com nosso <i>Suporte</i> na nossa central de atendimentos: www.boralabot.com.br/suporte ... <strong>Até mais</strong>");
			
		} else {
			if(status==0){
				this.notifyObservers(update.message().chat().id(), "", "Infelizmente não conseguimos entender. Navegue em nosso <b>menu</b>!");
			}
			view.notFound = true;
		}
		
		if(status==0){
			this.notifyObservers(update.message().chat().id(), "buttonLoc", "Envie sua Localização.");
		}else if(status==1){
			this.notifyObservers(update.message().chat().id(), "buttonCat", "Selecione uma categoria.");			
		}else if(status==2){
			this.notifyObservers(update.message().chat().id(), "buttonEsc", "Deseja enviar outra localização ou categoria?");
		}

	}
	
	public void searchLocation(Update update, View view){
		System.out.println("tratando a localização enviada");
		Location location = update.message().location();
				
		String lat = location.latitude().toString();
		String lon = location.longitude().toString();
		
		String locationString= lon+" "+lat;
		
		view.location = locationString;
		view.setStatus(1);
		
		this.notifyObservers(update.message().chat().id(), "buttonCat", "Selecione uma categoria.");
	}
	
	public void searchCategoria(Update update, View view){
		if(update.message().text().equals("baladas") || update.message().text().equals("restaurantes") ||
				update.message().text().equals("lanchonetes") || update.message().text().equals("pizzarias")){

			resultEnd(update, view);
			
		} else {
			this.notifyObservers(update.message().chat().id(), "buttonCat", "Selecione uma categoria.");
		}	
		
	}
	
	public void searchDecision(Update update, View view){
		if(update.message().text().equals("nova localização")){
			view.location="";
			view.setStatus(0);
			this.notifyObservers(update.message().chat().id(), "buttonLoc", "Envie sua Localização.");
			
		}else if(update.message().text().equals("nova categoria")){
			view.setStatus(1);			
			this.notifyObservers(update.message().chat().id(), "buttonCat", "Selecione uma categoria.");
			
		} else {
			this.notifyObservers(update.message().chat().id(), "buttonEsc", "Deseja enviar outra localização ou categoria?");
		}	
		
	}
	
	// monta a string de consulta dos resultados no banco 
	public void resultEnd(Update update, View view){
		
		Result resultado = new Result(view.location, update.message().text());
		if(resultado.getRes()!=null){
			
			//caso não encontre nenhum resultado
			if(resultado.getRes().size()==0){
				this.notifyObservers(update.message().chat().id(), "", "Não foi encontrado nenhum local nas especificações selecionadas, tente outras");
			
			//responde com a lista de resultado encontrado no banco
			}else{
		
				for(int i=0; i<resultado.getRes().size(); i++){
					if(resultado.getUrlClient().equals("")){
						this.notifyObservers(update.message().chat().id(), "", resultado.getRes().get(i));
					
					}else{
						String[] inlinesBtn = {resultado.getUrlClient(), resultado.getUrlGoogle()};
						this.notifyObservers(update.message().chat().id(),resultado.getRes().get(i), inlinesBtn);
					}
				}	
			}
		}
		
		this.notifyObservers(update.message().chat().id(), "buttonEsc", "Deseja enviar outra localização ou categoria?");
		view.setStatus(2);
	}
	
	
}