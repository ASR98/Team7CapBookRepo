package com.cg.capbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.UserDAO;
import com.cg.capbook.exception.UserNotFoundException;
@Component
public class UserServicesImpl implements UserServices{
   @Autowired
	UserDAO userDAO;
	@Override
	public User acceptUserDetails(User user) {
		user=userDAO.save(user);
		return user;
	}
	@Override
	public User getUserDetails(String emailid) throws UserNotFoundException {
		User user=userDAO.findById(emailid).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		return user;
	}

}
