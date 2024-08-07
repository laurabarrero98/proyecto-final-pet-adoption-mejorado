package com.keepcoding.data;

import com.keepcoding.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    
    List<Propietario> findAllByOrderByPrimerApellido();

    @Query("SELECT DISTINCT p FROM Propietario p " +
           "LEFT JOIN p.mascotas m " +
           "WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.primerApellido) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.segundoApellido) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Propietario> findDistinctBySearch(@Param("search") String search);
}
