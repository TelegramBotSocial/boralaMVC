package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchBase implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchBase(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	public void search(Update update) {
		view.sendTypingMessage(update);
		model.searchBase(update, view);
	}	

}
