package com.bjit.salon.service.serviceImpl

import com.bjit.salon.service.dto.request.CatalogCreateDto
import com.bjit.salon.service.entity.Catalog
import com.bjit.salon.service.exception.CatalogNotFoundException
import com.bjit.salon.service.mapper.CatalogMapper
import com.bjit.salon.service.repository.CatalogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CatalogServiceImplTest extends Specification {

    private CatalogServiceImpl catalogService;
    @Autowired
    private CatalogMapper catalogMapper;
    private CatalogRepository catalogRepository;

    def setup() {
        catalogRepository = Mock(CatalogRepository)
        catalogService = new CatalogServiceImpl(catalogRepository, catalogMapper)
    }

    def "should create a new catalog"() {

        given:
        def catalogRequest = CatalogCreateDto
                .builder()
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        def catalog = Catalog
                .builder()
                .id(1L)
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        catalogRepository.save(_) >> catalog


        when:
        def catalogResponseDto = catalogService.createNewCatalog(catalogRequest)

        then:
        catalogResponseDto.getName() == "Normal Haircut"
        catalogResponseDto.getDescription() == "It's very normal hair cut for men"
        catalogResponseDto.getPayableAmount() == (double) 100.0
        catalogResponseDto.getApproximateTimeForCompletion() == 60

    }

    def "should return a catalog object by catalog id"(){

        given:
        def catalog = Catalog
                .builder()
                .id(1L)
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        catalogRepository.findById(1L) >> Optional.of(catalog)

        when:
        def catalogResponseDto = catalogService.getCatalog(1L)

        then:
        catalogResponseDto.getName() == "Normal Haircut"
        catalogResponseDto.getDescription() == "It's very normal hair cut for men"
        catalogResponseDto.getPayableAmount() == (double) 100.0
        catalogResponseDto.getApproximateTimeForCompletion() == 60

    }

    def "should throw catalog not found exception by catalog id"(){
        given:
        catalogRepository.findById(1L) >> Optional.ofNullable(null)

        when:
        catalogService.getCatalog(1L)

        then:
        def ex = thrown(CatalogNotFoundException)
        ex.message == "Catalog not found for id: 1"
    }

    def "should return a list of catalog"(){
        given:
        def catalog = Catalog
                .builder()
                .id(1L)
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()


        def catalogs = [catalog,catalog,catalog,catalog,catalog]
        catalogRepository.findAll() >> catalogs

        when:
        def size = catalogService.getAllCatalog().size()

        then:
        size == 5
    }



}
