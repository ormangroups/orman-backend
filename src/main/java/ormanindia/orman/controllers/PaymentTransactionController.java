package ormanindia.orman.controllers;

import ormanindia.orman.models.PaymentTransaction;
import ormanindia.orman.services.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-transactions")
public class PaymentTransactionController {

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    // Create a new payment transaction
    @PostMapping("/{restaurantId}")
    public ResponseEntity<PaymentTransaction> createPaymentTransaction(
            @RequestBody PaymentTransaction paymentTransaction,
            @PathVariable String restaurantId) {
        PaymentTransaction createdTransaction = paymentTransactionService.createPaymentTransaction(paymentTransaction, restaurantId);
        return ResponseEntity.ok(createdTransaction);
    }

    // Get all payment transactions
    @GetMapping
    public ResponseEntity<List<PaymentTransaction>> getAllPaymentTransactions() {
        List<PaymentTransaction> transactions = paymentTransactionService.getAllPaymentTransactions();
        return ResponseEntity.ok(transactions);
    }

    // Update a payment transaction
    @PutMapping("/{id}")
    public ResponseEntity<PaymentTransaction> updatePaymentTransaction(
            @PathVariable String id,
            @RequestBody PaymentTransaction paymentTransaction) {
        PaymentTransaction updatedTransaction = paymentTransactionService.updatePaymentTransaction(id, paymentTransaction);
        if (updatedTransaction != null) {
            return ResponseEntity.ok(updatedTransaction);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a payment transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentTransaction(@PathVariable String id) {
        boolean isDeleted = paymentTransactionService.deletePaymentTransaction(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
