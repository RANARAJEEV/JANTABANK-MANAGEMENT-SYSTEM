package com.jantabank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jantabank.entities.TransactionInfo;
import com.jantabank.services.AccountService;

@Controller
public class WithdrawMoneyController 
{
	@Autowired private AccountService accountService;
	
	@GetMapping("services/withdraw-form")
	public String getWithdrawForm()
	{
		return "services/withdraw/withdraw-money-form";
	}
	@GetMapping("services/withdraw.do")
	public String withdrawMoney(@RequestParam("amount") int amount,@SessionAttribute("accountno") int accountno,RedirectAttributes redirectAttributes,Model model)
	{
		int accountBalance=accountService.getAmount(accountno);
		if(amount>accountBalance)
		{
			model.addAttribute("msg","You do not have sufficient amount into your account");
			model.addAttribute("amount",amount);
			return "services/withdraw/withdraw-money-form";
		}
		TransactionInfo transaction=accountService.debitAmount(amount,accountno);
		redirectAttributes.addFlashAttribute("transactionInfo", transaction);
		return "redirect:withdraw-success";
	}
	@GetMapping("services/withdraw-success")
	public ModelAndView withdrawMoneySuccess(@ModelAttribute("transactionInfo") TransactionInfo transaction)
	{
		ModelAndView modelAndView=new ModelAndView("services/withdraw/withdraw-money-success");
		modelAndView.addObject("transaction", transaction);
		return modelAndView;
	}
}
