package com.jantabank.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jantabank.entities.Account;
import com.jantabank.entities.TransactionInfo;
import com.jantabank.repositories.AccountRepository;
import com.jantabank.repositories.TransactionRepository;
import com.jantabank.services.AccountService;
import com.jantabank.utility.DateTimeUtility;

@Service
public class AccountServiceImpl implements AccountService 
{
	@Autowired private AccountRepository accountRepository;
	@Autowired private DateTimeUtility dateTimeUtility;
	@Autowired private TransactionRepository transactionRepository;

	public Account getAccountByUserId(String userid) 
	{
		return accountRepository.findByUserid(userid);
	}

	public int getAmount(int accountno) 
	{
		Account account=accountRepository.findById(accountno).orElse(null);
		return account.getAmount();
	}

	public TransactionInfo creditAmount(int amount, int accountno) 
	{
		//Updating amount into account object
		accountRepository.updateAmount(amount,accountno);
		//Persisting transactioninfo object
		TransactionInfo transaction=new TransactionInfo();
		transaction.setFromaccount(accountno);
		transaction.setAmount(amount);
		transaction.setType("Credit");
		transaction.setDate(dateTimeUtility.getCurrentDate());
		transaction.setTime(dateTimeUtility.getCurrentTime());
		transaction.setToaccount(accountno);
		transactionRepository.save(transaction);
		return transaction;
	}

	public TransactionInfo debitAmount(int amount, int accountno) 
	{
		accountRepository.updateAmount(-amount,accountno);
		TransactionInfo transaction=new TransactionInfo();
		transaction.setFromaccount(accountno);
		transaction.setAmount(amount);
		transaction.setType("Debit");
		transaction.setDate(dateTimeUtility.getCurrentDate());
		transaction.setTime(dateTimeUtility.getCurrentTime());
		transaction.setToaccount(accountno);
		transactionRepository.save(transaction);
		return transaction;
	}
	public String getAccountHolder(int accountno) 
	{
		String name=accountRepository.getNameByAccountno(accountno);
		return name;
	}

	public TransactionInfo transferMoney(int amount, int saccountno, int raccountno) 
	{
		//Updating sender amount
		accountRepository.updateAmount(-amount,saccountno);
		//Updating receiver amount
		accountRepository.updateAmount(amount,raccountno);
		saveRecieverTransferTransaction(saccountno,amount,raccountno);
		return saveSenderTransferTransaction(saccountno,amount,raccountno);
	}
	private void saveRecieverTransferTransaction(int saccountno, int amount, int raccountno) 
	{
		TransactionInfo transaction=new TransactionInfo();
		transaction.setFromaccount(saccountno);
		transaction.setAmount(amount);
		transaction.setType("Credit");
		transaction.setDate(dateTimeUtility.getCurrentDate());
		transaction.setTime(dateTimeUtility.getCurrentTime());
		transaction.setToaccount(raccountno);
		transactionRepository.save(transaction);
	}

	private TransactionInfo saveSenderTransferTransaction(int saccountno,int amount, int raccountno)
	{
		TransactionInfo transaction=new TransactionInfo();
		transaction.setFromaccount(saccountno);
		transaction.setAmount(amount);
		transaction.setType("Debit");
		transaction.setDate(dateTimeUtility.getCurrentDate());
		transaction.setTime(dateTimeUtility.getCurrentTime());
		transaction.setToaccount(raccountno);
		transactionRepository.save(transaction);
		return transaction;
	}
}
