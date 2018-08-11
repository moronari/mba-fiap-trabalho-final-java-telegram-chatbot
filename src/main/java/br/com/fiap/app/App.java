package br.com.fiap.app;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.factory.MenuFactory;
import okhttp3.OkHttpClient;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static TelegramBot telegramBot;
	
	private final static String apiKey = "{apikey}";
	
	private final static Long CHAT_ID = 600401980l;
	
	public final static String MESSAGE_BOAS_VENDAS = "Bem vindo ao banco C6Bank";
	
	public final static String MENU_CRIAR_CONTA = "Criar conta";
	public final static String MENU_MODIFICACAO_DE_CONTA = "Modificação de conta";
	public final static String MENU_INCLUSAO_DEPENDENTE = "Inclusão de dependentes (conta-conjunta)";
	public final static String MENU_EXIBICAO_DADOS_TITULAR = "Exibição dos dados do titular e dependentes";
	public final static String MENU_DEPOSITO = "Depósito";
	public final static String MENU_SAQUE = "Saque";
	public final static String MENU_SOLICITACAO_DE_EXTRATO = "Solicitação de extrato";
	public final static String MENU_SOLICITACAO_DE_EMPRESTIMO = "Solicitação de empréstimo";
	public final static String MENU_EXIBICAO_SALDO = "Exibição de saldo devedor do empréstimo e prazo de pagamento";
	public final static String MENU_EXIBICAO_LANCAMENTOS = "Exibição dos lançamentos detalhada, com somatória ao final";
	public final static String MENU_EXIBICAO_RETIRADAS = "Exibição das retiradas, com somatória ao final";
	public final static String MENU_EXIBICAO_TARIFAS = "Exibição das tarifas de serviço";
	
	public final static String[] OPCOES = new String[]{
			MENU_CRIAR_CONTA,
			MENU_MODIFICACAO_DE_CONTA,
			MENU_INCLUSAO_DEPENDENTE,
			MENU_EXIBICAO_DADOS_TITULAR,
			MENU_DEPOSITO,
			MENU_SAQUE,
			MENU_SOLICITACAO_DE_EXTRATO,
			MENU_SOLICITACAO_DE_EMPRESTIMO,
			MENU_EXIBICAO_SALDO,
			MENU_EXIBICAO_LANCAMENTOS,
			MENU_EXIBICAO_RETIRADAS,
			MENU_EXIBICAO_TARIFAS
		};
	
    public static void main( String[] args )
    {
    	
    	telegramBot = new TelegramBot.Builder(apiKey)
    			.okHttpClient(new OkHttpClient())	
    			.build();
    	
    	ReplyKeyboardMarkup menu1 = new ReplyKeyboardMarkup(
	    			new String[]{MENU_CRIAR_CONTA},
	    			new String[]{MENU_MODIFICACAO_DE_CONTA},
	    			new String[]{MENU_INCLUSAO_DEPENDENTE},
	    			new String[]{MENU_EXIBICAO_DADOS_TITULAR},
	    			new String[]{MENU_DEPOSITO},
	    			new String[]{MENU_SAQUE},
	    			new String[]{MENU_SOLICITACAO_DE_EXTRATO},
	    			new String[]{MENU_SOLICITACAO_DE_EMPRESTIMO},
	    			new String[]{MENU_EXIBICAO_SALDO},
	    			new String[]{MENU_EXIBICAO_LANCAMENTOS},
	    			new String[]{MENU_EXIBICAO_RETIRADAS},
	    			new String[]{MENU_EXIBICAO_TARIFAS}
    			);
    	
    	SendMessage message = new SendMessage(CHAT_ID, MESSAGE_BOAS_VENDAS)
    			.replyMarkup(menu1);
    	
    	SendResponse sendResponse = telegramBot.execute(message);
    	
    	if(!sendResponse.isOk())
    		System.out.println(sendResponse.toString());
    	
    	telegramBot.setUpdatesListener(new UpdatesListener() {
			
			@Override
			public int process(List<Update> updates) {
				
				updates.forEach(update -> {
					
					System.out.println(update.message().messageId()+" - "+update.message().text());
					
					for (String opcao : OPCOES) {
						if(update.message().text() == opcao) {
							try {
								MenuFactory.getInstance(opcao).process();
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
					}
				});
				
				return updates.get(updates.size()-1).updateId();
			}
		});
    	
    }
    
    
}
