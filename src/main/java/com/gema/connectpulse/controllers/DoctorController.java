package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.DoctorEntity;
import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.DoctorRepository;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<DoctorEntity> getAllDoctor(){
        List<DoctorEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctorById(@PathVariable String id){
        DoctorEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DoctorEntity>> getDoctorByUser(@PathVariable String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<DoctorEntity> doctor = repository.findDoctorByUser(user);

        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorEntity doctor) {
        String userId = doctor.getUser().getId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário não encontrado ou inválido.");
        }

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        doctor.setUser(user);
        repository.save(doctor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Médico cadastrado!");
    }

    @PutMapping
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorEntity doctor){
        Optional<DoctorEntity> optionalDoctor = repository.findById(doctor.getId());

        if(optionalDoctor.isPresent()) {
            DoctorEntity modifiedDoctor = optionalDoctor.get();
            modifiedDoctor.setName(doctor.getName());
            modifiedDoctor.setSpeciality(doctor.getSpeciality());
            repository.save(modifiedDoctor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Médico alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar médico!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorByid(@PathVariable String id) {
        Optional<DoctorEntity> optionalDoctor = repository.findById(id);

        if (optionalDoctor.isPresent()) {
            DoctorEntity result = optionalDoctor.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Médico removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }
}
