package com.cg.capbook.services;

import com.cg.capbook.beans.User;
import com.cg.capbook.exception.UserNotFoundException;

public interface UserServices {

	User acceptUserDetails(User user);
	User getUserDetails(String emailid) throws UserNotFoundException;
}
