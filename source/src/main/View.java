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
	
	private int status = 0;
	public boolean notFound = false;
	public String location = "";
			
	int indexMsg=0;
	
	ControllerSearch controllerSearch; 
	private Model model;
	
	public View(Model model){
		this.model = model; 
	}
	
	public void setControllerSearch(ControllerSearch controllerSearch){
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
						
					setControllerSearch(new ControllerSearchBase(model, this));
					if(notFound){						
						if(status==1){
							setControllerSearch(new ControllerSearchCategoria(model, this));
						}else if(status==2){
							setControllerSearch(new ControllerSearchDecision(model, this));
						}
						//notFound=false;
					}
					this.callController(update);
	
				}else{
					setControllerSearch(new ControllerSearchLocation(model, this));
					this.callController(update);
				}				
				
			}

		}
		
	}
	
	public void callController(Update update){
		this.controllerSearch.search(update);
	}
	
	public void update(long chatId, String typeResult, String resp){
		switch(typeResult){
			case "buttonLoc":
				Keyboard keyboardLoc = new ReplyKeyboardMarkup(
						new KeyboardButton[]{
								new KeyboardButton("Minha Localização").requestLocation(true)
						});  
				bot.execute(new SendMessage(chatId, resp).replyMarkup(keyboardLoc));
				break;
			
			case "buttonCat":
				Keyboard keyboardCat = new ReplyKeyboardMarkup(
					new KeyboardButton[]{
							new KeyboardButton("baladas"),
							new KeyboardButton("restaurantes")
					},
					new KeyboardButton[]{
							new KeyboardButton("lanchonetes"),
							new KeyboardButton("pizzarias")
					});  
				bot.execute(new SendMessage(chatId, resp).replyMarkup(keyboardCat));
				break;
				
			case "buttonEsc":
				Keyboard keyboardEsc = new ReplyKeyboardMarkup(
						new KeyboardButton[]{
								new KeyboardButton("nova localização")
						},new KeyboardButton[]{
								new KeyboardButton("nova categoria")
						}
						);  
				bot.execute(new SendMessage(chatId, resp).replyMarkup(keyboardEsc));
				break;
			
			default: 
				sendResponse = bot.execute(new SendMessage(chatId, resp).parseMode(ParseMode.HTML));
				
				break;
		}	
	}
	
	public void update(long chatId, String resp, String[] inlinesBtn){
		InlineKeyboardMarkup buttonsClient = new InlineKeyboardMarkup(
				new InlineKeyboardButton[]{
						new InlineKeyboardButton("Conheça Melhor ").url(inlinesBtn[0]),
						new InlineKeyboardButton("Abrir no Maps ").url(inlinesBtn[1])
				});
		bot.execute(new SendMessage(chatId, resp).parseMode(ParseMode.HTML).replyMarkup(buttonsClient));
	}
	
	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

	}
	
	public void setStatus(int newStatus){
		this.status = newStatus;
	}
	
	public int getStatus(){
		return this.status;
	}	
	
}
