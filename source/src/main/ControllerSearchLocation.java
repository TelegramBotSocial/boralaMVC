package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchLocation implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchLocation(Model model, View view){
		this.model = model;
		this.view = view;
	}

	public void search(Update update) {
		view.sendTypingMessage(update);
		model.searchLocation(update, view);
		
	}
	

}
