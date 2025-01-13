package com.dynamodb_integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.dynamodb_integration.entity.PersonEntity;
import com.dynamodb_integration.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = DynamoDBTestConfig.class)
public class PersonRepositoryIntegrationTest {

  @Autowired
  public static LocalStackContainer localStackContainer;

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @Autowired
  private PersonRepository personRepository;

  @BeforeEach
  void setup() {
    amazonDynamoDB.createTable(new CreateTableRequest()
        .withTableName("Person")
        .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
        .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
        .withBillingMode(BillingMode.PAY_PER_REQUEST));
  }

  @AfterEach
  void tearDown() {
    amazonDynamoDB.deleteTable(new DeleteTableRequest().withTableName("Person"));
  }

  @Test
  void shouldSaveAndFindPerson() {
    var person = new PersonEntity();
    person.setFirstName("Felipe");
    person.setLastName("Neves");

    person = personRepository.save(person);

    var searchedPerson = personRepository.findById(person.getId());

    assertThat(searchedPerson).isPresent();
    assertThat(searchedPerson.get().getId()).isEqualTo(person.getId());
  }

  @Test
  void shouldFindByFirstName() {
    var person = new PersonEntity();
    person.setFirstName("Felipe");
    person.setLastName("Neves");

    var person2 = new PersonEntity();
    person2.setFirstName("Felipe");
    person2.setLastName("Neves2");

    personRepository.save(person);
    personRepository.save(person2);

    var searchedPersons = personRepository.findByFirstName("Felipe");

    assertThat(searchedPersons).isNotEmpty().hasSize(2);

  }

}
