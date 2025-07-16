package com.jantabank.services;

import com.jantabank.entities.User;

public interface UserService 
{
	int saveUserAndCreateAccount(User user);
	User getUser(String userid);
}
