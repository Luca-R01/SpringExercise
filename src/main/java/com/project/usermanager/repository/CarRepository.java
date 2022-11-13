package com.project.usermanager.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.usermanager.model.CarEntity;

@Repository
public interface CarRepository extends MongoRepository<CarEntity, ObjectId> {
    
    Optional<CarEntity> findByLicensePlate(String licensePlate);

    List<CarEntity> findAllByOwnerFiscalCode(String ownerFiscalCode);

}
