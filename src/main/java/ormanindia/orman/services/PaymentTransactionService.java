package ormanindia.orman.services;

import ormanindia.orman.models.PaymentTransaction;
import ormanindia.orman.repositories.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentTransactionService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransaction createPaymentTransaction(PaymentTransaction paymentTransaction) {
        return paymentTransactionRepository.save(paymentTransaction);
    }

    public Optional<PaymentTransaction> getPaymentTransactionById(String id) {
        return paymentTransactionRepository.findById(id);
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
