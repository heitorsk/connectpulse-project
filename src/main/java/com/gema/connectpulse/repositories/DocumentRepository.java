package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.DocumentEntity;
import com.gema.connectpulse.entities.PhoneEntity;
import com.gema.connectpulse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {

    List<DocumentEntity> findDocumentByUser(UserEntity user);
}
