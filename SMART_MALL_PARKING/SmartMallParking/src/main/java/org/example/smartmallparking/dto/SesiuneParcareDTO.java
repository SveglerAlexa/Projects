package org.example.smartmallparking.dto;

import java.time.LocalDateTime;

public class SesiuneParcareDTO {
    private LocalDateTime oraInceput;
    private long minute;
    private double tarifPeOra;
    private double costTotal;

    public SesiuneParcareDTO(LocalDateTime oraInceput, long minute, double tarifPeOra, double costTotal) {
        this.oraInceput = oraInceput;
        this.minute = minute;
        this.tarifPeOra = tarifPeOra;
        this.costTotal = costTotal;
    }

    // Getters
    public LocalDateTime getOraInceput() { return oraInceput; }
    public long getMinute() { return minute; }
    public double getTarifPeOra() { return tarifPeOra; }
    public double getCostTotal() { return costTotal; }
}
