package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.repository.ParcareActivaRepository;
import org.example.smartmallparking.service.ParcareActivaService;
import org.example.smartmallparking.service.SoferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcari-active")
public class ParcareActivaController {

    private final ParcareActivaService parcareActivaService;
    private final ParcareActivaRepository parcareActivaRepository;
    private final SoferService soferService;

    public ParcareActivaController(ParcareActivaService parcareActivaService, ParcareActivaRepository parcareActivaRepository, SoferService soferService) {
        this.parcareActivaService = parcareActivaService;
        this.parcareActivaRepository = parcareActivaRepository;
        this.soferService = soferService;
    }

    @GetMapping
    public List<ParcareActiva> getAll() {
        return parcareActivaService.getAll();
    }

    @GetMapping("/{id}")
    public ParcareActiva getById(@PathVariable Long id) {
        return parcareActivaService.getById(id);
    }

    @PostMapping
    public ParcareActiva create(@RequestBody ParcareActiva p) {
        return parcareActivaService.save(p);
    }

    @PutMapping("/{id}")
    public ParcareActiva update(@PathVariable Long id, @RequestBody ParcareActiva p) {
        p.setId(id);
        return parcareActivaService.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        parcareActivaService.delete(id);
    }

    @GetMapping("/parcare-activa/{soferId}")
    public ResponseEntity<ParcareActiva> getParcareActiva(@PathVariable Long soferId) {
        Sofer sofer = soferService.getById(soferId); // aici trebuie sa returneze obiect complet din DB
        if (sofer == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<ParcareActiva> parcareOpt = parcareActivaService.getParcareActiva(sofer);
        return parcareOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
