package com.gema.connectpulse.entities;

import com.gema.connectpulse.typeEnum.UfEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "documents")
@Table(name = "documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    private String number_cpf;
    private String number_rg;
    private Date birthdate;

    @Enumerated(EnumType.STRING)
    private UfEnum uf;
}
