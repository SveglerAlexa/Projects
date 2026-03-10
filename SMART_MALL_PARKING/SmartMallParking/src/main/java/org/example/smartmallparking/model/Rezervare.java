package org.example.smartmallparking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Rezervare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loc_parcare_id")
    private LocParcare locParcare;

    @ManyToOne
    @JoinColumn(name = "sofer_id")
    private Sofer sofer;

    private LocalDateTime rezervareStart;
    private LocalDateTime rezervareEnd;

    private String status; // rezervat, ocupat
}
