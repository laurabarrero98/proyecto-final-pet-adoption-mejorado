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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping
    public String listarMascotas(@RequestParam(name = "search", required = false) String search,
                                 @RequestParam(name = "sort", defaultValue = "id") String sort,
                                 Model model) {
        List<Mascota> mascotas;
        if (search != null && !search.isEmpty()) {
            mascotas = mascotaRepository.findByNombreOrRazaOrPropietarioNombre(search);
        } else {
            mascotas = mascotaRepository.findAll();
        }
        if ("nombre".equals(sort)) {
            mascotas.sort((m1, m2) -> m1.getNombre().compareTo(m2.getNombre()));
        } else {
            mascotas.sort((m1, m2) -> m1.getId().compareTo(m2.getId()));
        }
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietarios", propietarioRepository.findAll());
        return "crear";
    }

    @PostMapping("/crear")
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

        // Guardar la mascota en la base de datos y obtener la mascota guardada con ID
        Mascota savedMascota = mascotaRepository.save(mascota);

        if (file != null && !file.isEmpty()) {
            String fileName = "mascota" + savedMascota.getId() + ".jpg";
            String tempDir = System.getProperty("java.io.tmpdir");
            File imageFile = new File(tempDir, fileName);
            file.transferTo(imageFile);
            mascota.setUrlFoto("/temp-images/" + fileName);
        } else {
            mascota.setUrlFoto("/images/default.jpg");
        }

        mascotaRepository.save(mascota);

        return "redirect:/mascotas";
    }

    @GetMapping("/{id}")
    public String verMascota(@PathVariable Long id,
                              @RequestParam(value = "redirectTo", required = false) String redirectTo,
                              Model model) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            model.addAttribute("mascota", mascota);
            model.addAttribute("redirectTo", redirectTo); // Añade el parámetro al modelo
            return "ver";
        }
        return "redirect:" + (redirectTo != null ? redirectTo : "/mascotas"); // Redirige a la página anterior o a la lista de mascotas
    }
    
    @GetMapping("/mas-jovenes")
    public String verMascotasJovenes(Model model) {
        List<Mascota> mascotas = mascotaRepository.findTop20ByOrderByFechaNacAsc();
        model.addAttribute("mascotas", mascotas);
        return "index";
    }

    @GetMapping("/{id}/asignar-propietario")
    public String mostrarFormularioAsignarPropietario(@PathVariable Long id, Model model) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            
            List<Propietario> propietarios = propietarioRepository.findAll(Sort.by(Sort.Order.asc("primerApellido"), Sort.Order.asc("nombre")));
            model.addAttribute("mascota", mascota);
            model.addAttribute("propietarios", propietarios);
            return "asignar_propietario";
        }
        return "redirect:/mascotas";
    }


    @PostMapping("/{id}/asignar-propietario")
    public String asignarPropietario(@PathVariable Long id, 
                                      @RequestParam(value = "propietarioId", required = false) Long propietarioId) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            
            // Primero eliminamos la mascota del antiguo propietario si es necesario
            if (mascota.getPropietario() != null) {
                Propietario antiguoPropietario = mascota.getPropietario();
                antiguoPropietario.getMascotas().remove(mascota);
                propietarioRepository.save(antiguoPropietario);
            }

            // Asignamos el nuevo propietario
            if (propietarioId != null && propietarioId > 0) {
                Optional<Propietario> propietarioOpt = propietarioRepository.findById(propietarioId);
                if (propietarioOpt.isPresent()) {
                    Propietario propietario = propietarioOpt.get();
                    mascota.setPropietario(propietario);
                    if (!propietario.getMascotas().contains(mascota)) {
                        propietario.getMascotas().add(mascota);
                        propietarioRepository.save(propietario);
                    }
                }
            } else {
                // Sin propietario seleccionado
                mascota.setPropietario(null);
            }

            mascotaRepository.save(mascota);
        }
        return "redirect:/mascotas/" + id;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        mascotaRepository.deleteById(id);
        return "redirect:/mascotas";
    }
}
