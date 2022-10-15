# DeckCards-API-Framework
# DeckCards-RestAssured-Framework
create an dedicated project to test one endpoint for the following pubic API

https://deckofcardsapi.com/

Endpoint that have to be covered is related to 'Draw a Card' functionality and have the following interface

https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/?count=2

Details of the API and this particular endpoint can be found at the provided URL
The goal of task is to cover mentioned endpoint API with JUnit tests reflecting the following technology stack
- Java + JUnit (JUnit 5 is preferable, but JUnit 4 is also fine)
- RestAssured framework for send/validate requests
- Maven as dependency management tool and independent test runner

Final goal is to provide project that can be cloned locally and that should be able to execute all tests by using maven command like

mvn clean test
