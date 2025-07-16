package com.jantabank.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController 
{
	@GetMapping
	public String getHomeView(HttpSession session)
	{
		System.out.println("Home controller");
		if(session.getAttribute("username")==null)
			return "user/login/login-form";
		return "home/home-page";
	}
}
