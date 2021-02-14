package com.crud.library.repository;

import com.crud.library.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findAll();
    Optional<Rental> findByVolume_Id(Long volumeId);
    Optional<Rental> findById(Long id);
}
