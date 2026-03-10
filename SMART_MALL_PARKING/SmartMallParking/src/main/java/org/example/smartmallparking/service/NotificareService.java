package org.example.smartmallparking.service;

import org.example.smartmallparking.model.Notificare;
import org.example.smartmallparking.repository.NotificareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificareService {

    private final NotificareRepository notificareRepository;

    public NotificareService(NotificareRepository notificareRepository) {
        this.notificareRepository = notificareRepository;
    }

    public List<Notificare> getAll() {
        return notificareRepository.findAll();
    }

    public Notificare getById(Long id) {
        return notificareRepository.findById(id).orElse(null);
    }

    public Notificare save(Notificare n) {
        return notificareRepository.save(n);
    }

    public void delete(Long id) {
        notificareRepository.deleteById(id);
    }
}
