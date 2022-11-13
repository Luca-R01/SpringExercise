package com.project.usermanager.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.usermanager.model.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    public Optional<UserEntity> findByFiscalCode(String fiscalCode);
    
}
