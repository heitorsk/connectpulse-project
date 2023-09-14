package com.gema.connectpulse.controllers;


import com.gema.connectpulse.entities.PhoneEntity;
import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.PhoneRepository;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phone")
public class PhoneController {
    @Autowired
    private PhoneRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<PhoneEntity> getAllPhone(){
        List<PhoneEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public PhoneEntity getPhoneById(@PathVariable String id){
        PhoneEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PhoneEntity>> getPhonesByUser(@PathVariable String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<PhoneEntity> phones = repository.findPhoneByUser(user);

        return ResponseEntity.ok(phones);
    }


    @PostMapping
    public ResponseEntity<String> registerPhone(@RequestBody PhoneEntity phone) {
        String userId = phone.getUser().getId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário não encontrado ou inválido.");
        }

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        boolean phoneExists = repository.existsByUserAndNumber(user, phone.getNumber());

        if (phoneExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este número de telefone já está cadastrado para este usuário.");
        }

        phone.setUser(user);
        repository.save(phone);
        return ResponseEntity.status(HttpStatus.CREATED).body("Telefone cadastrado!");
    }

    @PutMapping
    public ResponseEntity<String> updatePhone(@RequestBody PhoneEntity phone){
        Optional<PhoneEntity> optionalPhone = repository.findById(phone.getId());

        if(optionalPhone.isPresent()) {
            PhoneEntity modifiedPhone = optionalPhone.get();
            modifiedPhone.setDdd(phone.getDdd());
            modifiedPhone.setDdi(phone.getDdi());
            modifiedPhone.setNumber(phone.getNumber());
            repository.save(modifiedPhone);
            return ResponseEntity.status(HttpStatus.CREATED).body("Telefone alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar telefone!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhoneByid(@PathVariable String id) {
        Optional<PhoneEntity> optionalPhone = repository.findById(id);

        if (optionalPhone.isPresent()) {
            PhoneEntity result = optionalPhone.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Telefone removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }

}
