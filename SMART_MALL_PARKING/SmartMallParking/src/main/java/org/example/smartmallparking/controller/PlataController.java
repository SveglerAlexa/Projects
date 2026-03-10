package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.model.Plata;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.service.ParcareActivaService;
import org.example.smartmallparking.service.PlataService;
import org.example.smartmallparking.service.RezervareService;
import org.example.smartmallparking.service.SoferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plati")
public class PlataController {

    private final PlataService plataService;
    private final ParcareActivaService parcareActivaService;
    private final RezervareService rezervareService;
    private final SoferService soferService;

    public PlataController(
            PlataService plataService,
            ParcareActivaService parcareActivaService,
            RezervareService rezervareService,
            SoferService soferService
    ) {
        this.plataService = plataService;
        this.parcareActivaService = parcareActivaService;
        this.rezervareService = rezervareService;
        this.soferService = soferService;
    }

    @GetMapping
    public List<Plata> getAll() {
        return plataService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plata> getById(@PathVariable Long id) {
        Plata plata = plataService.getById(id);
        return plata != null ? ResponseEntity.ok(plata) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Plata create(@RequestBody Plata p) {
        return plataService.save(p);
    }

    // UPDATE CORECT (fără setId)
    @PutMapping("/{id}")
    public ResponseEntity<Plata> update(@PathVariable Long id, @RequestBody Plata p) {
        Optional<Plata> opt = plataService.getByIdOptional(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Plata plata = opt.get();
        plata.setDataPlata(p.getDataPlata());
        plata.setSuma(p.getSuma());
        plata.setSofer(p.getSofer());
        plata.setLocParcareId(p.getLocParcareId());

        return ResponseEntity.ok(plataService.save(plata));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        plataService.delete(id);
    }

    // PLATĂ
    @PostMapping("/plateste")
    public ResponseEntity<?> plateste(
            @RequestParam Long soferId,
            @RequestParam double suma
    ) {
        ParcareActiva parcareActiva = parcareActivaService
                .getParcareActivaById(soferId)
                .orElseThrow(() -> new RuntimeException("Nu există parcare activă"));

        Sofer sofer = soferService.getById(soferId);
        if (sofer == null) {
            return ResponseEntity.badRequest().body("Șofer inexistent");
        }

        Long locParcareId = parcareActiva.getLocParcare().getId();

        plataService.inregistreazaPlata(sofer, suma, locParcareId);
        rezervareService.elibereazaLoc(soferId);

        return ResponseEntity.ok("Plata efectuată cu succes");
    }
}
