package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.DoctorEntity;
import com.gema.connectpulse.entities.PatientEntity;
import com.gema.connectpulse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {

    List<DoctorEntity> findDoctorByUser(UserEntity user);
}
