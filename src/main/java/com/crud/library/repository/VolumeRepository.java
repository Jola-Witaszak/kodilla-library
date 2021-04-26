package com.crud.library.repository;

import com.crud.library.domain.Volume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeRepository extends CrudRepository<Volume, Long> {
}
