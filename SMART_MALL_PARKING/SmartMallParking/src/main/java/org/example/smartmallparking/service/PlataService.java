package org.example.smartmallparking.service;

import org.example.smartmallparking.model.Plata;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.repository.PlataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlataService {

    private final PlataRepository plataRepository;

    public PlataService(PlataRepository plataRepository) {
        this.plataRepository = plataRepository;
    }

    public List<Plata> getAll() {
        return plataRepository.findAll();
    }

    public Plata getById(Long id) {
        return plataRepository.findById(id).orElse(null);
    }

    public Optional<Plata> getByIdOptional(Long id) {
        return plataRepository.findById(id);
    }

    public Plata save(Plata p) {
        return plataRepository.save(p);
    }

    public void delete(Long id) {
        plataRepository.deleteById(id);
    }

    public void inregistreazaPlata(Sofer sofer, double suma, Long locParcareId) {
        Plata plata = new Plata(
                LocalDateTime.now(),
                suma,
                sofer,
                locParcareId
        );
        plataRepository.save(plata);
    }
}
