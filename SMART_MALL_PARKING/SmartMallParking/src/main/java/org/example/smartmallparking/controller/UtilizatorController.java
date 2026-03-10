package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.Utilizator;
import org.example.smartmallparking.service.UtilizatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatorController {

    private final UtilizatorService utilizatorService;

    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    @GetMapping
    public List<Utilizator> getAll() {
        return utilizatorService.getAll();
    }

    @GetMapping("/{id}")
    public Utilizator getById(@PathVariable Long id) {
        return utilizatorService.getById(id);
    }

    @PostMapping
    public Utilizator create(@RequestBody Utilizator u) {
        return utilizatorService.save(u);
    }

    @PutMapping("/{id}")
    public Utilizator update(@PathVariable Long id, @RequestBody Utilizator u) {
        u.setId(id);
        return utilizatorService.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        utilizatorService.delete(id);
    }
}
