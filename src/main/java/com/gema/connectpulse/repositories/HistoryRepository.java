package com.gema.connectpulse.repositories;


import com.gema.connectpulse.entities.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, String> {

    @Query("SELECT h FROM histories h WHERE h.appointment_id.id = :appointmentId")
    List<HistoryEntity> findHistoryByAppointment(@Param("appointmentId") String appointmentId);
}
