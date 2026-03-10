package org.example.smartmallparking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TarifParcare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double tarifPerOra;

    @OneToOne
    private LocParcare locParcare;

    // getter/setter
}
