package com.bjit.salon.service.serviceImpl;

import com.bjit.salon.service.dto.request.SalonCreateDto;
import com.bjit.salon.service.dto.request.SalonUpdateDto;
import com.bjit.salon.service.dto.response.SalonResponseDto;
import com.bjit.salon.service.entity.Salon;
import com.bjit.salon.service.exception.SalonNotFoundException;
import com.bjit.salon.service.mapper.SalonMapper;
import com.bjit.salon.service.repository.SalonRepository;
import com.bjit.salon.service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SalonServiceImpl implements SalonService {

    private static final Logger log = LoggerFactory.getLogger(SalonServiceImpl.class);

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;

    @Override
    public SalonResponseDto create(SalonCreateDto salonCreateDto) {
        Salon salon = salonRepository.save(salonMapper.toSalon(salonCreateDto));
        return salonMapper.toSalonResponse(salon);
    }

    @Override
    public SalonResponseDto update(SalonUpdateDto salonUpdateDto) {
        Salon salon = getSalonBySalonId(salonUpdateDto.getId());
        Salon updateSalon = Salon
                .builder()
                .id(salon.getId())
                .address(salonUpdateDto.getAddress())
                .description(salonUpdateDto.getDescription())
                .name(salonUpdateDto.getName())
                .openingTime(salonUpdateDto.getOpeningTime())
                .closingTime(salonUpdateDto.getClosingTime())
                .reviews(salon.getReviews())
                .userId(salon.getUserId())
                .contractNumber(salonUpdateDto.getContractNumber())
                .build();
        Salon salonResponse = salonRepository.save(updateSalon);
        return salonMapper.toSalonResponse(salonResponse);
    }

    @Override
    public SalonResponseDto getSalon(Long id) {
        Salon salon = getSalonBySalonId(id);
        log.info("Getting salon details:{}, for id:{}", salon ,id);
        return salonMapper.toSalonResponse(salon);
    }

    @Override
    public List<SalonResponseDto> getAllSalon() {
        List<Salon> salons = salonRepository.findAll();
        log.info("Getting all salons, details: {}", salons);
        return salonMapper.toListOfSalonResponseDto(salons);
    }

    @Override
    public List<SalonResponseDto> getSalonsByQuery(String str) {
        log.info("Searching salon with string: {}", str);
        return salonMapper.toListOfSalonResponseDto(salonRepository.findByNameContainingIgnoreCase(str));
    }

    private Salon getSalonBySalonId(Long id) {
        Optional<Salon> salon = salonRepository.findById(id);
        if(salon.isEmpty()) throw new SalonNotFoundException("Salon not found for id: " + id);
        return salon.get();
    }
}
