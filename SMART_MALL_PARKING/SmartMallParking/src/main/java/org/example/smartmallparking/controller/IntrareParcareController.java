package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.IntrareParcare;
import org.example.smartmallparking.service.IntrareParcareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intrari")
public class IntrareParcareController {

    private final IntrareParcareService intrareParcareService;

    public IntrareParcareController(IntrareParcareService intrareParcareService) {
        this.intrareParcareService = intrareParcareService;
    }

    @GetMapping
    public List<IntrareParcare> getAll() {
        return intrareParcareService.getAll();
    }

    @GetMapping("/{id}")
    public IntrareParcare getById(@PathVariable Long id) {
        return intrareParcareService.getById(id);
    }

    @PostMapping
    public IntrareParcare create(@RequestBody IntrareParcare i) {
        return intrareParcareService.save(i);
    }

    @PutMapping("/{id}")
    public IntrareParcare update(@PathVariable Long id, @RequestBody IntrareParcare i) {
        i.setId(id);
        return intrareParcareService.save(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        intrareParcareService.delete(id);
    }
}
