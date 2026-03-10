package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.ParcareActiva;
import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParcareActivaRepository extends JpaRepository<ParcareActiva, Long> {

    boolean existsByUtilizator(Utilizator utilizator);

    Optional<ParcareActiva> findByUtilizator(Sofer sofer);
    Optional<ParcareActiva> findByUtilizatorId(Long soferId);
}
