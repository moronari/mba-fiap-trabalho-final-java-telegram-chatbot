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
import br.com.fiap.entity.Conta;
import br.com.fiap.factory.MenuInterface;
import br.com.fiap.helpers.FileHelper;

public class MenuInclusaoDeDependenteProcess implements UpdatesListener {

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
					
					if(!fileObject.isPresent()) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( Não há conta para o CPF informado!!!"));
						continue;
					}else {
						this.cliente = (Cliente)fileObject.get();
						
						System.out.println("Step 2: "+update.updateId());
						step++;
						
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								"Insira o CPF do dependente:"));
					}
				}
				
				break;
			case 2:
				
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
						this.dependente.setCpf(Long.parseLong(update.message().text()));
					}catch (Exception e) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( o cpf deve conter apenas números"));
						continue;
					}
					
					System.out.println("Step 3: "+update.updateId());
					step++;
					
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							"Preencha o nome: "));
				}
				
				break;
			case 3:
				
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
					
					this.dependente.setNome(update.message().text());
					
					System.out.println("Step 3: "+update.updateId());
					step++;
					
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							"Preencha o RG (Atual: "+this.cliente.getRg()+"): "));
				}
				
				break;
			case 4:
				
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
					
					this.dependente.setRg(update.message().text());
					
					System.out.println("Step 4: "+update.updateId());
					step++;
					
					App.telegramBot.execute(new SendMessage(update.message().chat().id(),
							"Preencha sua data de nascimento (dd/MM/yyyy) (Atual:"+this.cliente.getDataNascFormat()+"):"));
				}
				
				break;
			case 5:
				
				baseResponse = App.telegramBot.execute(
						new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
				
				if(baseResponse.isOk()) {
					
					if(update.message().text().isEmpty()) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( Insira uma data de nascimento válida no formado dd/MM/yyyy"));
						continue;
					}
					
					try {
						this.dependente.setDataNascimento(LocalDate.parse(update.message().text(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					}catch (Exception e) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(),
								":-( Insira uma data de nascimento válida no formado dd/MM/yyyy"));
						continue;
					}
					
					this.cliente.getConta().addDependente(dependente);
					
					try {
						FileHelper.saveObject("./"+String.valueOf(this.cliente.getCpf()), this.cliente);
						App.telegramBot.execute(new SendMessage(update.message().chat().id(), ":-) Dependente adicionado com sucesso \n\n segue abaixo os dados da sua conta \n\n Agência: "+this.cliente.getConta().getNumAg()+"\n Conta: "+this.cliente.getConta().getNumCc()+"\n Saldo atual: "+this.cliente.getConta().getSaldo())
								.replyMarkup(menuConcluidoOpcoes));
					}catch (Exception e) {
						App.telegramBot.execute(new SendMessage(update.message().chat().id(), e.getMessage())
								.replyMarkup(menuConcluidoOpcoes));
						continue;
					}
					
					System.out.println("Step 5: "+update.updateId());
					System.out.println("Dependente adicionado com sucesso");
					System.out.println(this.cliente.toString());
					step++;
				}
				
				break;
			case 6: 
				System.out.println("step 6");
				break;
			default:
				System.out.println("default");
				break;
			}
			
			
		}
		
		return App.updateId;
	}

}