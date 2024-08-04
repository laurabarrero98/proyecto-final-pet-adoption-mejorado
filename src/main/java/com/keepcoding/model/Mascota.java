package com.keepcoding.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Mascota {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String nombre;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNac;
    
    private String raza;
    
    private int peso;
    
    private boolean tieneChip;
    
    private String urlFoto;
    
    @ManyToOne
    @JsonIgnore
    private Propietario propietario;
    
    @Override
    public String toString() {
        return "Mascota{id=" + id + ", nombre='" + nombre + "'}";
    }

}
