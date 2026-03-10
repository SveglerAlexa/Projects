package org.example.smartmallparking.service;

import org.example.smartmallparking.model.Administrator;
import org.example.smartmallparking.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }

    public Administrator getById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public Administrator save(Administrator a) {
        return administratorRepository.save(a);
    }

    public void delete(Long id) {
        administratorRepository.deleteById(id);
    }
}
