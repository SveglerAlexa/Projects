package org.example.smartmallparking.service;

import org.example.smartmallparking.dto.SesiuneParcareDTO;
import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.repository.ParcareActivaRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SesiuneParcareService {

    private final ParcareActivaRepository repo;
    private static final double TARIF_PE_ORA = 5.0;

    public SesiuneParcareService(ParcareActivaRepository repo) {
        this.repo = repo;
    }

    public Optional<SesiuneParcareDTO> getSesiuneCurenta(Long soferId) {
        Optional<ParcareActiva> paOpt = repo.findByUtilizatorId(soferId); // schimbat dacă repository-ul are findByUtilizatorId

        if (paOpt.isEmpty()) return Optional.empty();

        ParcareActiva pa = paOpt.get();

        // Simulare: 1 minut = 1 oră
        long minuteSimulate = Duration.between(pa.getOraInceput(), LocalDateTime.now()).toMinutes();

        double costTotal = (minuteSimulate / 60.0) * TARIF_PE_ORA;

        return Optional.of(new SesiuneParcareDTO(
                pa.getOraInceput(),
                minuteSimulate,
                TARIF_PE_ORA,
                costTotal
        ));
    }
}
