package org.example.smartmallparking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class PreferinteParcare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean inauntru; // true = inauntru, false = afara
    private int etaj; // 1 sau 2
    private String intrarePreferata; // A / B
    private boolean pentruPersoaneCuDizabilitati;

    @OneToOne
    @JoinColumn(name = "sofer_id")
    @JsonIgnore
    private Sofer sofer;
}
