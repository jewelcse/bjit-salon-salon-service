package com.bjit.salon.service.repository;

import com.bjit.salon.service.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonRepository extends JpaRepository<Salon,Long> {

    List<Salon> findByNameContainingIgnoreCase(String str);
}
