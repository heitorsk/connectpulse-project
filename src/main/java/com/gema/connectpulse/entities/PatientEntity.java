package com.gema.connectpulse.entities;

import com.gema.connectpulse.typeEnum.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "patients")
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
}
