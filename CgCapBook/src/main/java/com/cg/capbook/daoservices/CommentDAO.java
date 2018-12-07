package com.cg.capbook.daoservices;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.capbook.beans.Comment;
public interface CommentDAO extends JpaRepository<Comment, Integer>{
}