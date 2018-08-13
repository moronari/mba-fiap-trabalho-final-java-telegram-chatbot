package br.com.fiap.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import br.com.fiap.entity.Conta;
import br.com.fiap.helpers.FileHelper;

public class MenuCriarContaProcess implements UpdatesListener {

	private int step = 1;
	
	private Cliente cliente = new Cliente();
	
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
			
			switch (step) {
				case 1:
				
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
					
					if(fileObject.isPresent()) {
						this.cliente = (Cliente)fileObject.get();
						
						System.out.println("Step 5: "+update.updateId());
						step=5;
						
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-) já existe um cadastro com este CPF"));
						
						try {
							FileHelper.saveObject("./"+String.valueOf(this.cliente.getCpf()), this.cliente);
							App.telegramBot.execute(new SendMessage(update.message().chat().id(), ":-) Segue abaixo os dados da sua conta \n\n Agência: "+this.cliente.getConta().getNumAg()+"\n Conta: "+this.cliente.getConta().getNumCc()+"\n Saldo atual: "+this.cliente.getConta().getSaldo())
									.replyMarkup(menuConcluidoOpcoes));
						}catch (Exception e) {
							App.telegramBot.execute(new SendMessage(update.message().chat().id(), e.getMessage()));
							continue;
						}
						
					}else {
						System.out.println("Step 2: "+update.updateId());
						step++;
						
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								"Preencha seu Nome"));
					}
					
				}
				
				break;
			case 2:
				
				baseResponse = App.telegramBot.execute(
						new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
				
				if(baseResponse.isOk()) {
					
					if(update.message().text().length()<5) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( O nome deve conter no mínimo 5 caracteres"));
						continue;
					}
					
					if(update.message().text().length()>50) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( O nome deve conter no máximo 50 caracteres"));
						continue;
					}
					
					this.cliente.setNome(update.message().text());
					
					System.out.println("Step 3: "+update.updateId());
					step++;
					
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							"Preencha seu RG"));
				}
				
				break;
			case 3:
				
				baseResponse = App.telegramBot.execute(
						new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
				
				if(baseResponse.isOk()) {
					
					if(update.message().text().length()<9) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( O rg deve conter no mínimo 9 caracteres"));
						continue;
					}
					
					if(update.message().text().length()>11) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( O rg deve conter no máximo 11 caracteres"));
						continue;
					}
					
					this.cliente.setRg(update.message().text());
					
					System.out.println("Step 4: "+update.updateId());
					step++;
					
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							"Preencha sua data de nascimetno (dd/MM/yyyy)"));
				}
				
				break;
			case 4:
				
				baseResponse = App.telegramBot.execute(
						new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
				
				if(baseResponse.isOk()) {
					
					if(update.message().text().isEmpty()) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( Insira uma data de nascimento válida no formado dd/MM/yyyy"));
						continue;
					}
					
					try {
						this.cliente.setDataNascimento(LocalDate.parse(update.message().text(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					}catch (Exception e) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( Insira uma data de nascimento válida no formado dd/MM/yyyy"));
						continue;
					}
					
					this.cliente.setConta(new Conta(this.cliente));
					
					try {
						FileHelper.saveObject("./"+String.valueOf(this.cliente.getCpf()), this.cliente);
						App.telegramBot.execute(new SendMessage(update.message().chat().id(), ":-) Seu cadastro foi realizado com sucesso \n\n segue abaixo os dados da sua conta \n\n Agência: "+this.cliente.getConta().getNumAg()+"\n Conta: "+this.cliente.getConta().getNumCc()+"\n Saldo atual: "+this.cliente.getConta().getSaldo())
								.replyMarkup(menuConcluidoOpcoes));
					}catch (Exception e) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(), e.getMessage())
								.replyMarkup(menuConcluidoOpcoes));
						continue;
					}
					
					System.out.println("Step 5: "+update.updateId());
					System.out.println("Cliente preenchido com sucesso");
					System.out.println(this.cliente.toString());
					step++;
				}
				
				break;
			case 5: 
				System.out.println("step 5");
				break;
			default:
				System.out.println("default");
				break;
			}
			
			
		}
		
		return App.updateId;
	}

}
