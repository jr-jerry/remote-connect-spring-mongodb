package com.mongo_db.connect.Repository;

import com.mongo_db.connect.Entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepo extends MongoRepository<StudentEntity,String> {
    Optional<StudentEntity> findByName(String name);
}
