package com.bjit.salon.service.controller;


import com.bjit.salon.service.dto.request.SalonCreateDto;
import com.bjit.salon.service.dto.request.SalonUpdateDto;
import com.bjit.salon.service.dto.response.SalonResponseDto;
import com.bjit.salon.service.service.SalonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.bjit.salon.service.util.ConstraintsUtil.APPLICATION_BASE_URL;

@AllArgsConstructor
@RestController
@RequestMapping(APPLICATION_BASE_URL)
public class SalonController {
    private final Logger log = LoggerFactory.getLogger(SalonController.class);
    private final SalonService salonService;


    @PostMapping("/salons") // admin can create salon
    public ResponseEntity<SalonResponseDto> create(@RequestBody SalonCreateDto salonCreateDto){
        log.info("Creating salon with name: {}", salonCreateDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(salonService.create(salonCreateDto));
    }

    @PutMapping("/salons") // admin can update
    public ResponseEntity<SalonResponseDto> update(@RequestBody SalonUpdateDto salonUpdateDto){
        log.info("Updating salon with name: {}",salonUpdateDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(salonService.update(salonUpdateDto));
    }

    @GetMapping("/salons/{id}")
    public ResponseEntity<SalonResponseDto> get(@PathVariable("id") Long id){
        log.info("Getting salon details with salon id: {}",id);
        return ResponseEntity.ok(salonService.getSalon(id));
    }

    @GetMapping("/salons")
    public ResponseEntity<List<SalonResponseDto>> getAll(){
        List<SalonResponseDto> allSalon = salonService.getAllSalon();
        log.info("getting salons, size: {}",allSalon.size());
        return ResponseEntity.ok(allSalon);
    }

    @GetMapping("/salons/search")
    public ResponseEntity<List<SalonResponseDto>> search(@RequestParam("q") String str){
        log.info("Searching salons with query: {}",str);
        return ResponseEntity.ok(salonService.getSalonsByQuery(str));
    }

}
