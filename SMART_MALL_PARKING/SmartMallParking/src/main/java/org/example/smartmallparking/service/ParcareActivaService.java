package org.example.smartmallparking.service;

import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.repository.ParcareActivaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcareActivaService {

    private final ParcareActivaRepository parcareActivaRepository;

    public ParcareActivaService(ParcareActivaRepository parcareActivaRepository) {
        this.parcareActivaRepository = parcareActivaRepository;
    }

    public List<ParcareActiva> getAll() {
        return parcareActivaRepository.findAll();
    }

    public ParcareActiva getById(Long id) {
        return parcareActivaRepository.findById(id).orElse(null);
    }

    public ParcareActiva save(ParcareActiva p) {
        return parcareActivaRepository.save(p);
    }

    public void delete(Long id) {
        parcareActivaRepository.deleteById(id);
    }

    public Optional<ParcareActiva> getParcareActiva(Sofer sofer) {
        return parcareActivaRepository.findByUtilizator(sofer);
    }

    public Optional<ParcareActiva> getParcareActivaById(Long soferId) {
        return parcareActivaRepository.findByUtilizatorId(soferId);
    }


}
