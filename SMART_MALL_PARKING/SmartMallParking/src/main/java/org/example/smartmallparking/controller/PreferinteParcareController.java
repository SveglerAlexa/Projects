package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.PreferinteParcare;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.service.PreferinteParcareService;
import org.example.smartmallparking.repository.SoferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preferinte")
public class PreferinteParcareController {

    private final PreferinteParcareService preferinteService;
    private final SoferRepository soferRepository;

    public PreferinteParcareController(PreferinteParcareService preferinteService, SoferRepository soferRepository) {
        this.preferinteService = preferinteService;
        this.soferRepository = soferRepository;
    }

    // ========================= CRUD STANDARD =========================

    @GetMapping
    public List<PreferinteParcare> getAll() {
        return preferinteService.getAll();
    }

    @GetMapping("/{id}")
    public PreferinteParcare getById(@PathVariable Long id) {
        return preferinteService.getById(id);
    }

    @PostMapping
    public PreferinteParcare create(@RequestBody PreferinteParcare p) {
        return preferinteService.save(p);
    }

    @PutMapping("/{id}")
    public PreferinteParcare update(@PathVariable Long id, @RequestBody PreferinteParcare p) {
        p.setId(id);
        return preferinteService.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        preferinteService.delete(id);
    }

    // ========================= ENDPOINTURI PENTRU FLUTTER =========================

    /**
     * Seteaza sau actualizeaza preferintele pentru un sofer
     * Body JSON asteptat:
     * {
     *   "soferId": 1,
     *   "inauntru": true,
     *   "etaj": 2,
     *   "intrarePreferata": "A",
     *   "pentruPersoaneCuDizabilitati": false
     * }
     */
    @PostMapping("/set")
    public ResponseEntity<?> setPreferinte(@RequestBody Map<String, Object> body) {
        try {
            Long soferId = Long.valueOf(String.valueOf(body.get("soferId")));
            Boolean inauntru = (Boolean) body.get("inauntru");
            Integer etaj = (Integer) body.get("etaj");
            String intrare = (String) body.get("intrarePreferata");
            Boolean dizabilitati = (Boolean) body.get("pentruPersoaneCuDizabilitati");

            Sofer sofer = soferRepository.findById(soferId).orElse(null);
            if (sofer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "Soferul nu exista"));
            }

            // Verifica daca deja exista preferinte pentru acest sofer
            PreferinteParcare preferinte = preferinteService.getBySoferId(soferId);
            if (preferinte == null) {
                preferinte = new PreferinteParcare();
                preferinte.setSofer(sofer);
            }

            preferinte.setInauntru(inauntru);
            preferinte.setEtaj(etaj);
            preferinte.setIntrarePreferata(intrare);
            preferinte.setPentruPersoaneCuDizabilitati(dizabilitati);

            PreferinteParcare saved = preferinteService.save(preferinte);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "preferinteId", saved.getId(),
                    "soferId", sofer.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Date invalide: " + e.getMessage()));
        }
    }

    /**
     * Obține preferintele unui sofer după id-ul său
     */
    @GetMapping("/sofer/{soferId}")
    public ResponseEntity<?> getPreferinteSofer(@PathVariable Long soferId) {
        PreferinteParcare preferinte = preferinteService.getBySoferId(soferId);
        if (preferinte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Preferintele nu au fost gasite"));
        }
        return ResponseEntity.ok(preferinte);
    }

    @PostMapping("/{soferId}")
    public ResponseEntity<?> savePreferinte(
            @PathVariable Long soferId,
            @RequestBody PreferinteParcare preferinte) {

        Sofer sofer = soferRepository.findById(soferId).orElse(null);
        if (sofer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Sofer nu exista"));
        }

        PreferinteParcare saved = preferinteService.saveForSofer(sofer, preferinte);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "preferinteId", saved.getId(),
                "inauntru", saved.isInauntru(),
                "etaj", saved.getEtaj(),
                "intrarePreferata", saved.getIntrarePreferata(),
                "dizabilitati", saved.isPentruPersoaneCuDizabilitati()
        ));
    }

}
