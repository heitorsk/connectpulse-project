package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.AppointmentEntity;
import com.gema.connectpulse.entities.DoctorEntity;
import com.gema.connectpulse.entities.HistoryEntity;
import com.gema.connectpulse.entities.PatientEntity;
import com.gema.connectpulse.repositories.AppointmentRepository;
import com.gema.connectpulse.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryRepository repository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping
    public List<HistoryEntity> getAllHistory(){
        List<HistoryEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public HistoryEntity getHistoryById(@PathVariable String id){
        HistoryEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<HistoryEntity>> getHistoryByAppointment(@PathVariable String appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<HistoryEntity> history = repository.findHistoryByAppointment(appointment.getId());

        return ResponseEntity.ok(history);
    }

    @PostMapping
    public ResponseEntity<String> registerHistory(@RequestBody HistoryEntity history) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (history.getDate_time()== null) {
            history.setDate_time(currentDate);
        }

        String appointmentId = history.getAppointment_id().getId();

        if (appointmentId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do da consulta não encontrado ou inválido.");
        }


        AppointmentEntity appointmentFind = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointmentFind == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Consulta não foi encontrada.");
        }

        history.setAppointment_id(appointmentFind);
        repository.save(history);

        return ResponseEntity.status(HttpStatus.CREATED).body("Histórico cadastrado!");
    }

    @PutMapping
    public ResponseEntity<String> updateHistory(@RequestBody HistoryEntity history){
        Optional<HistoryEntity> optionalHistory= repository.findById(history.getId());

        if(optionalHistory.isPresent()) {
            HistoryEntity modifiedHistory = optionalHistory.get();
            modifiedHistory.setAppointment_id(history.getAppointment_id());
            modifiedHistory.setObservation(history.getObservation());
            repository.save(modifiedHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Histórico alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar histórico!");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistoryByid(@PathVariable String id) {
        Optional<HistoryEntity> optionalHistory = repository.findById(id);

        if (optionalHistory.isPresent()) {
            HistoryEntity result = optionalHistory.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Histórico removida com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }
}
