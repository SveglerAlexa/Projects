package org.example.smartmallparking.service;

import org.example.smartmallparking.model.TarifParcare;
import org.example.smartmallparking.repository.TarifParcareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifParcareService {

    private final TarifParcareRepository tarifParcareRepository;

    public TarifParcareService(TarifParcareRepository tarifParcareRepository) {
        this.tarifParcareRepository = tarifParcareRepository;
    }

    public List<TarifParcare> getAll() {
        return tarifParcareRepository.findAll();
    }

    public TarifParcare getById(Long id) {
        return tarifParcareRepository.findById(id).orElse(null);
    }

    public TarifParcare save(TarifParcare t) {
        return tarifParcareRepository.save(t);
    }

    public void delete(Long id) {
        tarifParcareRepository.deleteById(id);
    }
}
