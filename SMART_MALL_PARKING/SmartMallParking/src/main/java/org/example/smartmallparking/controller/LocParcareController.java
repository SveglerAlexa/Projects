package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.LocParcare;
import org.example.smartmallparking.service.LocParcareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locuri")
public class LocParcareController {

    private final LocParcareService locParcareService;

    public LocParcareController(LocParcareService locParcareService) {
        this.locParcareService = locParcareService;
    }

    @GetMapping
    public List<LocParcare> getAll() {
        return locParcareService.getAll();
    }

    @GetMapping("/{id}")
    public LocParcare getById(@PathVariable Long id) {
        return locParcareService.getById(id);
    }

    @PostMapping
    public LocParcare create(@RequestBody LocParcare loc) {
        return locParcareService.save(loc);
    }

    @PutMapping("/{id}")
    public LocParcare update(@PathVariable Long id, @RequestBody LocParcare loc) {
        loc.setId(id);
        return locParcareService.save(loc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locParcareService.delete(id);
    }
}
