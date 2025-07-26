package com.cityzen.lockermanagementservice.repository;

import com.cityzen.lockermanagementservice.entity.Locker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LockerRepo extends MongoRepository<Locker, Long> {
    Optional<Locker> findByAadharNumber(String aadharNumber);
}
