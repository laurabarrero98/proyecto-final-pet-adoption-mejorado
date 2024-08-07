package com.keepcoding.data;

import com.keepcoding.model.Mascota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findTop20ByOrderByFechaNacAsc();

    // Búsqueda por nombre, raza y nombre de propietario (ignorando mayúsculas/minúsculas)
    @Query("SELECT m FROM Mascota m " +
           "LEFT JOIN m.propietario p " +
           "WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(m.raza) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Mascota> findByNombreOrRazaOrPropietarioNombre(@Param("search") String search);


	List<Mascota> findByPropietarioId(Long id);

	List<Mascota> findByPropietarioIsNull();

	List<Mascota> findByNombreContainingOrRazaContaining(String search, String search2);
}
