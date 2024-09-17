# Ethereum Deposit Tracker 

## ETH Deposit Tracker - Java-based Application
The ETH Deposit Tracker is a Java-based application that monitors Ethereum deposits on the blockchain. It tracks deposit transactions, stores them in a MongoDB database, and sends notifications via Telegram when new deposits are detected.

## Prerequisites
Before you start, ensure you have the following installed:

Java: Version 11 or later.
Maven: For dependency management and project build.
MongoDB: Ensure MongoDB is installed and running on the default port 27017.
MongoDB Installation
If you don't have MongoDB installed, you can install it using the following steps:


In the project directory, you can run:

mvn clean install
Compiles the Java code and installs the dependencies.

mvn exec:java
Runs the ETH Deposit Tracker application in development mode.

## Architecture
This project was developed using Clean Architecture principles along with SOLID design principles. The Clean Architecture approach ensures separation of concerns and independence from frameworks, making the system more maintainable, scalable, and testable. The application is structured into layers:

1. Entities
Core business logic and domain models.
In this case, the Deposit class will hold information related to Ethereum deposits (e.g., blockNumber, timestamp, fee, etc.).

2. Use Cases
Application-specific business rules.
The logic to track Ethereum deposits, process them, and store them in MongoDB resides here.

3. Interface Adapters
Presenters, controllers, and gateways.
These are responsible for connecting the core application logic to the frameworks (e.g., MongoDB repository or RPC interactions with Ethereum).

4. Frameworks and Drivers
External frameworks and tools (e.g., MongoDB, RPC frameworks, Telegram Bot API) reside in this layer. The application can use libraries like Web3j for blockchain interactions and MongoDB Java drivers for database operations.

## SOLID Principles in Java
Single Responsibility Principle (SRP): Each class and module has a single, well-defined responsibility. For instance, the DepositService class handles all deposit-related logic, while MongoDBService handles database interactions.

Open-Closed Principle (OCP): The system is open for extension but closed for modification. You can extend functionality, such as tracking new blockchains, without modifying core components.

Liskov Substitution Principle (LSP): Subclasses can replace superclass objects. For instance, if you decide to add other blockchain services, they can easily be substituted in place of Ethereum tracking.

Interface Segregation Principle (ISP): Clients are not forced to depend on interfaces they do not use. The interfaces will only expose the necessary methods required by specific services.

Dependency Inversion Principle (DIP): High-level modules depend on abstractions rather than low-level modules. For example, interfaces will abstract MongoDB and Ethereum interactions, ensuring that these dependencies can be swapped without changing the business logic.

## Flexibility
The ETH Deposit Tracker was designed with flexibility in mind. It can be easily configured to listen for any token from any blockchain by configuring a context for each one. This modular approach allows for easy expansion to support multiple cryptocurrencies and blockchains without significant changes to the core architecture.

## Frameworks and Drivers Layer
This layer will contain the integrations with MongoDB and the Ethereum RPC. In this case, we are using MongoDB Java drivers and Web3j for Ethereum interaction. Telegram notifications can be added using Java libraries like TelegramBots.


Bottocken: HTTP API:
7332969409:AAEfD5QrAI4VBJ-FpQZ3Dx-sr7guEMaSRVU

RPC INFRA: c1c3cbaab7a944df9fe3b9665c38b271
