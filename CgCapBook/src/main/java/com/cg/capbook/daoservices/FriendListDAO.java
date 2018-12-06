package com.cg.capbook.daoservices;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cg.capbook.beans.FriendsList;
public interface FriendListDAO extends JpaRepository<FriendsList, Integer>{
	@Query("select  f.friendEmail from FriendsList f  where f.userEmail=:userEmail or f.friendEmail=:userEmail")
	ArrayList<String> getAllFriendsList(@Param("userEmail")String userEmail); 

}
