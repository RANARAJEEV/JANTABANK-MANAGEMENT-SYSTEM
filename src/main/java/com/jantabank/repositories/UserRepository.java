package com.jantabank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jantabank.entities.User;

public interface UserRepository extends JpaRepository<User,String>
{
}
