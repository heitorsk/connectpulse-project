package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.PhoneEntity;
import com.gema.connectpulse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepository extends JpaRepository<PhoneEntity, String> {

    List<PhoneEntity> findPhoneByUser(UserEntity user);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM phones p WHERE p.user = :user AND p.number = :number) THEN true ELSE false END")
    boolean existsByUserAndNumber(@Param("user") UserEntity user, @Param("number") String number);

}
