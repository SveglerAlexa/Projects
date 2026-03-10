package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.Sofer;
import org.example.smartmallparking.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoferRepository extends JpaRepository<Sofer, Long> {
    Sofer findByEmail(String email);
    boolean existsByEmail(String email);
}
