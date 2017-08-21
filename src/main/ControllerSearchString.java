package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchString implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchString(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	public void search(Update update) {
		view.sendTypingMessage(update);
		model.searchString(update);
	}	

}
