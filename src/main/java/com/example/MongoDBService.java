package com.example;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBService {
    private static final String DATABASE_NAME = "ethereum";
    private static final String COLLECTION_NAME = "deposits";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBService() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public void saveDeposit(Deposit deposit) {
        Document document = new Document("blockNumber", deposit.getBlockNumber())
                .append("blockTimestamp", deposit.getBlockTimestamp())
                .append("fee", deposit.getFee())
                .append("hash", deposit.getHash())
                .append("pubkey", deposit.getPubkey());
        collection.insertOne(document);
    }
}
