package br.com.fiap.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

import br.com.fiap.app.App;
import br.com.fiap.entity.Cliente;
import br.com.fiap.factory.MenuInterface;
import br.com.fiap.helpers.FileHelper;

public class MenuExibicaoDadosTitularProcess implements UpdatesListener {

	private int step = 1;
	
	private Cliente cliente = new Cliente();
	
	private Cliente dependente = new Cliente();
	
	private ReplyKeyboardMarkup menuConcluidoOpcoes = new ReplyKeyboardMarkup(
			new String[]{"Menu principal"}
		)
		.resizeKeyboard(true)
		.oneTimeKeyboard(true)
		.selective(true);
	
	@Override
	public int process(List<Update> updates) {
		
		BaseResponse baseResponse;
		
		for (Update update : updates) {
			
			if(App.updateId>=updates.get(updates.size()-1).updateId()) 
				continue;
			
			App.updateId = updates.get(updates.size()-1).updateId();
			
			if(update.message().text().equals("Menu principal")){
				new MenuPrincipalBoasVindas().process();
				continue;
			}
			
			if(update.message().text().equals("Sair")){
				new MenuPrincipalBoasVindas().process();
				continue;
			}
			
			baseResponse = App.telegramBot.execute(
					new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
			
			if(baseResponse.isOk()) {
				
				if(update.message().text().length()<10) {
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							":-( O cpf deve conter no mínimo 10 caracteres"));
					continue;
				}
				
				if(update.message().text().length()>12) {
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							":-( O cpf deve conter no máximo 12 caracteres"));
					continue;
				}
				
				try {
					this.cliente.setCpf(Long.parseLong(update.message().text()));
				}catch (Exception e) {
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							":-( o cpf deve conter apenas números"));
					continue;
				}
				
				Optional<Object> fileObject = FileHelper
						.loadObject("./"+String.valueOf(this.cliente.getCpf()));
				
				if(!fileObject.isPresent()) {
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							":-( Não há conta para o CPF informado!!!"));
					continue;
				}
				
				this.cliente = (Cliente)fileObject.get();
				
				
				StringBuilder message = new StringBuilder();
				
				message.append(":-) Segue abaixo os dados da sua conta \n\n Agência: "+this.cliente.getConta().getNumAg()+"\n Conta: "+this.cliente.getConta().getNumCc()+"\n Saldo atual: "+this.cliente.getConta().getSaldo());
				if(!this.cliente.getConta().getDependentes().isEmpty()) {
					this.cliente.getConta().getDependentes().forEach(dependente -> {
						message.append("\n\n-----------------------------");
						message.append("\nNome: "+dependente.getNome());
						message.append("\nCPF: "+dependente.getCpf());
						message.append("\nRG: "+dependente.getRg());
						message.append("\nNascimento: "+dependente.getDataNascFormat());
						message.append("\n-----------------------------");
					});
				}
				
				App.telegramBot.execute(new SendMessage(update.message().chat().id(), message.toString())
						.replyMarkup(menuConcluidoOpcoes));
				
				System.out.println(this.cliente.toString());
			}
			
			
		}
		
		return App.updateId;
	}

}