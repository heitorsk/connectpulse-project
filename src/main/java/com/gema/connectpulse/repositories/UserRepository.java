package com.gema.connectpulse.repositories;

import com.gema.connectpulse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
