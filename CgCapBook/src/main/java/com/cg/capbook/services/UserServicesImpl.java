package com.cg.capbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.capbook.beans.User;
import com.cg.capbook.daoservices.UserDAO;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;
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
	public User getUserDetails(String emailid,String password) throws UserNotFoundException, IncorrectPasswordException {
		User user=userDAO.findById(emailid).orElseThrow(()->new UserNotFoundException("User Details Not found"));
		if(user.getPassword()!=password) throw new IncorrectPasswordException("Incorrect Password");
		return user;
	}

}
