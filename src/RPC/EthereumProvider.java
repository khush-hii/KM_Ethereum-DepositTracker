import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpWeb3j;

import java.net.URL;

// Interface for Ethereum Provider (implements IBlockchainProvider)
public class EthereumProvider implements IBlockchainProvider {

    private final Web3j web3j;

    public EthereumProvider(EthereumGatewayConfig config) throws Exception {
        String fullRpcUrl = String.format("%s/v2/%s", config.getRpcUrl(), config.getApiKey());
        web3j = Web3j.build(new HttpWeb3j(new URL(fullRpcUrl)));
        if (config.getNetwork() != null) {
            web3j.net.setNetwork(config.getNetwork().toString()).send();
        }
    }

    @Override
    public org.web3j.protocol.core.methods.response.Transaction getTransaction(String txHash) throws Exception {
        return web3j.ethGetTransaction.send(txHash).getTransaction();
    }

    @Override
    public org.web3j.protocol.core.methods.response.Block getBlock(String blockNumberOrHash) throws Exception {
        return web3j.ethGetBlock.send(blockNumberOrHash, true).getBlock();
    }

    @Override
    public Long getBlockNumber() throws Exception {
        return web3j.ethBlockNumber.send().getBlockNumber().longValue();
    }

    // Implement getTransactionTrace method (optional)
    @Override
    public Object getTransactionTrace(String txHash, Object options) throws Exception {
        throw new UnsupportedOperationException("getTransactionTrace not implemented yet");
    }

    @Override
    public void on(String event, Consumer<Object> listener) {
        // Web3j doesn't have a generic on method, handle specific events if needed
        if (event.equals("newBlock")) {
            web3j.filter().newBlockFilters().subscribe(filterId -> {
                web3j.ethFilter.getLogs(filterId).sendAsync().thenAccept(block -> listener.accept(block));
            });
        }
    }
}

// Interface for Ethereum Gateway configuration
interface Ethereumgate {
    String getRpcUrl();

    String getApiKey();

    ethers.Networkish getNetwork(); // Optional

    String getVersion(); // Optional

    String getMetadataNetwork();
}

// Ethereum-specific BlockchainGateway implementation
public class Ethereumgate extends Blockchaingate implements IBlockchainGateway {

    public static final String TOKEN = "ETH";

    private final String network;

    public Ethereumgatew(EthereumGatewayConfig config) throws Exception {
        super(new EthereumProvider(config));
        this.network = config.getMetadataNetwork();
        this.blockchain = "ethereum";
        this.token = TOKEN;
    }
}