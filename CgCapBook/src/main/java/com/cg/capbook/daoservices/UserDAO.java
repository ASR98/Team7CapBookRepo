package com.cg.capbook.daoservices;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cg.capbook.beans.User;
public interface UserDAO extends JpaRepository<User, String>{
	
}
