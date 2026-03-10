package org.example.smartmallparking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Notificare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mesaj;

    @ManyToOne
    private Utilizator utilizator;

    // getter/setter
}
