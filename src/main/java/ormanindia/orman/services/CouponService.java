package ormanindia.orman.services;

import ormanindia.orman.models.Coupon;
import ormanindia.orman.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    // Create a new coupon
    public Coupon createCoupon(Coupon coupon) {
        if (coupon.getValidFrom().isAfter(coupon.getValidUntil())) {
            throw new IllegalArgumentException("Valid from date cannot be after valid until date.");
        }
        coupon.setActive(true); // Activate coupon by default
        return couponRepository.save(coupon);
    }

    // Get all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Get a coupon by ID
    public Optional<Coupon> getCouponById(String id) {
        return couponRepository.findById(id);
    }

    // Update an existing coupon
    public Coupon updateCoupon(String id, Coupon coupon) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            Coupon updatedCoupon = existingCoupon.get();
            updatedCoupon.setCode(coupon.getCode());
            updatedCoupon.setDiscountPercentage(coupon.getDiscountPercentage());
            updatedCoupon.setMaxDiscountAmount(coupon.getMaxDiscountAmount());
            updatedCoupon.setValidFrom(coupon.getValidFrom());
            updatedCoupon.setValidUntil(coupon.getValidUntil());
            updatedCoupon.setActive(coupon.isActive());
            return couponRepository.save(updatedCoupon);
        } else {
            throw new IllegalArgumentException("Coupon not found with ID: " + id);
        }
    }

    // Delete a coupon by ID
    public boolean deleteCoupon(String id) {
        if (couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Activate a coupon
    public Coupon activateCoupon(String id) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setActive(true);
            return couponRepository.save(coupon);
        } else {
            throw new IllegalArgumentException("Coupon not found with ID: " + id);
        }
    }

    // Deactivate a coupon
    public Coupon deactivateCoupon(String id) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            Coupon coupon = existingCoupon.get();
            coupon.setActive(false);
            return couponRepository.save(coupon);
        } else {
            throw new IllegalArgumentException("Coupon not found with ID: " + id);
        }
    }

    // Validate a coupon by code
    public boolean validateCoupon(String code) {
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        if (coupon.isPresent()) {
            Coupon c = coupon.get();
            return c.isActive() && !LocalDate.now().isBefore(c.getValidFrom()) && !LocalDate.now().isAfter(c.getValidUntil());
        }
        return false;
    }
}
