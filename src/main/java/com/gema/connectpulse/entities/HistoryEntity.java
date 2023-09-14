package com.gema.connectpulse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "histories")
@Table(name = "histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "appointment_id", foreignKey = @ForeignKey(name = "fk_appointment_id"))
    private AppointmentEntity appointment_id;
    private String observation;
    private Date date_time;

}
