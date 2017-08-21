package main;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer{

	TelegramBot bot = TelegramBotAdapter.build("352316358:AAEPgQbbp0cMjlrTlNhbf6fYj1JhgqKRXu0");

	GetUpdatesResponse updatesResponse;
	SendResponse sendResponse;
	BaseResponse baseResponse;
			
	int indexMsg=0;
	
	ControllerSearch controllerSearch; 
	private Model model;
	
	boolean searchBehaviour = false;
	
	public View(Model model){
		this.model = model; 
	}
	
	public void setControllerSearch(ControllerSearch controllerSearch){ //Strategy Pattern
		this.controllerSearch = controllerSearch;
	}
	
	public void receiveUsersMessages() {

		while (true){
	
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(indexMsg));
			
			List<Update> updates = updatesResponse.updates();

			for (Update update : updates) {
				
				indexMsg = update.updateId()+1;
				
				String resultText = update.message().text();
				if(resultText!=null){
					setControllerSearch(new ControllerSearchString(model, this));
	
				}else{
					setControllerSearch(new ControllerSearchLocation(model, this));
				
				}				
				
			}

		}
		
	}
	
	public void callController(Update update){
		//this.controllerSearch.search(update);
	}
	
	public void update(long chatId){
		//sendResponse = bot.execute(new SendMessage(chatId));
		this.searchBehaviour = false;
	}
	
	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}
	

}
