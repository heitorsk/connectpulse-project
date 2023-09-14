package com.gema.connectpulse.controllers;

import com.gema.connectpulse.entities.DocumentEntity;
import com.gema.connectpulse.entities.UserEntity;
import com.gema.connectpulse.repositories.DocumentRepository;
import com.gema.connectpulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<DocumentEntity> getAllDocument(){
        List<DocumentEntity> result = repository.findAll();
        return result;
    }

    @GetMapping("/{id}")
    public DocumentEntity getDocumentById(@PathVariable String id){
        DocumentEntity result = repository.findById(id).get();
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentEntity>> getDocumentByUser(@PathVariable String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<DocumentEntity> document = repository.findDocumentByUser(user);

        return ResponseEntity.ok(document);
    }

    @PostMapping
    public ResponseEntity<String> registerDocument(@RequestBody DocumentEntity document) {
        String userId = document.getUser().getId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do usuário não encontrado ou inválido.");
        }

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        document.setUser(user);
        repository.save(document);

        return ResponseEntity.status(HttpStatus.CREATED).body("Documento cadastrado!");
    }

    @PutMapping
    public ResponseEntity<String> updateDocument(@RequestBody DocumentEntity document){
        Optional<DocumentEntity> optionalDocument = repository.findById(document.getId());

        if(optionalDocument.isPresent()) {
            DocumentEntity modifiedDocument = optionalDocument.get();
            modifiedDocument.setBirthdate(document.getBirthdate());
            modifiedDocument.setNumber_cpf(document.getNumber_cpf());
            modifiedDocument.setNumber_rg(document.getNumber_rg());
            modifiedDocument.setUf(document.getUf());
            repository.save(modifiedDocument);
            return ResponseEntity.status(HttpStatus.CREATED).body("Documento alterado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar documento!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumentByid(@PathVariable String id) {
        Optional<DocumentEntity> optionalDocument = repository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentEntity result = optionalDocument.get();
            System.out.println("Esse foi o id encontrado e deletado: "+result.getId());
            repository.delete(result);
            return ResponseEntity.status(HttpStatus.CREATED).body("Documento removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O id informado não está presente no banco de dados!");
    }
}
