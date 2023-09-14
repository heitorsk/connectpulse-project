package com.gema.connectpulse.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.sql.Date;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean is_active;
    private Date date_created;
    private Date date_updated;
    private String email;
    private String password;

}
