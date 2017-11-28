package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchCategoria implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchCategoria(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	public void search(Update update) {
		view.sendTypingMessage(update);
		model.searchCategoria(update, view);
	}	

}
