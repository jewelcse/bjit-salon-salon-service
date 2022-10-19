package com.bjit.salon.service.serviceImpl

import com.bjit.salon.service.dto.request.CatalogCreateDto
import com.bjit.salon.service.dto.response.CatalogResponseDto
import com.bjit.salon.service.entiry.Catalog
import com.bjit.salon.service.exception.CatalogNotFoundException
import com.bjit.salon.service.mapper.CatalogMapper
import com.bjit.salon.service.repository.CatalogRepository
import spock.lang.Specification

class CatalogServiceImplTest extends Specification {

    private CatalogServiceImpl catalogService;
    private CatalogMapper catalogMapper;
    private CatalogRepository catalogRepository;

    def setup() {
        catalogMapper = Mock(CatalogMapper)
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

        def catalogResponse = CatalogResponseDto
                .builder()
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        catalogMapper.toCatalog(catalogRequest) >> catalog
        catalogRepository.save(catalog) >> catalog
        catalogMapper.toCatalogResponse(catalog) >> catalogResponse


        when:
        def db = catalogService.createNewCatalog(catalogRequest)

        then:
        db.getName() == "Normal Haircut"
        db.getDescription() == "It's very normal hair cut for men"
        db.getPayableAmount() == (double) 100.0
        db.getApproximateTimeForCompletion() == 60

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

        def catalogResponse = CatalogResponseDto
                .builder()
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        catalogRepository.findById(1L) >> Optional.of(catalog)
        catalogMapper.toCatalogResponse(catalog) >> catalogResponse

        when:
        def db = catalogService.getCatalog(1L)


        then:
        db.getName() == "Normal Haircut"
        db.getDescription() == "It's very normal hair cut for men"
        db.getPayableAmount() == (double) 100.0
        db.getApproximateTimeForCompletion() == 60

    }

    def "should return a list of catalog"(){
        given:
        given:

        def catalog = Catalog
                .builder()
                .id(1L)
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        def catalogResponse = CatalogResponseDto
                .builder()
                .name("Normal Haircut")
                .description("It's very normal hair cut for men")
                .approximateTimeForCompletion(60)
                .payableAmount(100.0)
                .build()

        def catalogs = [catalog,catalog,catalog,catalog,catalog]
        def catalogsResponse = [catalogResponse,catalogResponse,catalogResponse,catalogResponse,catalogResponse]
        catalogRepository.findAll() >> catalogs
        catalogMapper.toCatalogsResponse(catalogs) >> catalogsResponse

        when:
        def size = catalogService.getAllCatalog().size()

        then:
        size == 5
    }

    def "should throw catalog not found exception"(){
        given:
        catalogRepository.findById(1L) >> {  throw new CatalogNotFoundException("catalog not found") }

        when:
        catalogService.getCatalog(1L)

        then:
        def ex = thrown(CatalogNotFoundException)
        ex.message == "catalog not found"
    }

}
