package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.PatientEntity;
import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.PatientRepository;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<PatientEntity> getAllPatient(){
        List<PatientEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public PatientEntity getPatientById(@PathVariable String id){
        PatientEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PatientEntity>> getPatientByUser(@PathVariable String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<PatientEntity> patient = repository.findPatientByUser(user);

        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody PatientEntity patient) {
        String userId = patient.getUser().getId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário não encontrado ou inválido.");
        }

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        patient.setUser(user);
        repository.save(patient);

        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente cadastrado!");
    }

    @PutMapping
    public ResponseEntity<String> updatePatient(@RequestBody PatientEntity patient){
        Optional<PatientEntity> optionalPatient = repository.findById(patient.getId());

        if(optionalPatient.isPresent()) {
            PatientEntity modifiedPatient = optionalPatient.get();
            modifiedPatient.setName(patient.getName());
            modifiedPatient.setGender(patient.getGender());
            repository.save(modifiedPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar paciente!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientByid(@PathVariable String id) {
        Optional<PatientEntity> optionalPatient = repository.findById(id);

        if (optionalPatient.isPresent()) {
            PatientEntity result = optionalPatient.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }

}
