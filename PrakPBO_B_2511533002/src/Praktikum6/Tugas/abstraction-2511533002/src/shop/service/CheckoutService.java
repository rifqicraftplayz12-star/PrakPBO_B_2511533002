package shop.service;

import java.util.List;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {

    public long subtotal(List<Product> items) {
        long total = 0;
        for (Product product : items) {
            total += product.getFinalPrice();
        }
        return total;
    }

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable shippable) {
                grams += shippable.getWeightGrams();
            }
        }
        return grams;
    }

    public long total(List<Product> items, ShippingService shipping) {
        long sub = subtotal(items);
        int weight = totalWeight(items);
        long shippingCost = (shipping != null) ? shipping.calculateCost(weight) : 0;
        return sub + shippingCost;
    }

    public boolean checkout(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        if (items == null || items.isEmpty()) {
        return false;
        }
        long finalTotal = grandTotal(items, shipping, payment);
        return payment.pay(finalTotal);
    }

    public boolean refund(PaymentMethod payment, long amount) {
        if (!(payment instanceof RefundablePayment refundablePayment)) {
            return false;
        }
        return refundablePayment.refund(amount);
    }

    public long grandTotal(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        long baseTotal = total(items, shipping);
        return baseTotal + payment.adminFee(baseTotal);
    }
}