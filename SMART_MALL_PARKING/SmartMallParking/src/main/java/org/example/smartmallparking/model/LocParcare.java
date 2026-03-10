package org.example.smartmallparking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class LocParcare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numarLoc; // A1, B3 etc.

    private String etaj; // exterior, parter, etaj1
    private boolean inauntru; // true dacă e interior
    private String intrarePreferata; // Vest, Est, Principal
    private boolean pentruPersoaneCuDizabilitati; // loc pentru persoane cu dizabilități

    private float x; // coordonata X pe hartă
    private float y; // coordonata Y pe hartă

    private String status; // liber, ocupat, rezervat

    @OneToMany(mappedBy = "locParcare")
    @JsonIgnore
    private List<Rezervare> rezervari;

    // Gettere și settere sunt generate de @Data
}
