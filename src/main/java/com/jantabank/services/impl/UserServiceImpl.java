package com.jantabank.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jantabank.entities.Account;
import com.jantabank.entities.User;
import com.jantabank.repositories.AccountRepository;
import com.jantabank.repositories.UserRepository;
import com.jantabank.services.UserService;

@Service
public class UserServiceImpl implements UserService 
{
	@Autowired private UserRepository userRepository;
	@Autowired private AccountRepository accountRepository;
	
	public int saveUserAndCreateAccount(User user) 
	{
		//Saving user object
		userRepository.save(user);
		//Saving account object
		Account account=new Account();
		account.setUserid(user.getUserid());
		accountRepository.save(account);
		//Returning account no
		return account.getAccountno();
	}

	public User getUser(String userid) 
	{
		return userRepository.findById(userid).orElse(null);
	}
}
