package com.bjit.salon.service.service;

import com.bjit.salon.service.dto.request.SalonCreateDto;
import com.bjit.salon.service.dto.request.SalonUpdateDto;
import com.bjit.salon.service.dto.response.SalonResponseDto;

import java.util.List;

public interface SalonService {
    SalonResponseDto create(SalonCreateDto salonCreateDto);
    SalonResponseDto update(SalonUpdateDto salonUpdateDto);
    SalonResponseDto getSalon(Long id);
    List<SalonResponseDto> getAllSalon();

    List<SalonResponseDto> getSalonsByQuery(String str);
}
