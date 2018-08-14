package br.com.fiap.services;

import java.util.List;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.MessagesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.app.App;
import br.com.fiap.entity.Cliente;
import br.com.fiap.factory.MenuFactory;
import br.com.fiap.factory.MenuInterface;

public class MenuCriarConta implements MenuInterface {
		
	private ReplyKeyboardMarkup menuOpcoes = new ReplyKeyboardMarkup(
			new String[]{"Sair"}
		)
		.oneTimeKeyboard(true)
		.resizeKeyboard(true)
		.oneTimeKeyboard(true)
		.selective(true);
	
	@Override
	public void process() {
		
		App.telegramBot.removeGetUpdatesListener();
		
		App.telegramBot.execute(new SendMessage(App.CHAT_ID, "Preencha os dados a seguir")
				.replyMarkup(menuOpcoes));
    	App.telegramBot.execute(new SendMessage(App.CHAT_ID, "Preencha seu CPF"));
    	App.telegramBot.setUpdatesListener(new MenuCriarContaProcess(), new GetUpdates().limit(App.updatesLimit).offset(App.updateId));
		
	}
	
}
