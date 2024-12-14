package ormanindia.orman.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Coupon;

public interface CouponRepository extends MongoRepository<Coupon,String > {
    Optional<Coupon> findByCode(String code);
}
