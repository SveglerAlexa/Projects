package org.example.smartmallparking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class IntrareParcare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime oraIntrare;

    @OneToOne
    private ParcareActiva parcareActiva;

    // getter/setter
}
