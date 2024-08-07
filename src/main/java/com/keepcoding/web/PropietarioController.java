package com.keepcoding.web;

import com.keepcoding.model.Mascota;
import com.keepcoding.model.Propietario;
import com.keepcoding.data.MascotaRepository;
import com.keepcoding.data.PropietarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

	@Autowired
	private PropietarioRepository propietarioRepository;

	@Autowired
	private MascotaRepository mascotaRepository;

	@GetMapping({"", "/"})
	public String listarPropietarios(@RequestParam(name = "search", required = false) String search, Model model) {
	    List<Propietario> propietarios;
	    if (search != null && !search.isEmpty()) {
	        propietarios = propietarioRepository.findDistinctBySearch(search);
	    } else {
	        propietarios = propietarioRepository.findAll();
	    }
	    model.addAttribute("propietarios", propietarios);
	    model.addAttribute("search", search);
	    return "listar_propietarios";
	}

	
	@GetMapping("/{id}")
    public String verPropietario(@PathVariable Long id, Model model) {
        Propietario propietario = propietarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid propietario ID: " + id));
        model.addAttribute("propietario", propietario);
        return "ver_propietario";
    }

	@GetMapping("/crear")
	public String mostrarFormularioCreacion(Model model) {
		List<Mascota> mascotas = mascotaRepository.findByPropietarioIsNull();
		model.addAttribute("propietario", new Propietario());
		model.addAttribute("mascotas", mascotas);
		return "crear_propietario";
	}

	@PostMapping("/crear")
	public String crearPropietario(@ModelAttribute Propietario propietario,
	                                @RequestParam(value = "mascotasIds", required = false) List<Long> mascotasIds) {
	    propietarioRepository.save(propietario);

	    // Asigna las mascotas seleccionadas al propietario
	    if (mascotasIds != null) {
	        List<Mascota> mascotas = mascotaRepository.findAllById(mascotasIds);
	        for (Mascota mascota : mascotas) {
	            mascota.setPropietario(propietario);
	            mascotaRepository.save(mascota);
	        }
	    }

	    return "redirect:/propietarios/";
	}
}
