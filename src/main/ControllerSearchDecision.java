package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchDecision implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchDecision(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	public void search(Update update) {
		view.sendTypingMessage(update);
		model.searchDecision(update, view);
	}	

}
