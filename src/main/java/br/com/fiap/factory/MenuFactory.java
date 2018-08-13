package br.com.fiap.factory;

import br.com.fiap.app.App;
import br.com.fiap.services.MenuCriarConta;
import br.com.fiap.services.MenuDeposito;
import br.com.fiap.services.MenuExibicaoDadosTitular;
import br.com.fiap.services.MenuExibicaoLancamentos;
import br.com.fiap.services.MenuExibicaoRetiradas;
import br.com.fiap.services.MenuExibicaoSaldo;
import br.com.fiap.services.MenuExibicaoTarifas;
import br.com.fiap.services.MenuInclusaoDeDependente;
import br.com.fiap.services.MenuModificacaoDeConta;
import br.com.fiap.services.MenuPrincipal;
import br.com.fiap.services.MenuSaque;
import br.com.fiap.services.MenuSolicitacaoDeEmprestimo;
import br.com.fiap.services.MenuSolicitacaoDeExtrato;

public class MenuFactory {
	
	private static MenuInterface instance = null;
	
	public static MenuInterface getInstance(String option) throws Exception
	{
		switch (option) {
			case App.MENU_PRINCIPAL: instance = new MenuPrincipal(); break;
			case App.MENU_CRIAR_CONTA: instance = new MenuCriarConta(); break;
			case App.MENU_MODIFICACAO_DE_CONTA: instance = new MenuModificacaoDeConta(); break;
			case App.MENU_INCLUSAO_DEPENDENTE: instance = new MenuInclusaoDeDependente(); break;
			case App.MENU_EXIBICAO_DADOS_TITULAR: instance = new MenuExibicaoDadosTitular(); break;
			case App.MENU_DEPOSITO: instance = new MenuDeposito(); break;
			case App.MENU_SAQUE: instance = new MenuSaque(); break;
			case App.MENU_SOLICITACAO_DE_EXTRATO: instance = new MenuSolicitacaoDeExtrato(); break;
			case App.MENU_SOLICITACAO_DE_EMPRESTIMO: instance = new MenuSolicitacaoDeEmprestimo(); break;
			case App.MENU_EXIBICAO_SALDO: instance = new MenuExibicaoSaldo(); break;
			case App.MENU_EXIBICAO_LANCAMENTOS: instance = new MenuExibicaoLancamentos(); break;
			case App.MENU_EXIBICAO_RETIRADAS: instance = new MenuExibicaoRetiradas(); break;
			case App.MENU_EXIBICAO_TARIFAS: instance = new MenuExibicaoTarifas(); break;
			default:  throw new Exception("Opção "+option+" não aceita!");
		}
		
		return instance;
	}
	
}
