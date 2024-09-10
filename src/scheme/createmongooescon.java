import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "deposit")
public class createmongooescon {

    @Id
    private String id; 

    private String blockNumber;
    private String blockTimestamp;
    private String fee;
    private String hash;
    private String pubkey;
    private String blockchain;
    private String network;
    private String token;

    
}