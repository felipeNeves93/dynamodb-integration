package com.dynamodb_integration.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = "Person")
@Setter
@AllArgsConstructor
public class PersonEntity {

  private String id;
  private String firstName;
  private String lastName;

  public PersonEntity() {
    this.id = UUID.randomUUID().toString();
  }

  @DynamoDBHashKey(attributeName = "id")
  public String getId() {
    return this.id;
  }

  @DynamoDBAttribute
  public String getFirstName() {
    return this.firstName;
  }

  @DynamoDBAttribute
  public String getLastName() {
    return this.lastName;
  }
}
