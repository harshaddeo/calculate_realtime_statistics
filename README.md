# calculate_realtime_statistics

This is a spring boot application that shows the statistics for last 60 seconds.<br/>
Application is configurable in application.properties and is running on port 8080.

Technologies used<br/>
Spring Boot<br/>
Maven<br/>
Java 8

Design points considered<br/>
SOLID - single responsibility, open-closed, Liskov subsitution, interface segragation, dependency inversion principle<br/>
Clean and simple

Steps to run the project:<br/>

NOTE: Maven and Java 8 must be installed.<br/>

Command : mvn clean install spring-boot:run <br/>
Maven will load all the depdendencies and build the project(run the tests as well), and run the application.<br/>

Following endpoints are available:<br/>

GET - http://localhost:8080/transactions

POST - http://localhost:8080/statistics<br/>
Here, calculation is done in constant time O(1) for which an in-memory concurrent hash map has been used.

TESTING:<br/>
Application contains the unit tests. Command : mvn clean install - to show test results.<br/>
However, integration tests are not added for now. But can be added.
