package com.cg.capbook.services;

import com.cg.capbook.beans.User;
import com.cg.capbook.exceptions.IncorrectPasswordException;
import com.cg.capbook.exceptions.UserNotFoundException;

public interface UserServices {

	User acceptUserDetails(User user);
	User getUserDetails(String emailid,String password) throws UserNotFoundException, IncorrectPasswordException;
}
