package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.LocParcare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocParcareRepository extends JpaRepository<LocParcare, Long> {

    Optional<LocParcare> findFirstByStatusAndEtajAndInauntruAndIntrarePreferataAndPentruPersoaneCuDizabilitati(
            String status,
            String etaj,
            boolean inauntru,
            String intrarePreferata,
            boolean pentruPersoaneCuDizabilitati
    );

    Optional<LocParcare> findFirstByStatus(String status);
    Optional<LocParcare> findFirstByStatusAndEtajAndInauntruAndIntrarePreferata(
            String status, String etaj, boolean inauntru, String intrarePreferata);

    Optional<LocParcare> findFirstByStatusAndPentruPersoaneCuDizabilitati(String liber, boolean b);
}
