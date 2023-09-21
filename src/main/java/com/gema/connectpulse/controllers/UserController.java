package com.gema.connectpulse.controllers;


import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Component
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<UserEntity> getAllUser(){
        List<UserEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable String id){
        UserEntity result = repository.findById(id).get();
        return result;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (user.getDate_created() == null) {
            user.setDate_created(currentDate);
            user.setDate_updated(currentDate);
            user.setIs_active(true);
        } else {
            user.setDate_updated(new Date(System.currentTimeMillis()));
        }

        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserEntity user){
        Optional<UserEntity> optionalUser = repository.findById(user.getId());
        Date currentDate = new Date(System.currentTimeMillis());

        if(optionalUser.isPresent()) {
            UserEntity modifiedUser = optionalUser.get();
            modifiedUser.setPassword(user.getPassword());
            modifiedUser.setDate_updated(currentDate);
            modifiedUser.setIs_active(user.getIs_active());
            repository.save(modifiedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário alterado com sucesso!");
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserByid(@PathVariable String id) {
        Optional<UserEntity> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity result = optionalUser.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }

}
