package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.PreferinteParcare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferinteParcareRepository extends JpaRepository<PreferinteParcare, Long> {
    Optional<PreferinteParcare> findBySoferId(Long soferId);
}
