package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.AppointmentEntity;
import com.gema.connectpulse.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {


    @Query("SELECT a FROM appointments a WHERE a.doctor.id = :doctorId")
    List<AppointmentEntity> findAppointmentByDoctor(@Param("doctorId") String doctorId);

    @Query("SELECT a FROM appointments a WHERE a.patient.id = :patientId")
    List<AppointmentEntity> findAppointmentByPatient(@Param("patientId") String patientId);



}
