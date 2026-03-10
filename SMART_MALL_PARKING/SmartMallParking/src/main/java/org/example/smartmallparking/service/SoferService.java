package org.example.smartmallparking.service;

import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.repository.SoferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoferService {

    private final SoferRepository soferRepository;

    public SoferService(SoferRepository soferRepository) {
        this.soferRepository = soferRepository;
    }

    public List<Sofer> getAll() {
        return soferRepository.findAll();
    }

    public Sofer getById(Long id) {
        return soferRepository.findById(id).orElse(null);
    }

    public Sofer save(Sofer s) {
        return soferRepository.save(s);
    }

    public void delete(Long id) {
        soferRepository.deleteById(id);
    }
}
