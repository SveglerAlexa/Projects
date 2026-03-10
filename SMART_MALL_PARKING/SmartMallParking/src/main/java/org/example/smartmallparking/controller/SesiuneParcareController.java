package org.example.smartmallparking.controller;

import org.example.smartmallparking.dto.SesiuneParcareDTO;
import org.example.smartmallparking.service.SesiuneParcareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/sesiuni-parcare")
public class SesiuneParcareController {

    private final SesiuneParcareService sesiuneParcareService;

    public SesiuneParcareController(SesiuneParcareService sesiuneParcareService) {
        this.sesiuneParcareService = sesiuneParcareService;
    }

    @GetMapping("/curenta/{soferId}")
    public ResponseEntity<?> getSesiuneCurenta(@PathVariable Long soferId) {
        Optional<SesiuneParcareDTO> sesiuneOpt = sesiuneParcareService.getSesiuneCurenta(soferId);

        if (sesiuneOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(sesiuneOpt.get());
    }
}
