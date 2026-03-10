package org.example.smartmallparking.controller;

import org.example.smartmallparking.dto.RezervareDTO;
import org.example.smartmallparking.model.LocParcare;
import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.model.Rezervare;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.service.ParcareActivaService;
import org.example.smartmallparking.service.RezervareService;
import org.example.smartmallparking.service.SoferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rezervari")
public class RezervareController {

    private final RezervareService rezervareService;
    private final SoferService soferService;
    private final ParcareActivaService parcareActivaService;

    public RezervareController(RezervareService rezervareService, SoferService soferService, ParcareActivaService parcareActivaService) {
        this.rezervareService = rezervareService;
        this.soferService = soferService;
        this.parcareActivaService = parcareActivaService;
    }

    // --- CRUD standard ---
    @GetMapping
    public List<Rezervare> getAll() {
        return rezervareService.getAll();
    }

    @GetMapping("/{id}")
    public Rezervare getById(@PathVariable Long id) {
        return rezervareService.getById(id);
    }

    @PostMapping
    public Rezervare create(@RequestBody Rezervare r) {
        return rezervareService.save(r);
    }

    @PutMapping("/{id}")
    public Rezervare update(@PathVariable Long id, @RequestBody Rezervare r) {
        r.setId(id);
        return rezervareService.save(r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rezervareService.delete(id);
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

//    @PostMapping("/rezervare")
//    public ResponseEntity<Sofer> rezervaLoc(@RequestBody Map<String, Object> payload) {
//        Long soferId = Long.valueOf(payload.get("soferId").toString());
//        Sofer sofer = soferService.getById(soferId);
//        if (sofer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        return ResponseEntity.ok(sofer);
//    }


    @PostMapping("/rezervare")
    public ResponseEntity<Rezervare> rezervaLoc(@RequestBody Map<String, Object> payload) {
        try {
            // 1️⃣ Obținem soferul
            Long soferId = Long.valueOf(payload.get("soferId").toString());
            Sofer sofer = soferService.getById(soferId);
            if (sofer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            // 2️⃣ Obținem un loc de parcare liber (simplificat)

            if (rezervareService.hasActiveRezervare(sofer)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            Optional<LocParcare> locOpt = rezervareService.rezervaLoc(sofer);
            if (locOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            // 3️⃣ Creăm obiectul Rezervare
            Rezervare r = new Rezervare();
            r.setSofer(sofer);
            r.setLocParcare(locOpt.get());
            r.setStatus("rezervat");
            r.setRezervareStart(LocalDateTime.now());

            // 4️⃣ Salvăm rezervarea în baza de date
            Rezervare saved = rezervareService.save(r);
            //saved.getLocParcare().getNumarLoc();

            // 5️⃣ Returnăm rezervarea complet populată
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/parcare-activa/{soferId}")
    public ResponseEntity<?> getParcareActiva(@PathVariable Long soferId) {
        Sofer sofer = soferService.getById(soferId);
        if (sofer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soferul nu exista");
        }


        Optional<ParcareActiva> parcareOpt = parcareActivaService.getParcareActiva(sofer);
        if (parcareOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista parcare activa");
        }

        // returnează doar datele locului
        LocParcare loc = parcareOpt.get().getLocParcare();
        return ResponseEntity.ok(loc);
    }

}
