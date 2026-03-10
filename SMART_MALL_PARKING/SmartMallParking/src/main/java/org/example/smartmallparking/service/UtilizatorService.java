package org.example.smartmallparking.service;

import org.example.smartmallparking.model.Utilizator;
import org.example.smartmallparking.repository.UtilizatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizatorService {

    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Utilizator> getAll() {
        return utilizatorRepository.findAll();
    }

    public Utilizator getById(Long id) {
        return utilizatorRepository.findById(id).orElse(null);
    }

    public Utilizator save(Utilizator u) {
        return utilizatorRepository.save(u);
    }

    public void delete(Long id) {
        utilizatorRepository.deleteById(id);
    }
}
