package com.cg.capbook.daoservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.capbook.beans.UploadFile;

public interface UploadFileDAO extends JpaRepository<UploadFile, Integer>{
@Query("Select a from UploadFile a where a.fileName=:emailId")
UploadFile getFile(@Param("emailId")String emailId);
}
