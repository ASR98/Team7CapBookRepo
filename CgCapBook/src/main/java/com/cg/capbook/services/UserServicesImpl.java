package com.cg.capbook.services;

import org.springframework.stereotype.Component;

import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.UserDAO;
@Component
public class UserServicesImpl implements UserServices{

	UserDAO userDAO;
	@Override
	public User acceptUserDetails(User user) {
		user=userDAO.save(user);
		return user;
	}

}
