package org.example.smartmallparking.service;

import org.example.smartmallparking.model.IntrareParcare;
import org.example.smartmallparking.repository.IntrareParcareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntrareParcareService {

    private final IntrareParcareRepository intrareParcareRepository;

    public IntrareParcareService(IntrareParcareRepository intrareParcareRepository) {
        this.intrareParcareRepository = intrareParcareRepository;
    }

    public List<IntrareParcare> getAll() {
        return intrareParcareRepository.findAll();
    }

    public IntrareParcare getById(Long id) {
        return intrareParcareRepository.findById(id).orElse(null);
    }

    public IntrareParcare save(IntrareParcare i) {
        return intrareParcareRepository.save(i);
    }

    public void delete(Long id) {
        intrareParcareRepository.deleteById(id);
    }
}
