package ormanindia.orman.services;

import ormanindia.orman.models.Payment;
import ormanindia.orman.models.PaymentTransaction;
import ormanindia.orman.models.Restaurant;
import ormanindia.orman.repositories.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentTransactionService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;
    @Autowired
    private RestaurantService restaurantService;

    public PaymentTransaction createPaymentTransaction(PaymentTransaction paymentTransaction,String restaurantId) {
        Restaurant restaurant=restaurantService.getRestaurantById(restaurantId);
        PaymentTransaction saved=paymentTransactionRepository.save(paymentTransaction);
        if (restaurant!=null) {
            Payment payment=restaurant.getPayment();
            payment.setTotalAmount(payment.getTotalAmount()-paymentTransaction.getAmountPaid());
            payment.setPaidAmount(payment.getPaidAmount()+paymentTransaction.getAmountPaid());
            payment.setPendingAmount(payment.getPendingAmount()-paymentTransaction.getAmountPaid());
            payment.getPastSettlements().add(paymentTransaction);

        }
        return saved;

    }

   

    public List<PaymentTransaction> getAllPaymentTransactions() {
        return paymentTransactionRepository.findAll();
    }

    public PaymentTransaction updatePaymentTransaction(String id, PaymentTransaction paymentTransaction) {
        if (paymentTransactionRepository.existsById(id)) {
            paymentTransaction.setId(id);
            return paymentTransactionRepository.save(paymentTransaction);
        }
        return null;
    }

    public boolean deletePaymentTransaction(String id) {
        if (paymentTransactionRepository.existsById(id)) {
            paymentTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
