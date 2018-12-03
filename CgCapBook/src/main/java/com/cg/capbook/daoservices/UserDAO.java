package com.cg.capbook.daoservices;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.capbook.beans.User;

public interface UserDAO extends JpaRepository<User, String>{

}
