package br.com.fiap.services;

import java.util.List;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.app.App;
import br.com.fiap.factory.MenuFactory;
import br.com.fiap.factory.MenuInterface;

public class MenuPrincipalBoasVindasProcess implements UpdatesListener {

	private String[] opcoes = new String[]{
			App.MENU_CRIAR_CONTA,
			App.MENU_MODIFICACAO_DE_CONTA,
			App.MENU_INCLUSAO_DEPENDENTE,
			App.MENU_EXIBICAO_DADOS_TITULAR,
			App.MENU_DEPOSITO,
			App.MENU_SAQUE,
			App.MENU_SOLICITACAO_DE_EXTRATO,
			App.MENU_SOLICITACAO_DE_EMPRESTIMO,
			App.MENU_EXIBICAO_SALDO,
			App.MENU_EXIBICAO_LANCAMENTOS,
			App.MENU_EXIBICAO_RETIRADAS,
			App.MENU_EXIBICAO_TARIFAS
		};
	
	private ReplyKeyboardMarkup menuOpcoes = new ReplyKeyboardMarkup(
			new String[]{App.MENU_CRIAR_CONTA},
			new String[]{App.MENU_MODIFICACAO_DE_CONTA},
			new String[]{App.MENU_INCLUSAO_DEPENDENTE},
			new String[]{App.MENU_EXIBICAO_DADOS_TITULAR},
			new String[]{App.MENU_DEPOSITO},
			new String[]{App.MENU_SAQUE},
			new String[]{App.MENU_SOLICITACAO_DE_EXTRATO},
			new String[]{App.MENU_SOLICITACAO_DE_EMPRESTIMO},
			new String[]{App.MENU_EXIBICAO_SALDO},
			new String[]{App.MENU_EXIBICAO_LANCAMENTOS},
			new String[]{App.MENU_EXIBICAO_RETIRADAS},
			new String[]{App.MENU_EXIBICAO_TARIFAS}
		)
		.resizeKeyboard(true)
		.oneTimeKeyboard(true)
		.selective(true);
	
	@Override
	public int process(List<Update> updates) {
		
		updates.forEach(update -> {
			
			System.out.println(update.message().messageId()+" - "+update.message().text());
			
			for (String opcao : opcoes) {
				if(update.message().text().equals(opcao)) {
					try {
						
						System.out.println("selecionada opção: "+opcao);
						
						MenuFactory.getInstance(opcao).process();
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
		});
		
		return updates.get(updates.size()-1).updateId();
	}

}
