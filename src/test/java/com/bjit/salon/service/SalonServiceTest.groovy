package com.bjit.salon.service

import com.bjit.salon.service.dto.request.SalonCreateDto
import com.bjit.salon.service.dto.request.SalonUpdateDto
import com.bjit.salon.service.dto.response.SalonResponseDto
import com.bjit.salon.service.entiry.Salon
import com.bjit.salon.service.mapper.SalonMapper
import com.bjit.salon.service.repository.SalonRepository
import com.bjit.salon.service.service.SalonService
import com.bjit.salon.service.serviceImpl.SalonServiceImpl
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalTime


@SpringBootTest
class SalonServiceTest extends Specification {


    private SalonServiceImpl salonService
//    @Autowired
    private SalonMapper salonMapper
    private SalonRepository salonRepository

    private static LocalTime startTime
    private static LocalTime endTime
    private Salon salon
    private Salon updatedSalon;
    private SalonCreateDto salonRequest;
    private SalonUpdateDto salonUpdateRequest;
    private SalonResponseDto salonResponse;

    def setup() {

        salonMapper = Mock(SalonMapper)
        salonRepository = Mock(SalonRepository)
        salonService = new SalonServiceImpl(salonRepository, salonMapper)

        startTime = LocalTime.parse("10:00:00")
        endTime = startTime
                .plusHours(1)
                .plusMinutes(40)

        salonRequest = SalonCreateDto.builder()
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .userId(1L)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()
        salon = Salon.builder()
                .id(1L)
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .userId(1L)
                .reviews(0.0)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()

        updatedSalon = Salon.builder()
                .id(1L)
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .userId(1L)
                .reviews(0.0)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()
        salonResponse = SalonResponseDto.builder()
                .id(1L)
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .userId(1L)
                .reviews(0.0)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()

        salonUpdateRequest = SalonUpdateDto.builder()
                .id(1L)
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()
    }


    def "should create a new salon"() {
        given:
        salonMapper.toSalon(salonRequest) >> salon
        salonRepository.save(salon) >> salon
        salonMapper.toSalonResponse(salon) >>salonResponse

        when:
        def db = salonService.create(salonRequest)

        then:
        db.getName() == "abc salon"
        db.getDescription() == "Best Salon of Dhaka City"
        db.getAddress() == "Dhaka"
        db.getClosingTime() == LocalTime.parse("11:40:00")
        db.getOpeningTime() == LocalTime.parse("10:00:00")
        db.getUserId() == 1
        db.getReviews() == (double)0.0
        db.getContractNumber() == "4738478"
    }

    def "should update salon"(){
        given:
        salonRepository.findById(salonUpdateRequest.getId()) >> Optional.of(salon)
        salonRepository.save(_) >> updatedSalon
        salonMapper.toSalonResponse(_) >> salonResponse

        when:
        def db = salonService.update(salonUpdateRequest)

        then:
        db.getName() == "abc salon"
        db.getDescription() == "Best Salon of Dhaka City"
        db.getAddress() == "Dhaka"
        db.getClosingTime() == LocalTime.parse("11:40:00")
        db.getOpeningTime() == LocalTime.parse("10:00:00")
        db.getUserId() == 1
        db.getReviews() == (double)0.0
        db.getContractNumber() == "4738478"

    }
}
