package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.PaymentTransaction;

public interface PaymentTransactionRepository extends MongoRepository<PaymentTransaction,String > {
}
