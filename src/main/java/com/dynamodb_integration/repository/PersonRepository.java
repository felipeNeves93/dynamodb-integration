package com.dynamodb_integration.repository;

import com.dynamodb_integration.entity.PersonEntity;
import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PersonRepository extends CrudRepository<PersonEntity, String> {

  List<PersonEntity> findByFirstName(String firstName);

}