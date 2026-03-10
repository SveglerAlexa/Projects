package org.example.smartmallparking.service;

import org.example.smartmallparking.model.LocParcare;
import org.example.smartmallparking.repository.LocParcareRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SimulareSosireService {

    private final LocParcareRepository locRepo;

    public SimulareSosireService(LocParcareRepository locRepo) {
        this.locRepo = locRepo;
    }

    @Async
    public void marcheazaOcupatDupaDelay(LocParcare loc) {
        try {
            Thread.sleep(5_000);
            loc.setStatus("ocupat");
            locRepo.save(loc);
            System.out.println("Loc " + loc.getNumarLoc() + " a devenit OCUPAT");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
