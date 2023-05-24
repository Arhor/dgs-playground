## EngX

1. SOLID, KISS, DRY and other development principles.
2. Testing. What types of tests exist? What types did you use in the task? Why your tests are unit/integration?

## Java

1. What is Lambda and Functional Interface?
2. Stream API. What is the difference between using streams and classic loops? What helped the emergence of streams possible in Java 8? Is it the best solution to use parallel stream wherever possible?
3. Why we need default methods?
4. How to create an immutable object/class?

## Git

1. What does vcs mean? What vcs do you know? Name at least 2. Tell about the differences.
2. Why do we need vcs? What principles are they built upon?
3. What is rebasing, merging, cherry-picking? What is the difference between them?
4. Can you have multiple remote repositories for one local? Explain the answer.
5. What are hooks? How to use them? Where to find samples? What are they commonly used for?
6. What are merge conflicts? How to deal with them?

## Build Tools

1. What are build tools? Why are they used?
2. What are a gradle project and task?
3. What is dependency management? Why is it needed?
   * What is dependency?
   * What is scope of a dependency (configuration)?
   * What is the difference between api and implementation scopes?
   * What is transitive dependencies?
4. What is the use of multi-project builds?
   * What do `allprojects` and `subprojects` sections mean?
5. What is the use of plugins?
6. What is gradle wrapper?

# CI/CD

1. What is CI/CD?
2. Please describe the main goals of CI/CD.
3. Describe Continuous Integration process?
4. What is Jenkins?
5. What is SonarQube?
6. What is JaCoCo?

## Spring

1. IoC & DI. What is the difference? Other IoC implementations.
2. How can we configure Spring context?
3. Spring Bean lifecycle.
4. Injection with annotations. What types exist? Advantages and disadvantages of those types? How did you choose the injection approach?
5. What is the difference between Repository, Component, Service and Controller annotations?
6. Spring Bean scopes. What scopes exist out of the box? Is it possible to create a custom scope?
7. How add several configurations in application (db, connection pool settings) and choose one of them at startup?
8. What is Spring Boot? Name advantages and disadvantages of Spring Boot.
9. What Spring Boot features do you know?
10. What are the differences between Spring Boot and Spring?
11. What are the Spring Boot properties/yml?
12. What are the Spring Boot starters? Name some of them and their purpose.
13. What is Spring Boot autoconfiguration?
14. What is Spring Boot Actuator used for?

# Security

1. What is Spring Security?
2. Describe the main principles of Spring Security and how you configure it in your application? How did you secure the application? How does security work internally? What level of your application is secured?
3. What does authentication and Authorization mean? What is deference?
4. What is Oauth2? What is Authorization Server, Resource Server? How to get oauth token?
5. What is OpenId Connect?
6. What is a Security context?
7. What is Security principal?
8. What is JWT? Describe JWT structure?
9. Why should you use JWT?
10. Is it possible to achieve stateless requests using JWT (do not worry that your request will be processed on a machine where there is no previously created session)?
11. Is it possible to achieve the same stateless result with session stored outside the server?
12. What is CSRF protection, why is it needed? What is the purpose of the CSRF attack? How to protect application from CSRF?

## General

1. Describe, how HTTP request is processing (from “SEND” button in Postman to Rest Controller in your application).
2. Describe application structure.

## REST

1. HTTP methods for Rest API.
2. What does Richardson's maturity model describes?
3. Idempotency of methods. Idempotent REST methods.
4. Do you know some practices for API versioning?
5. Why we should support filtering, sorting, and pagination in RESTful APIs?
6. What are filtering, sorting, and pagination best practices can you name?
7. What is Richardson Maturity Model? Name the Levels of the Model and explain them.
8. What is HATEOAS? Why do we need it?
9. What are the basic principles and alternatives for Rest Assured?

## DB

1. What is DB index? How does it work?
2. Types of keys. Why we need a key?
3. What is transaction? What is ACID? What is Isolation levels (reading)? What propagation types do you know?

## JPA

1. What is the Java Persistence API?
2. What is the object-relational mapping?
3. What are the advantages and disadvantages of JPA?
4. What id generation strategies can you name? Can you describe their pros and cons?
5. What JPA inheritance types can you name? Can you describe them?
6. What fetch strategies do you know? What is the default? How to reproduce LazyInitializationException?
7. What cascade types do you know? Is it possible to insert an entity using cascade?
8. When do we need property `orphalRemoval`?
9. What entity states do you know?
10. What is the difference between methods find vs getReference?
11. What query types do you know? Can you name their pros and cons?
12. Can we update an entity without calling merge method?
