package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.PatientEntity;
import com.gema.connectpulse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {

    List<PatientEntity> findPatientByUser(UserEntity user);
}
