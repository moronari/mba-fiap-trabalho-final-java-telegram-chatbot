package br.com.fiap.services;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.app.App;
import br.com.fiap.factory.MenuInterface;

public class MenuModificacaoDeConta implements MenuInterface {
	
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
    	App.telegramBot.setUpdatesListener(new MenuModificacaoDeContaProcess(), new GetUpdates().limit(App.updatesLimit).offset(App.updateId));
		
	}

}
