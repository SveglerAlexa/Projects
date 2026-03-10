package org.example.smartmallparking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class ParcareActiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime oraInceput;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @ManyToOne
    @JoinColumn(name = "loc_parcare_id", nullable = false)
    private LocParcare locParcare;
}
