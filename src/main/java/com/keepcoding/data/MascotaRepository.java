package com.keepcoding.data;

import com.keepcoding.model.Mascota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findTop20ByOrderByFechaNacAsc();

    List<Mascota> findByNombreContainingIgnoreCaseOrRazaContainingIgnoreCase(String nombre, String raza);

	List<Mascota> findByPropietarioId(Long id);

	List<Mascota> findByPropietarioIsNull();
}
