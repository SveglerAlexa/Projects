package org.example.smartmallparking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity

public class Sofer extends Utilizator {

    @OneToOne(mappedBy = "sofer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private PreferinteParcare preferinte;
    private String nrInmatriculare;
}

