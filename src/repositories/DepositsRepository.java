import com.example.core.domain.deposit.Deposit; // Assuming Deposit class in core/domain/deposit package
import com.example.core.types.repositories.IDepositsRepository;
import com.example.core.types.services.GetDepositsProps;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepositsRepository implements IDepositsRepository {

    private final MongoTemplate mongoTemplate;

    public DepositsRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void storeDeposit(Deposit deposit) {
        try {
            mongoTemplate.save(deposit);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            // Handle duplicate key error
            System.out.println("Deposit with this hash already exists: " + deposit.getHash());
        } catch (Exception e) {
            System.err.println("Error storing deposit:", e);
            throw e;
        }
    }

    @Override
    public Long getLatestStoredBlock() {
        Query query = new Query().withLimit(1).withSorting(Sort.by(Sort.Direction.DESC, "blockNumber"));
        Deposit latestDeposit = mongoTemplate.findOne(query, Deposit.class);
        return latestDeposit != null ? latestDeposit.getBlockNumber() : null;
    }

    @Override
    public List<Deposit> getDeposits(GetDepositsProps props) {
        Criteria criteria = Criteria.where("blockchain").is(props.getBlockchain())
                .andOperator(
                        Criteria.where("network").is(props.getNetwork()),
                        Criteria.where("token").is(props.getToken())
                );
        if (props.getBlockTimestamp() != null) {
            criteria.andOperator(Criteria.where("blockTimestamp").gte(props.getBlockTimestamp()));
        }
        return mongoTemplate.find(Deposit.class, Query.query(criteria));
    }
}