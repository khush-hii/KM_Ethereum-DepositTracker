package com.example;

import java.io.IOException;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

public class DepositService {

    private static final String INFURA_URL = "https://mainnet.infura.io/v3/your-infura-project-id"; 
    private static final String BEACON_DEPOSIT_CONTRACT_ADDRESS = "0x00000000219ab540356cBB839Cbe05303d7705Fa";

    private final Web3j web3j;
    private final MongoDBService mongoDBService;

    public DepositService(MongoDBService mongoDBService) {
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
            List<EthLog.LogResult> logs = ethLog.getLogs();

            for (EthLog.LogResult logResult : logs) {
                processLog((EthLog.LogObject) logResult);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLog(EthLog.LogObject logObject) {
        String transactionHash = logObject.getTransactionHash();
        System.out.println("Processing deposit transaction: " + transactionHash);

        try {
            TransactionReceipt receipt = web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt().orElse(null);

            if (receipt != null) {
                String fromAddress = receipt.getFrom();
                String value = receipt.getCumulativeGasUsed().toString();
                String blockNumberStr = receipt.getBlockNumber().toString();
                String blockTimestampStr = receipt.getBlockNumber().toString();//"N/A"; // Placeholder for actual timestamp

                Deposit deposit = new Deposit(
                        blockNumberStr,
                        blockTimestampStr,
                        value,
                        transactionHash,
                        fromAddress
                );

                System.out.println("Deposit details:");
                System.out.println(deposit);

                mongoDBService.saveDeposit(deposit);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trackLatestBlock() {
        try {
            EthBlock latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            EthBlock.Block block = latestBlock.getBlock();

            System.out.println("Latest block number: " + block.getNumber());
            System.out.println("Block timestamp: " + block.getTimestamp());
            System.out.println("Block hash: " + block.getHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
