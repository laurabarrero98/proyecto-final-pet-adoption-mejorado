package com.keepcoding.web.api;

import com.keepcoding.model.Mascota;
import com.keepcoding.data.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaControllerApi {

	@Autowired
	private MascotaRepository mascotaRepository;

	@GetMapping
	public ResponseEntity<List<Mascota>> getAllMascotas(
			@RequestParam(value = "sort", defaultValue = "id") String sort) {
		List<Mascota> mascotas = mascotaRepository.findAll(Sort.by(sort));
		return new ResponseEntity<>(mascotas, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
		Mascota mascota = mascotaRepository.findById(id).orElse(null);
		return mascota != null ? new ResponseEntity<>(mascota, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
		Mascota newMascota = mascotaRepository.save(mascota);
		return new ResponseEntity<>(newMascota, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
		if (!mascotaRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		mascota.setId(id);
		Mascota updatedMascota = mascotaRepository.save(mascota);
		return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
		if (!mascotaRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		mascotaRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/mas-jovenes")
	public ResponseEntity<List<Mascota>> get20YoungestMascotas() {
		List<Mascota> mascotas = mascotaRepository.findTop20ByOrderByFechaNacAsc();
		return new ResponseEntity<>(mascotas, HttpStatus.OK);
	}
}
