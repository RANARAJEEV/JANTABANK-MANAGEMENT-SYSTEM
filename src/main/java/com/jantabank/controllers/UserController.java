package com.jantabank.controllers;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jantabank.entities.Account;
import com.jantabank.entities.User;
import com.jantabank.services.AccountService;
import com.jantabank.services.UserService;

@Controller
public class UserController 
{
	@Autowired private UserService userService;
	@Autowired private AccountService accountService;
	
	/*@GetMapping("user/login")
	public String getLoginForm()
	{
		return "user/login/login-form";
	}*/
	@GetMapping("user/registration")
	public String getRegistrationForm(Model model)
	{
		model.addAttribute("cdate",LocalDate.now());
		return "user/registration/registration-form";
	}
	@PostMapping("user/register-user")
	public ModelAndView registerUserAndCreateAccount(User user)
	{
		int accountno=userService.saveUserAndCreateAccount(user);
		ModelAndView modelAndView=new ModelAndView("user/registration/registration-success");
		modelAndView.addObject("username",user.getFirstname()+" "+user.getLastname());
		modelAndView.addObject("accountno",accountno);
		return modelAndView;
	}
	@PostMapping("user/authenticate-user")
	public String authenticateUser(@RequestParam("userid") String userid, @RequestParam("password") String password,Model model,HttpSession session)
	{
		User user=userService.getUser(userid);
		if(user==null)
		{
			model.addAttribute("msg","Entered userid does not exist");
			model.addAttribute("uid",userid);
			return "user/login/login-form";
		}
		String dbpassword=user.getPassword();
		if(!password.equals(dbpassword))
		{
			model.addAttribute("msg","Entered password is wrong");
			model.addAttribute("uid",userid);
			return "user/login/login-form";
		}
		session.setAttribute("username",user.getFirstname()+" "+user.getLastname());
		Account account=accountService.getAccountByUserId(userid); 
		session.setAttribute("accountno",account.getAccountno());
		return "redirect:/";
	}
	@GetMapping("user/logout")
	public String userLogout(HttpSession session,Model model)
	{
		model.addAttribute("username",session.getAttribute("username"));
		session.invalidate();
		return "user/login/logout-success";
	}
}
