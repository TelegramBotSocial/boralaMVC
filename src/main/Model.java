package main;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

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
	
	public void notifyObservers(long chatId){
		for(Observer observer:observers){
			observer.update(chatId);
		}
	}
	
	public String searchString(Update update){
		if(update.message().text().equals("/start")){
			bot.execute(new SendMessage(update.message().chat().id(), "Seja Bem-vindo ao <strong>Borala</strong> bot :) Para utilizar nosso bot é necessário: enviar sua <b>LOCALIZAÇÃO</b>; posteriormente navegar em nossos menus de categorias para encontrar o lugar <b>IDEAL</b> mais próximo. E caso precise entrar em contato conosco, digite <code>'/suporte'</code> !").parseMode(ParseMode.HTML));
			
		} else if(update.message().text().equals("/suporte")){
			bot.execute(new SendMessage(update.message().chat().id(), "Caso não tenhamos conseguido te <b>ajudar</b> ou tenha alguma <b>sugestão</b>, entre em contato com nosso <i>Suporte</i> na nossa central de atendimentos: www.boralabot.com.br/suporte ... <strong>Até mais</strong>").parseMode(ParseMode.HTML));
			
		} else {
			bot.execute(new SendMessage(update.message().chat().id(), "Infelizmente não conseguimos entender. Navegue em nosso <b>menu</b>!").parseMode(ParseMode.HTML));
			
		}
		Keyboard keyboard = new ReplyKeyboardMarkup(
				new KeyboardButton[]{
						new KeyboardButton("proximidade"),
						new KeyboardButton("eventos")
				});
		bot.execute(new SendMessage(update.message().chat().id(),"Escolha um tipo de busca:").replyMarkup(keyboard));
		this.searchBehaviour=true;
	}
	
}