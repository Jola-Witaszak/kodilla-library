package com.crud.library.repository;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import com.crud.library.domain.Volume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolumeRepository extends CrudRepository<Volume, Long> {
    List<Volume> findAllByStatus(Status status);
}
