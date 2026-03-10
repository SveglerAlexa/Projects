package org.example.smartmallparking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plata")
public class Plata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPlata;

    private double suma;

    @ManyToOne
    @JoinColumn(name = "sofer_id", nullable = false)
    private Sofer sofer;

    // ID-ul locului de parcare (fără constrângeri)
    private Long locParcareId;

    public Plata() {}

    public Plata(LocalDateTime dataPlata, double suma, Sofer sofer, Long locParcareId) {
        this.dataPlata = dataPlata;
        this.suma = suma;
        this.sofer = sofer;
        this.locParcareId = locParcareId;
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPlata() {
        return dataPlata;
    }

    public void setDataPlata(LocalDateTime dataPlata) {
        this.dataPlata = dataPlata;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Sofer getSofer() {
        return sofer;
    }

    public void setSofer(Sofer sofer) {
        this.sofer = sofer;
    }

    public Long getLocParcareId() {
        return locParcareId;
    }

    public void setLocParcareId(Long locParcareId) {
        this.locParcareId = locParcareId;
    }
}
