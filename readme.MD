## DYNAMO DB INTEGRATION EXAMPLE

* This project aims to show how to integrate AWS DynamoDB into a Spring Boot app using
  docker-compose,
  and a simple controller to interact with the database
* For the sake of simplicity, I did not implement separation of concerns in the layers *(
  controller -> service -> service)* nor
  DTOs in the controller layer
* The objective of this repo is to provide a simple skeleton for those aiming to integrate DynamoDB
  into their
  apps, and to show how to write integration tests to ensure that everything is working fine.