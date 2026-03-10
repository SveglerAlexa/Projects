package org.example.smartmallparking.service;

import org.example.smartmallparking.model.LocParcare;
import org.example.smartmallparking.repository.LocParcareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocParcareService {

    private final LocParcareRepository locParcareRepository;

    public LocParcareService(LocParcareRepository locParcareRepository) {
        this.locParcareRepository = locParcareRepository;
    }

    public List<LocParcare> getAll() {
        return locParcareRepository.findAll();
    }

    public LocParcare getById(Long id) {
        return locParcareRepository.findById(id).orElse(null);
    }

    public LocParcare save(LocParcare loc) {
        return locParcareRepository.save(loc);
    }

    public void delete(Long id) {
        locParcareRepository.deleteById(id);
    }
}
