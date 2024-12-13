package ormanindia.orman.controllers;

import ormanindia.orman.models.PaymentTransaction;
import ormanindia.orman.services.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paymenttransactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @Autowired
    public PaymentTransactionController(PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentTransaction> createPaymentTransaction(@RequestBody PaymentTransaction paymentTransaction) {
        PaymentTransaction createdPaymentTransaction = paymentTransactionService.createPaymentTransaction(paymentTransaction);
        return ResponseEntity.status(201).body(createdPaymentTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransaction> getPaymentTransactionById(@PathVariable String id) {
        Optional<PaymentTransaction> paymentTransaction = paymentTransactionService.getPaymentTransactionById(id);
        return paymentTransaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PaymentTransaction>> getAllPaymentTransactions() {
        List<PaymentTransaction> paymentTransactions = paymentTransactionService.getAllPaymentTransactions();
        return ResponseEntity.ok(paymentTransactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTransaction> updatePaymentTransaction(@PathVariable String id, @RequestBody PaymentTransaction paymentTransaction) {
        PaymentTransaction updatedPaymentTransaction = paymentTransactionService.updatePaymentTransaction(id, paymentTransaction);
        return updatedPaymentTransaction != null ? ResponseEntity.ok(updatedPaymentTransaction)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentTransaction(@PathVariable String id) {
        boolean deleted = paymentTransactionService.deletePaymentTransaction(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
