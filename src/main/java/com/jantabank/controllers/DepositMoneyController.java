package com.jantabank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jantabank.entities.TransactionInfo;
import com.jantabank.services.AccountService;

@Controller
public class DepositMoneyController 
{
	@Autowired private AccountService accountService;
	
	@GetMapping("services/deposit-form")
	public String getDepositForm()
	{
		return "services/deposit/deposit-money-form";
	}
	@GetMapping("services/deposit.do")
	public String depositMoney(@RequestParam("amount") int amount,@SessionAttribute("accountno") int accountno,RedirectAttributes redirectAttributes)
	{
		TransactionInfo transaction=accountService.creditAmount(amount,accountno);
		redirectAttributes.addFlashAttribute("transactionInfo", transaction);
		return "redirect:deposit-success";
	}
	@GetMapping("services/deposit-success")
	public ModelAndView depositMoneySuccess(@ModelAttribute("transactionInfo") TransactionInfo transaction)
	{
		ModelAndView modelAndView=new ModelAndView("services/deposit/deposit-money-success");
		modelAndView.addObject("transaction", transaction);
		return modelAndView;
	}
}
