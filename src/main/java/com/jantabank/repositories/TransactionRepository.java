package com.jantabank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jantabank.entities.TransactionInfo;

public interface TransactionRepository extends JpaRepository<TransactionInfo,Integer>  
{
	@Query("from TransactionInfo where (fromaccount=:arg and toaccount=:arg) or (fromaccount=:arg and type='Debit') or (toaccount=:arg and type='Credit')")
	List<TransactionInfo> getTransactionListOfCurrentUser(@Param("arg") int accountno);
}
