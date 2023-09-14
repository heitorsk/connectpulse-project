package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.AddressEntity;
import com.gema.connectpulse.entities.DocumentEntity;
import com.gema.connectpulse.entities.PhoneEntity;
import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.AddressRespository;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressRespository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<AddressEntity> getAllAddress(){
        List<AddressEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public AddressEntity getAddressById(@PathVariable String id){
        AddressEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentEntity>> getAddressByUser(@PathVariable String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<DocumentEntity> address = repository.findAddressByUser(user);

        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<String> registerAddress(@RequestBody AddressEntity address) {
        String userId = address.getUser().getId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário não encontrado ou inválido.");
        }

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }


        address.setUser(user);
        repository.save(address);

        return ResponseEntity.status(HttpStatus.CREATED).body("Endereço cadastrado!");
    }


    @PutMapping
    public ResponseEntity<String> updateAddress(@RequestBody AddressEntity address){
        Optional<AddressEntity> optionalAddress = repository.findById(address.getId());

        if(optionalAddress.isPresent()) {
            AddressEntity modifiedAddress = optionalAddress.get();
            modifiedAddress.setAddress(address.getAddress());
            modifiedAddress.setCep(address.getCep());
            modifiedAddress.setCity(address.getCity());
            modifiedAddress.setComplement(address.getComplement());
            modifiedAddress.setCountry(address.getCountry());
            modifiedAddress.setNeighboarhood(address.getNeighboarhood());
            modifiedAddress.setNumber(address.getNumber());
            modifiedAddress.setState(address.getState());
            repository.save(modifiedAddress);
            return ResponseEntity.status(HttpStatus.CREATED).body("Endereço alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar endereço!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressByid(@PathVariable String id) {
        Optional<AddressEntity> optionalAddress = repository.findById(id);

        if (optionalAddress.isPresent()) {
            AddressEntity result = optionalAddress.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Endereço removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }

}
