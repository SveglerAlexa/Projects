package org.example.smartmallparking.service;

import org.example.smartmallparking.model.*;
import org.example.smartmallparking.repository.LocParcareRepository;
import org.example.smartmallparking.repository.ParcareActivaRepository;
import org.example.smartmallparking.repository.RezervareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.smartmallparking.service.PreferinteParcareService;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.example.smartmallparking.model.*;
import org.example.smartmallparking.repository.LocParcareRepository;
import org.example.smartmallparking.repository.ParcareActivaRepository;
import org.example.smartmallparking.repository.RezervareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RezervareService {

    private final LocParcareRepository locRepo;
    private final ParcareActivaRepository parcareActivaRepo;
    private final SimulareSosireService simulareService;
    private final RezervareRepository rezRepo;

    public RezervareService(
            LocParcareRepository locRepo,
            ParcareActivaRepository parcareActivaRepo,
            SimulareSosireService simulareService,
            RezervareRepository rezRepo
    ) {
        this.locRepo = locRepo;
        this.parcareActivaRepo = parcareActivaRepo;
        this.simulareService = simulareService;
        this.rezRepo = rezRepo;
    }


    @Transactional
    public Optional<LocParcare> rezervaLoc(Utilizator utilizator) {
        if (!(utilizator instanceof Sofer sofer)) {
            return Optional.empty();
        }

        // 1 Verificăm dacă soferul are deja un loc rezervat sau ocupat
        boolean areRezervareActiva = parcareActivaRepo.existsByUtilizator(sofer);
        if (areRezervareActiva) {
            return Optional.empty(); // poate arunca exceptie sau log
        }



        // 2 Preluăm preferințele, dacă există
        PreferinteParcare p = sofer.getPreferinte();

        Optional<LocParcare> locOpt;

        if (p != null) {
            // Dacă prefera loc pentru persoane cu dizabilitati, cauta doar acelea
            if (p.isPentruPersoaneCuDizabilitati()) {
                locOpt = locRepo.findFirstByStatusAndPentruPersoaneCuDizabilitati("liber", true);
            } else {
                // Dacă nu, cauta dupa toate celelalte preferinte
                locOpt = locRepo.findFirstByStatusAndEtajAndInauntruAndIntrarePreferata(
                        "liber",
                        String.valueOf(p.getEtaj()),
                        p.isInauntru(),
                        p.getIntrarePreferata()
                );
            }
        } else {
            locOpt = locRepo.findFirstByStatus("liber");
        }


        //  Rezervăm locul
        LocParcare loc = locOpt.get();
        loc.setStatus("rezervat");
        locRepo.save(loc);

        //  Creăm ParcareActiva
        ParcareActiva pa = new ParcareActiva();
        pa.setUtilizator(sofer);
        pa.setLocParcare(loc);
        pa.setOraInceput(LocalDateTime.now());
        parcareActivaRepo.save(pa);

        //  Simulare ocupare după delay
        simulareService.marcheazaOcupatDupaDelay(loc);

        return Optional.of(loc);
    }


//    @Transactional
//    public Optional<LocParcare> rezervaLoc(Utilizator utilizator) {
//        if (!(utilizator instanceof Sofer sofer)) return Optional.empty();
//
//        // 1️⃣ Verificăm dacă soferul are deja o rezervare
//        boolean areRezervare = rezRepo.existsBySofer(sofer);
//
//        // 2️⃣ Verificăm dacă soferul are deja un loc ocupat
//        boolean areParcareActiva = parcareActivaRepo.existsByUtilizator(sofer);
//
//        if (areRezervare || areParcareActiva) {
//            return Optional.empty(); // deja are loc ocupat sau rezervat
//        }
//
//        PreferinteParcare p = sofer.getPreferinte();
//        if (p == null) return Optional.empty();
//
//        Optional<LocParcare> locOpt =
//                locRepo.findFirstByStatusAndEtajAndInauntruAndIntrarePreferataAndPentruPersoaneCuDizabilitati(
//                        "liber",
//                        String.valueOf(p.getEtaj()),
//                        p.isInauntru(),
//                        p.getIntrarePreferata(),
//                        p.isPentruPersoaneCuDizabilitati()
//                );
//
//        if (locOpt.isEmpty()) return Optional.empty();
//
//        LocParcare loc = locOpt.get();
//        loc.setStatus("rezervat");
//        locRepo.save(loc);
//
//        ParcareActiva pa = new ParcareActiva();
//        pa.setUtilizator(sofer);
//        pa.setLocParcare(loc);
//        pa.setOraInceput(LocalDateTime.now());
//        parcareActivaRepo.save(pa);
//
//        simulareService.marcheazaOcupatDupaDelay(loc);
//
//        return Optional.of(loc);
//    }


    // 🔹 CRUD pentru Rezervare
    public List<Rezervare> getAll() {
        return rezRepo.findAll();
    }

    public Rezervare getById(Long id) {
        return rezRepo.findById(id).orElse(null);
    }

    public Rezervare save(Rezervare r) {
        return rezRepo.save(r);
    }

    public void delete(Long id) {
        rezRepo.deleteById(id);
    }

    public boolean hasActiveRezervare(Sofer sofer) {
        return rezRepo.existsBySoferAndStatusIn(sofer, List.of("rezervat", "ocupat"));
    }

    public void elibereazaLoc(Long soferId) {
        Optional<Rezervare> rezervareOpt = rezRepo.findBySoferId(soferId);

        if (rezervareOpt.isPresent()) {
            Rezervare rezervare = rezervareOpt.get();

            // 1 Setăm locul ca liber
            LocParcare loc = rezervare.getLocParcare();
            loc.setStatus("liber");
            locRepo.save(loc);

            // 2 Ștergem rezervarea
            rezRepo.delete(rezervare);

            // 3 Ștergem parcare activa dacă există
            parcareActivaRepo.findByUtilizator(rezervare.getSofer())
                    .ifPresent(parcareActivaRepo::delete);
        }
    }
}
