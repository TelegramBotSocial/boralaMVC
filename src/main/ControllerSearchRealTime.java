package main;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchRealTime implements ControllerSearch{
	
	private Model model;
	private View view;

	public ControllerSearchRealTime(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	public void search(Update update) {
		view.sendTypingMessage(update);
		model.realTime(update, view);
	}	

}
