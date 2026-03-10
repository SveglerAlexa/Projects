package org.example.smartmallparking.service;

import org.example.smartmallparking.model.PreferinteParcare;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.repository.PreferinteParcareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PreferinteParcareService {

    private final PreferinteParcareRepository preferinteRepository;

    public PreferinteParcareService(PreferinteParcareRepository preferinteRepository) {
        this.preferinteRepository = preferinteRepository;
    }

    public List<PreferinteParcare> getAll() {
        return preferinteRepository.findAll();
    }

    public PreferinteParcare getById(Long id) {
        return preferinteRepository.findById(id).orElse(null);
    }

    public PreferinteParcare save(PreferinteParcare p) {
        return preferinteRepository.save(p);
    }

    public void delete(Long id) {
        preferinteRepository.deleteById(id);
    }

    // ==================== Upsert pentru un sofer ====================
    public PreferinteParcare saveForSofer(Sofer sofer, PreferinteParcare preferinte) {
        PreferinteParcare existing = preferinteRepository
                .findBySoferId(sofer.getId())
                .orElse(null);

        if (existing != null) {
            // suprascriem valorile
            existing.setInauntru(preferinte.isInauntru());
            existing.setEtaj(preferinte.getEtaj());
            existing.setIntrarePreferata(preferinte.getIntrarePreferata());
            existing.setPentruPersoaneCuDizabilitati(preferinte.isPentruPersoaneCuDizabilitati());
            return preferinteRepository.save(existing);
        } else {
            // cream una noua
            preferinte.setSofer(sofer);
            return preferinteRepository.save(preferinte);
        }
    }

    public PreferinteParcare getBySoferId(Long soferId) {
        return preferinteRepository.findBySoferId(soferId).orElse(null);
    }
}
