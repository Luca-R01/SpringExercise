package com.project.usermanager.repository.deleted;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.usermanager.model.deleted.UserEntityDeleted;

@Repository
public interface UserDeletedRepository extends MongoRepository<UserEntityDeleted, ObjectId> {
    
}
