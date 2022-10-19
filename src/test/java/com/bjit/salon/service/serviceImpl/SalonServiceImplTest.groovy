package com.bjit.salon.service.serviceImpl

import com.bjit.salon.service.dto.request.SalonCreateDto
import com.bjit.salon.service.dto.request.SalonUpdateDto
import com.bjit.salon.service.entiry.Salon
import com.bjit.salon.service.exception.SalonNotFoundException
import com.bjit.salon.service.mapper.SalonMapper
import com.bjit.salon.service.repository.SalonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalTime

@SpringBootTest
class SalonServiceImplTest extends Specification {

    private SalonServiceImpl salonService
    @Autowired
    private SalonMapper salonMapper
    private SalonRepository salonRepository


    def setup() {
        salonRepository = Mock(SalonRepository)
        salonService = new SalonServiceImpl(salonRepository, salonMapper)
    }

    def "should create a new salon"() {
        given:
        def startTime = LocalTime.parse("10:00:00")
        def endTime = startTime
                .plusHours(1)
                .plusMinutes(40)

        def salonRequest = SalonCreateDto.builder()
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .userId(1L)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()

        def salon = Salon.builder()
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

        salonRepository.save(_) >> salon

        when:
        def salonResponseDto = salonService.create(salonRequest)

        then:
        salonResponseDto.getName() == "abc salon"
        salonResponseDto.getDescription() == "Best Salon of Dhaka City"
        salonResponseDto.getAddress() == "Dhaka"
        salonResponseDto.getClosingTime() == LocalTime.parse("11:40:00")
        salonResponseDto.getOpeningTime() == LocalTime.parse("10:00:00")
        salonResponseDto.getUserId() == 1
        salonResponseDto.getReviews() == (double) 0.0
        salonResponseDto.getContractNumber() == "4738478"
    }

    def "should update salon"() {
        given:
        def startTime = LocalTime.parse("10:00:00")
        def endTime = startTime
                .plusHours(1)
                .plusMinutes(40)

        def salon = Salon.builder()
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

        def salonUpdateRequest = SalonUpdateDto.builder()
                .name("abc salon")
                .description("Best Salon of Dhaka City")
                .address("Dhaka")
                .id(1L)
                .closingTime(endTime)
                .openingTime(startTime)
                .contractNumber("4738478")
                .build()

        salonRepository.findById(1L) >> Optional.of(salon)
        salonRepository.save(_) >> salon

        when:
        def salonResponseDto = salonService.update(salonUpdateRequest)

        then:
        salonResponseDto.getName() == "abc salon"
        salonResponseDto.getDescription() == "Best Salon of Dhaka City"
        salonResponseDto.getAddress() == "Dhaka"
        salonResponseDto.getClosingTime() == LocalTime.parse("11:40:00")
        salonResponseDto.getOpeningTime() == LocalTime.parse("10:00:00")
        salonResponseDto.getUserId() == 1
        salonResponseDto.getReviews() == (double) 0.0
        salonResponseDto.getContractNumber() == "4738478"

    }

    def "should return a salon object by salon id"() {

        given:
        def startTime = LocalTime.parse("10:00:00")
        def endTime = startTime
                .plusHours(1)
                .plusMinutes(40)
        
        def salon = Salon.builder()
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
        
        salonRepository.findById(1L) >> Optional.of(salon)

        when:
        def salonResponseDto = salonService.getSalon(1L)

        then:
        salonResponseDto.getName() == "abc salon"
        salonResponseDto.getDescription() == "Best Salon of Dhaka City"
        salonResponseDto.getAddress() == "Dhaka"
        salonResponseDto.getClosingTime() == LocalTime.parse("11:40:00")
        salonResponseDto.getOpeningTime() == LocalTime.parse("10:00:00")
        salonResponseDto.getUserId() == 1
        salonResponseDto.getReviews() == (double) 0.0
        salonResponseDto.getContractNumber() == "4738478"

    }

    def "should throw salon not found exception by salon id"(){
        given:
        salonRepository.findById(1L) >> {throw new SalonNotFoundException("salon not found")}
        when:
        salonService.getSalon(1L)
        then:
        def exception = thrown(SalonNotFoundException)
        exception.message == "salon not found"

    }

    def "should return a list of salons"() {

        given:
        def startTime = LocalTime.parse("10:00:00")
        def endTime = startTime
                .plusHours(1)
                .plusMinutes(40)

        def salon =  Salon.builder()
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

        def salons = [salon, salon, salon, salon, salon]
        salonRepository.findAll() >> salons

        when:
        def size = salonService.getAllSalon().size()

        then:
        size == 5

    }

    def "should return salon by any query string"() {

        given:
        def startTime = LocalTime.parse("10:00:00")
        def endTime = startTime
                .plusHours(1)
                .plusMinutes(40)

        def salon = Salon.builder()
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

        def salons = [salon, salon]

        salonRepository.findByNameContainingIgnoreCase("abc") >> salons

        when:
        def size = salonService.getSalonsByQuery("abc").size()

        then:
        size == 2
    }
}
