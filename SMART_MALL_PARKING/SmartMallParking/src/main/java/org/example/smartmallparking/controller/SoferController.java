package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.service.SoferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.smartmallparking.repository.SoferRepository;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/soferi")
public class SoferController {

    private final SoferService soferService;
    private final SoferRepository soferRepository;

    public SoferController(SoferService soferService, SoferRepository soferRepository) {
        this.soferService = soferService;
        this.soferRepository = soferRepository;
    }

    @GetMapping
    public List<Sofer> getAll() {
        return soferService.getAll();
    }

    @GetMapping("/{id}")
    public Sofer getById(@PathVariable Long id) {
        return soferService.getById(id);
    }

    @PostMapping
    public Sofer create(@RequestBody Sofer s) {
        return soferService.save(s);
    }

    @PutMapping("/{id}")
    public Sofer update(@PathVariable Long id, @RequestBody Sofer s) {
        s.setId(id);
        return soferService.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        soferService.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String parola = body.get("parola");

        Sofer s = soferRepository.findByEmail(email);
        if (s == null || !s.getParola().equals(parola)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Email sau parola incorecta"));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "id", s.getId(),
                "nume", s.getNume(),
                "email", s.getEmail(),
                "nrInmatriculare", s.getNrInmatriculare()
        ));
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Sofer s) {
        if (soferRepository.existsByEmail(s.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Email deja existent"));
        }

        Sofer saved = soferRepository.save(s);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "id", saved.getId(),  // returnam si id-ul
                "nume", saved.getNume(),
                "email", saved.getEmail(),
                "nrInmatriculare", saved.getNrInmatriculare()
        ));
    }

    @PutMapping("/{soferId}/inmatriculare")
    public ResponseEntity<?> schimbaInmatriculare(
            @PathVariable Long soferId,
            @RequestBody Map<String, String> body) {

        String nouNr = body.get("nrInmatriculare");

        Sofer sofer = soferRepository.findById(soferId).orElse(null);
        if (sofer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Sofer inexistent"));
        }

        sofer.setNrInmatriculare(nouNr);
        soferRepository.save(sofer);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "nrInmatriculare", sofer.getNrInmatriculare()
        ));
    }

    @PutMapping("/{id}/parola")
    public ResponseEntity<?> schimbaParola(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String parolaNoua = body.get("parola");

        if (parolaNoua == null || parolaNoua.length() < 6) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Parola prea scurta"));
        }

        Sofer sofer = soferRepository.findById(id).orElse(null);
        if (sofer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Sofer inexistent"));
        }

        sofer.setParola(parolaNoua); // (simplu, fără hash – OK pt proiect)
        soferRepository.save(sofer);

        return ResponseEntity.ok(Map.of("success", true));
    }



}
