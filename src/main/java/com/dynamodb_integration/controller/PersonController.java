package com.dynamodb_integration.controller;

import com.dynamodb_integration.entity.PersonEntity;
import com.dynamodb_integration.repository.PersonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

  private final PersonRepository personRepository;

  @PostMapping
  public String save(@RequestBody PersonEntity personEntity) {
    var savedPerson = personRepository.save(personEntity);

    return savedPerson.getId();
  }

  @GetMapping("/{id}")
  public PersonEntity findById(@PathVariable("id") String id) {
    var searchedPerson = personRepository.findById(id);

    return searchedPerson.orElse(null);

  }

  @GetMapping("/find-by-first-name/{first-name}")
  public List<PersonEntity> findByName(@PathVariable("first-name") String firstName) {
    return personRepository.findByFirstName(firstName);
  }
}
