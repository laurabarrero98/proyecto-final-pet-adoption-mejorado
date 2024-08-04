package com.keepcoding.web;

import com.keepcoding.model.Mascota;
import com.keepcoding.model.Propietario;
import com.keepcoding.data.MascotaRepository;
import com.keepcoding.data.PropietarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MascotaController {

	@Autowired
	private MascotaRepository mascotaRepository;

	@Autowired
	private PropietarioRepository propietarioRepository;

	@GetMapping("/")
	public String listarMascotas(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "sort", defaultValue = "id") String sort, Model model) {
		List<Mascota> mascotas;
		if (search != null && !search.trim().isEmpty()) {
			mascotas = mascotaRepository.findByNombreContainingIgnoreCaseOrRazaContainingIgnoreCase(search, search);
		} else {
			mascotas = mascotaRepository.findAll(Sort.by(sort));
		}

		model.addAttribute("mascotas", mascotas);
		model.addAttribute("search", search);

		return "index";
	}

	@GetMapping("/mascotas/crear")
	public String mostrarFormularioCreacion(Model model) {
		model.addAttribute("mascota", new Mascota());
		model.addAttribute("propietarios", propietarioRepository.findAll());
		return "crear";
	}

	@PostMapping("/mascotas/crear")
	public String crearMascota(@ModelAttribute Mascota mascota,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "propietarioId", required = false) Long propietarioId) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaNac = null;
		if (mascota.getFechaNac() != null) {
			try {
				fechaNac = dateFormat.parse(dateFormat.format(mascota.getFechaNac()));
			} catch (Exception e) {
				return "error"; // No implementado
			}
		}
		mascota.setFechaNac(fechaNac);

		if (propietarioId != null && propietarioId > 0) {
			Propietario propietario = propietarioRepository.findById(propietarioId).orElse(null);
			if (propietario != null) {
				mascota.setPropietario(propietario);
			}
		}

		mascotaRepository.save(mascota);

		if (file != null && !file.isEmpty()) {
			String fileName = "mascota" + mascota.getId() + ".jpg";
			File imageFile = new File("src/main/resources/static/images/" + fileName);
			file.transferTo(imageFile);
			mascota.setUrlFoto("/images/" + fileName);
		} else {
			mascota.setUrlFoto("/images/default.jpg");
		}

		mascotaRepository.save(mascota);

		return "redirect:/";
	}

	@GetMapping("/mascotas/{id}")
	public String verMascota(@PathVariable Long id, Model model) {
		Mascota mascota = mascotaRepository.findById(id).orElse(null);
		if (mascota != null) {
			model.addAttribute("mascota", mascota);
			return "ver";
		}
		return "redirect:/";
	}

	@GetMapping("/mascotas/mas-jovenes")
	public String mostrarMascotasMasJovenes(Model model) {
		List<Mascota> mascotas = mascotaRepository.findTop20ByOrderByFechaNacAsc();
		model.addAttribute("mascotas", mascotas);
		return "index";
	}

	@GetMapping("/mascotas/eliminar/{id}")
	public String confirmarEliminarMascota(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "delete";
	}

	@PostMapping("/mascotas/eliminar/{id}")
	public String eliminarMascotaConfirmado(@PathVariable Long id) {
		mascotaRepository.deleteById(id);
		return "redirect:/";
	}
}
