package com.keepcoding.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
@Entity
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @OneToMany(mappedBy = "propietario")
    @JsonIgnore    
    private Set<Mascota> mascotas;
    
    @Override
    public String toString() {
        return "Propietario{id=" + id + ", nombre='" + nombre + "'}";
    }

}
