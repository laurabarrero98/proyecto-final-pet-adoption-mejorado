package com.keepcoding.web.api;

import com.keepcoding.model.Propietario;
import com.keepcoding.data.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioControllerApi {

    @Autowired
    private PropietarioRepository propietarioRepository;

    @GetMapping
    public ResponseEntity<List<Propietario>> getAllPropietarios() {
        List<Propietario> propietarios = propietarioRepository.findAll();
        return ResponseEntity.ok(propietarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propietario> getPropietarioById(@PathVariable Long id) {
        Propietario propietario = propietarioRepository.findById(id).orElse(null);
        return propietario != null ? ResponseEntity.ok(propietario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Propietario> createPropietario(@RequestBody Propietario propietario) {
        Propietario newPropietario = propietarioRepository.save(propietario);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPropietario);
    }
}
