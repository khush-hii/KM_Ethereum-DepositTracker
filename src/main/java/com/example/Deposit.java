package com.example;

public class Deposit {
    private String blockNumber;
    private String blockTimestamp;
    private String fee;
    private String hash;
    private String pubkey;

    // Constructor
    public Deposit(String blockNumber, String blockTimestamp, String fee, String hash, String pubkey) {
        this.blockNumber = blockNumber;
        this.blockTimestamp = blockTimestamp;
        this.fee = fee;
        this.hash = hash;
        this.pubkey = pubkey;
    }

    // Getters
    public String getBlockNumber() {
        return blockNumber;
    }

    public String getBlockTimestamp() {
        return blockTimestamp;
    }

    public String getFee() {
        return fee;
    }

    public String getHash() {
        return hash;
    }

    public String getPubkey() {
        return pubkey;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "blockNumber='" + blockNumber + '\'' +
                ", blockTimestamp='" + blockTimestamp + '\'' +
                ", fee='" + fee + '\'' +
                ", hash='" + hash + '\'' +
                ", pubkey='" + pubkey + '\'' +
                '}';
    }
}
