package br.com.fiap.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.entity.Cliente;
import br.com.fiap.factory.MenuFactory;
import br.com.fiap.services.MenuCriarContaProcess;
import br.com.fiap.services.MenuPrincipalBoasVindas;
import okhttp3.OkHttpClient;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static TelegramBot telegramBot;
	
	private final static String apiKey = "";
	
	public final static Long CHAT_ID = 600401980l;
	
	public final static String MESSAGE_BOAS_VENDAS = "Bem vindo ao banco Teste";
	
	public final static String MENU_BOAS_VINDAS = "Boas vindas";
	public final static String MENU_PRINCIPAL = "Menú principal";
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
	
	public final static String TIPO_CONTA_POUPANCA = "Conta poupança";
	public final static String TIPO_CORRENTE = "Conta corrente";
	
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
	
	public static int updateId = 0;
	
    public static void main( String[] args )
    {
    	
//    	Cliente cliente = new Cliente();
//    	cliente.setNome("Adriano La Selva");
//    	
//    	try(
//		    FileOutputStream fout = new FileOutputStream("./36162266869");
//		    ObjectOutputStream oos = new ObjectOutputStream(fout);
//		){
//		    oos.writeObject(cliente);
//		} catch (Exception ex) {
//		    ex.printStackTrace();
//		}
//    	
//    	try(
//			FileInputStream fin = new FileInputStream("./36162266869");
//			ObjectInputStream oos = new ObjectInputStream(fin);
//		){
//    		Cliente cliente2 = (Cliente)oos.readObject();
//    		System.out.println(cliente2.toString());
//		} catch (Exception ex) {
//		    ex.printStackTrace();
//		}
    	
    	
    	
    	
    	telegramBot = new TelegramBot.Builder(apiKey)
//    			.okHttpClient(new OkHttpClient())	
//    			.updateListenerSleep(1000)
    			.build();
    	
		new MenuPrincipalBoasVindas().process();
    	
//    	App.telegramBot.execute(new SendMessage(CHAT_ID, "Preencha os dados a seguir"));
//    	App.telegramBot.execute(new SendMessage(CHAT_ID, "Preencha seu CPF"));
//    	App.telegramBot.setUpdatesListener(new MenuCriarContaProcess());
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	new MenuPrincipalBoasVindas().process();
    	
    	

//    	String accountFileName = String.format("%s.db", "00001");
//    	
//		try(
//			FileWriter fw = new FileWriter(accountFileName);
//			PrintWriter out = new PrintWriter(fw);
//		){
//			out.print("Escrita em Arquivo Texto");
//		} catch (IOException e1) {
//			System.out.println(e1.getMessage());
//		}
//    	
//		String linha=null;
//		try(
//			FileReader fr = new FileReader(accountFileName);
//			BufferedReader br = new BufferedReader(fr);
//		){
//			while ((linha = br.readLine()) != null){
//				System. out.println(linha);
//			}
//		} catch (FileNotFoundException e) {
//			System.out.println(e.getMessage());
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
    	
//		String linha=null;
//		try(
//			FileReader fr = new FileReader("entrada.txt");
//			BufferedReader br = new BufferedReader(fr);
//		){
//			while ((linha = br.readLine()) != null){
//			System. out.println(linha);
//			}
//		}
    	
    	
//    	createListener(CHAT_ID, OPCOES);
    	
//    	telegramBot.setUpdatesListener(new UpdatesListener() {
//			
//			@Override
//			public int process(List<Update> updates) {
//				
//				updates.forEach(update -> {
//					
//					System.out.println(update.message().messageId()+" - "+update.message().text());
//					
//					for (String opcao : OPCOES) {
//						if(update.message().text().equals(opcao)) {
//							try {
//								
//								System.out.println("selecionada opção: "+opcao);
//								
//								BaseResponse deleteMessageResponse = telegramBot.execute(new DeleteMessage(
//										CHAT_ID, 
//										update.message().messageId()));
//							
//								System.out.println(deleteMessageResponse.toString());
//								
//								MenuFactory.getInstance(opcao).process();
//								
//							} catch (Exception e) {
//								System.out.println(e.getMessage());
//							}
//						}
//					}
//				});
//				
//				return updates.get(updates.size()-1).updateId();
//			}
//		});
    	
    }
//    
//    public static void createListener(Long chatId, String[] opcoes) {
//    	telegramBot.setUpdatesListener(new UpdatesListener() {
//			
//			@Override
//			public int process(List<Update> updates) {
//				
//				updates.forEach(update -> {
//					
//					System.out.println(update.message().messageId()+" - "+update.message().text());
//					
//					for (String opcao : opcoes) {
//						if(update.message().text().equals(opcao)) {
//							try {
//								
//								System.out.println("selecionada opção: "+opcao);
//								
//								BaseResponse deleteMessageResponse = telegramBot.execute(new DeleteMessage(
//										chatId, 
//										update.message().messageId()));
//							
//								System.out.println(deleteMessageResponse.toString());
//								
//								MenuFactory.getInstance(opcao).process();
//								
//							} catch (Exception e) {
//								System.out.println(e.getMessage());
//							}
//						}
//					}
//				});
//				
//				return updates.get(updates.size()-1).updateId();
//			}
//		});
//    }
    
    
}
