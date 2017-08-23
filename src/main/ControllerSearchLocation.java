package main;

import com.pengrad.telegrambot.model.Location;
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
		this.setLocationView(update);
		
	}
	
	public void setLocationView(Update update){
		//tratamento de localização
		System.out.println("tratando a localização enviada");
		Location location = update.message().location();
				
		String lat = location.latitude().toString();
		String lon = location.longitude().toString();
		
		String locationString= lon+" "+lat;
		
		this.view.location = locationString;
		model.searchLocation(update, view);
		
	}

}
