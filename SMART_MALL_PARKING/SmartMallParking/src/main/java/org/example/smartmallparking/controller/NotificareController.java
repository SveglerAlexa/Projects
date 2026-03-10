package org.example.smartmallparking.controller;

import org.example.smartmallparking.model.Notificare;
import org.example.smartmallparking.service.NotificareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificari")
public class NotificareController {

    private final NotificareService notificareService;

    public NotificareController(NotificareService notificareService) {
        this.notificareService = notificareService;
    }

    @GetMapping
    public List<Notificare> getAll() {
        return notificareService.getAll();
    }

    @GetMapping("/{id}")
    public Notificare getById(@PathVariable Long id) {
        return notificareService.getById(id);
    }

    @PostMapping
    public Notificare create(@RequestBody Notificare n) {
        return notificareService.save(n);
    }

    @PutMapping("/{id}")
    public Notificare update(@PathVariable Long id, @RequestBody Notificare n) {
        n.setId(id);
        return notificareService.save(n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificareService.delete(id);
    }
}
