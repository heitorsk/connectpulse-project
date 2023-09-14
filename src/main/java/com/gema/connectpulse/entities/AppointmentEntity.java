package com.gema.connectpulse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "appointments")
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "fk_doctor_id"))
    private DoctorEntity doctor;

    @OneToOne
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_patient_id"))
    private PatientEntity patient;

    private Date date_created;
    private Date date_updated;
    private Date date_appointment;
    private Boolean patient_atended;

}
