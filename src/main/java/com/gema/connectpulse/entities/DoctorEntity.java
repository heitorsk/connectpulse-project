package com.gema.connectpulse.entities;

import com.gema.connectpulse.typeEnum.SpecialityEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "doctors")
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    private String name;
    @Enumerated(EnumType.STRING)
    private SpecialityEnum speciality;
}
