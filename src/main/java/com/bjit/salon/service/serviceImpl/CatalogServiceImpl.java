package com.bjit.salon.service.serviceImpl;

import com.bjit.salon.service.dto.request.CatalogCreateDto;
import com.bjit.salon.service.dto.response.CatalogResponseDto;
import com.bjit.salon.service.entity.Catalog;
import com.bjit.salon.service.exception.CatalogNotFoundException;
import com.bjit.salon.service.mapper.CatalogMapper;
import com.bjit.salon.service.repository.CatalogRepository;
import com.bjit.salon.service.service.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    @Override
    public CatalogResponseDto createNewCatalog(CatalogCreateDto catalogDto) {
        Catalog catalog = catalogRepository.save(catalogMapper.toCatalog(catalogDto));
        return catalogMapper.toCatalogResponse(catalog);
    }

    @Override
    public CatalogResponseDto getCatalog(long id) {
        Catalog catalog = getCatalogByCatalogId(id);
        return catalogMapper.toCatalogResponse(catalog);
    }

    @Override
    public List<CatalogResponseDto> getAllCatalog() {
        return catalogMapper.toCatalogsResponse(catalogRepository.findAll());
    }

    @Override
    public List<CatalogResponseDto> getAllCatalogBySalon(long salonId) {
        return catalogMapper.toCatalogsResponse(catalogRepository.findAllBySalonId(salonId));
    }

    private Catalog getCatalogByCatalogId(Long id) {
        Optional<Catalog> catalog = catalogRepository.findById(id);
        if(catalog.isEmpty()){
            throw new CatalogNotFoundException("Catalog not found for id: " + id);
        }
        return catalog.get();
    }
}
