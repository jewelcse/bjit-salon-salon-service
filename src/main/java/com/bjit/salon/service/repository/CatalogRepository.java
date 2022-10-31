package com.bjit.salon.service.repository;

import com.bjit.salon.service.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Long> {
    List<Catalog> findAllBySalonId(long salonId);
}
