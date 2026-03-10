package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.Administrator;
import org.example.smartmallparking.service.AdministratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administratori")
public class AdministratorController {

    private final AdministratorService administratorService;

    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping
    public List<Administrator> getAll() {
        return administratorService.getAll();
    }

    @GetMapping("/{id}")
    public Administrator getById(@PathVariable Long id) {
        return administratorService.getById(id);
    }

    @PostMapping
    public Administrator create(@RequestBody Administrator a) {
        return administratorService.save(a);
    }

    @PutMapping("/{id}")
    public Administrator update(@PathVariable Long id, @RequestBody Administrator a) {
        a.setId(id);
        return administratorService.save(a);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        administratorService.delete(id);
    }
}
