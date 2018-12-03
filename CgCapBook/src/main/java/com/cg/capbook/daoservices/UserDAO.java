package com.cg.capbook.daoservices;

import javax.validation.constraints.Email;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.capbook.beans.User;

public interface UserDAO extends JpaRepository<User, Integer>{

}
