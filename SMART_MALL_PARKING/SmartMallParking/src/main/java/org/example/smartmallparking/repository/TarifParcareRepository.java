package org.example.smartmallparking.repository;

import org.example.smartmallparking.model.TarifParcare;
import org.example.smartmallparking.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifParcareRepository extends JpaRepository<TarifParcare, Long> {}
