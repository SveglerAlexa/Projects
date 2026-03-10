package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.Rezervare;
import org.example.smartmallparking.model.Sofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Long> {
    boolean existsBySofer(Sofer sofer);
    boolean existsBySoferAndStatus(Sofer sofer, String status);
    boolean existsBySoferAndStatusIn(Sofer sofer, List<String> statusuri);


    Optional<Rezervare> findBySoferId(Long soferId);
}
