package br.com.fiap.services;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.app.App;
import br.com.fiap.factory.MenuInterface;

public class MenuPrincipal implements MenuInterface {
	
	
	@Override
	public void process() {
	
		SendMessage message = new SendMessage(App.CHAT_ID, "Preencha as seguintes informações:");
		
		SendResponse sendResponse = App.telegramBot.execute(message);
		
		try {
			sendResponse.wait();
			
			if(!sendResponse.isOk())
				System.out.println(sendResponse.toString());
			
			
			
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
