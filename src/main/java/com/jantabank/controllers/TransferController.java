package com.jantabank.controllers;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jantabank.entities.TransactionInfo;
import com.jantabank.services.AccountService;

@Controller
public class TransferController 
{
	@Autowired private AccountService accountService;
	
	@GetMapping("services/transfer-form")
	public String getTransferFormView()
	{
		return "services/transfer/transfer-money-form";
	}
	@GetMapping("services/verify-account.do")
	public ModelAndView verifyAccountNo(@RequestParam("accountno") int accountno,HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("services/transfer/transfer-money-form");
		session.setAttribute("raccountno",accountno);
		if((Integer)session.getAttribute("accountno")==accountno)
		{
			modelAndView.addObject("msg","This is your own account number");
			return modelAndView;
		}
		String name=accountService.getAccountHolder(accountno);
		if(name==null)
		{
			modelAndView.addObject("msg","Entered account number does not exist");
			return modelAndView;
		}
		modelAndView.setViewName("services/transfer/transfer-money-form2");
		session.setAttribute("rname",name);
		return modelAndView;
	}
	@GetMapping("services/transfer.do")
	public String performTransferMoneyOperation(@RequestParam("amount") int amount,HttpSession httpSession,RedirectAttributes redirectAttributes,Model model)
	{
		int accountno=(Integer)httpSession.getAttribute("accountno");
		int accountBalance=accountService.getAmount(accountno);
		if(amount>accountBalance)
		{
			model.addAttribute("msg","You do not have sufficient amount into your account");
			model.addAttribute("amount",amount);
			return "services/transfer/transfer-money-form2";
		}
		TransactionInfo transaction=accountService.transferMoney(amount,accountno,(Integer)httpSession.getAttribute("raccountno"));
		redirectAttributes.addFlashAttribute("transaction", transaction);
		return "redirect:transfer-success";
	}
	@GetMapping("services/transfer-success")
	public ModelAndView transferMoneySuccess(@ModelAttribute("transaction") TransactionInfo transaction)
	{
		ModelAndView modelAndView=new ModelAndView("services/transfer/transfer-money-success");
		modelAndView.addObject("transaction",transaction);
		return modelAndView;
	}
}
