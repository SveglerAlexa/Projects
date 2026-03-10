package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.TarifParcare;
import org.example.smartmallparking.service.TarifParcareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarife")
public class TarifParcareController {

    private final TarifParcareService tarifParcareService;

    public TarifParcareController(TarifParcareService tarifParcareService) {
        this.tarifParcareService = tarifParcareService;
    }

    @GetMapping
    public List<TarifParcare> getAll() {
        return tarifParcareService.getAll();
    }

    @GetMapping("/{id}")
    public TarifParcare getById(@PathVariable Long id) {
        return tarifParcareService.getById(id);
    }

    @PostMapping
    public TarifParcare create(@RequestBody TarifParcare t) {
        return tarifParcareService.save(t);
    }

    @PutMapping("/{id}")
    public TarifParcare update(@PathVariable Long id, @RequestBody TarifParcare t) {
        t.setId(id);
        return tarifParcareService.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tarifParcareService.delete(id);
    }
}
