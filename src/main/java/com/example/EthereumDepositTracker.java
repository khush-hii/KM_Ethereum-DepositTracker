package com.example;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;

public class EthereumDepositTracker {
    private static final String INFURA_URL = "https://mainnet.infura.io/v3/YOUR_INFURA_PROJECT_ID";
    private static final String BEACON_DEPOSIT_CONTRACT_ADDRESS = "0x00000000219ab540356cBB839Cbe05303d7705Fa";

    private final Web3j web3j;
    private final MongoDBService mongoDBService;

    public EthereumDepositTracker(MongoDBService mongoDBService) {
        this.web3j = Web3j.build(new HttpService(INFURA_URL));
        this.mongoDBService = mongoDBService;
    }

    public void trackDeposits() {
        try {
            EthFilter filter = new EthFilter(
                    DefaultBlockParameterName.EARLIEST,
                    DefaultBlockParameterName.LATEST,
                    BEACON_DEPOSIT_CONTRACT_ADDRESS
            );

            EthLog ethLog = web3j.ethGetLogs(filter).send();

            @SuppressWarnings("rawtypes")
            List<EthLog.LogResult> logs = ethLog.getLogs();
            for (@SuppressWarnings("rawtypes") EthLog.LogResult logResult : logs) {
                EthLog.LogObject log = (EthLog.LogObject) logResult;
                String transactionHash = log.getTransactionHash();

                // Fetch additional details like blockNumber and blockTimestamp
                String blockNumber = log.getBlockNumberRaw();
                String blockTimestamp = web3j.ethGetBlockByNumber(
                        DefaultBlockParameterName.valueOf(blockNumber), false).send().getBlock().getTimestampRaw();

                // Example fee calculation and pubkey extraction (dummy implementation)
                String fee = "0.0"; // Replace with actual fee calculation
                String pubkey = "0x0000000000000000000000000000000000000000"; // Replace with actual pubkey

                Deposit deposit = new Deposit(blockNumber, blockTimestamp, fee, transactionHash, pubkey);
                mongoDBService.saveDeposit(deposit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MongoDBService mongoDBService = new MongoDBService();
        EthereumDepositTracker tracker = new EthereumDepositTracker(mongoDBService);
        tracker.trackDeposits();
    }
}
