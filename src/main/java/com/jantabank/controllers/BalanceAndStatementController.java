package com.jantabank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.jantabank.entities.TransactionInfo;
import com.jantabank.repositories.TransactionRepository;
import com.jantabank.services.AccountService;

@Controller
public class BalanceAndStatementController 
{
	@Autowired private AccountService accountService;
	@Autowired private TransactionRepository transactionRepository;
	
	@GetMapping("services/view-balance")
	public ModelAndView getAccountBalance(@SessionAttribute("accountno") int accountno)
	{
		int amount=accountService.getAmount(accountno);
		ModelAndView modelAndView=new ModelAndView("services/balance/show-balance");
		modelAndView.addObject("amount",amount);
		return modelAndView;
	}
	@GetMapping("services/view-statements")
	public ModelAndView getStatementOfCurrentUser(@SessionAttribute("accountno") int accountno)
	{
		List<TransactionInfo> statements=transactionRepository.getTransactionListOfCurrentUser(accountno);
		ModelAndView modelAndView=new ModelAndView("services/balance/show-statements");
		modelAndView.addObject("statementList",statements);
		return modelAndView;
	}		
}
