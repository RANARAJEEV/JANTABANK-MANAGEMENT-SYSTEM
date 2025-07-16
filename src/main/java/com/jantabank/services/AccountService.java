package com.jantabank.services;

import com.jantabank.entities.Account;
import com.jantabank.entities.TransactionInfo;

public interface AccountService 
{
	Account getAccountByUserId(String userid);
	int getAmount(int accountno);
	TransactionInfo creditAmount(int amount, int accountno);
	TransactionInfo debitAmount(int amount, int accountno);
	String getAccountHolder(int accountno);
	TransactionInfo transferMoney(int amount, int saccountno,int raccountno);
}
