package com.gema.connectpulse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "phones")
@Table(name = "phones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    private String number;
    private Integer ddd;
    private Integer ddi;


}
