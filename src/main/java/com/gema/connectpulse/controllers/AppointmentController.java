package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.AppointmentEntity;
import com.gema.connectpulse.entities.DoctorEntity;
import com.gema.connectpulse.entities.PatientEntity;
import com.gema.connectpulse.repositories.AppointmentRepository;
import com.gema.connectpulse.repositories.DoctorRepository;
import com.gema.connectpulse.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<AppointmentEntity> getAllAppointment(){
        List<AppointmentEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public AppointmentEntity getAppointmentById(@PathVariable String id){
        AppointmentEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentByDoctor(@PathVariable String doctorId) {
        DoctorEntity doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<AppointmentEntity> appointment = repository.findAppointmentByDoctor(doctor.getId());
        //System.out.println(doctor.getId().toString());

        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentByPatient(@PathVariable String patientId) {
        PatientEntity patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<AppointmentEntity> appointment = repository.findAppointmentByPatient(patient.getId());

        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<String> registerAppointment(@RequestBody AppointmentEntity appointment) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (appointment.getDate_created()== null) {
            appointment.setDate_created(currentDate);
            appointment.setDate_updated(currentDate);
        } else {
            appointment.setDate_updated(new Date(System.currentTimeMillis()));
        }


        String doctorId = appointment.getDoctor().getId();
        String patientId = appointment.getPatient().getId();

        if (doctorId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do médico não encontrado ou inválido.");
        }

        if (patientId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do paciente não encontrado ou inválido.");
        }

        DoctorEntity doctorFind = doctorRepository.findById(doctorId).orElse(null);

        PatientEntity patientFind = patientRepository.findById(patientId).orElse(null);

        if (doctorFind == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Médico não encontrado.");
        }

        if (patientFind == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente não encontrado.");
        }


        appointment.setDoctor(doctorFind);
        appointment.setPatient(patientFind);
        repository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Consulta cadastrada!");
    }


    @PutMapping
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentEntity appointment){
        Optional<AppointmentEntity> optionalAppointment = repository.findById(appointment.getId());
        Date currentDate = new Date(System.currentTimeMillis());

        if(optionalAppointment.isPresent()) {
            AppointmentEntity modifiedAppointment = optionalAppointment.get();
            modifiedAppointment.setPatient_atended(appointment.getPatient_atended());
            modifiedAppointment.setDoctor(appointment.getDoctor());
            modifiedAppointment.setPatient(appointment.getPatient());
            modifiedAppointment.setDate_appointment(appointment.getDate_appointment());
            modifiedAppointment.setDate_updated(currentDate);
            repository.save(modifiedAppointment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta alterada com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar consulta!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentByid(@PathVariable String id) {
        Optional<AppointmentEntity> optionalAppointment = repository.findById(id);

        if (optionalAppointment.isPresent()) {
            AppointmentEntity result = optionalAppointment.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta removida com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }
}
