package org.example.smartmallparking.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Administrator extends Utilizator {

    private String departament;
}

