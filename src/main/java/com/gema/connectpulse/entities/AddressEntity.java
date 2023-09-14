package com.gema.connectpulse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    private String cep;
    private String address;
    private String number;
    private String complement;
    private String neighboarhood;
    private String city;
    private String state;
    private String country;
}
