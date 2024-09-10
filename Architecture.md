## Architecture
This Java-based implementation follows Clean Architecture principles along with the SOLID design principles. This ensures separation of concerns and independence of frameworks, making the system more maintainable, scalable, and testable. The application is structured into the following layers:

Entities: Core business logic and domain models.
Use Cases: Application-specific business rules.
Interface Adapters: Presenters, controllers, and gateways.
Frameworks and Drivers: External frameworks and tools (e.g., MongoDB, web framework).

## SOLID principles applied:

Single Responsibility Principle: Each class and module has a single, well-defined responsibility.
Open-Closed Principle: The system is open for extension but closed for modification.
Liskov Substitution Principle: Objects of a superclass are replaceable with objects of its subclasses without affecting the correctness of the program.

Interface Segregation Principle: Clients are not forced to depend on interfaces they do not use.
Dependency Inversion Principle: High-level modules do not depend on low-level modules. Both depend on abstractions.

Flexibility
The ETH Deposit Tracker was designed with flexibility in mind. It can be easily configured to listen for any token from any blockchain by configuring the context for each one. This modular approach allows for easy expansion to support multiple cryptocurrencies and blockchains without significant changes to the core architecture.

## Project Structure

|-- src
|   |-- main
|   |   |-- java
|   |   |   |-- com.example.
|   |   |   |   |-- entity
|   |   |   |   |   |-- Deposit.java
|   |   |   |   |-- service
|   |   |   |   |   |-- DepositService.java
|   |   |   |   |-- controller
|   |   |   |   |   |-- EthereumDepositTracker.jav
|   |   |
|-- pom.xml


## Key Files

Deposit.java: This represents the Ethereum deposit entity, containing fields such as blockNumber, blockTimestamp, fee, hash, and pubkey.

DepositService.java: Contains the business logic to fetch Ethereum transactions from the Beacon Deposit contract using web3j, filter deposits, and store them in MongoDB.

EthereumDepositTracker.java: The entry point of the application. It monitors the blockchain for deposits, initiates the service to process transactions, and triggers notifications.

application.properties: Contains the configurations for MongoDB and other application settings.

