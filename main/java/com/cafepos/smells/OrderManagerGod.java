package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10;
    public static String LAST_DISCOUNT_CODE = null;

    // God Class & Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
    public static String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) {
        // Primitive Obsession: discountCode strings
        // Primitive Obsession: TAX_PERCENT as primitive
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.Priced p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        if (qty <= 0) qty = 1;
        Money subtotal = unitPrice.multiply(qty);
        Money discount = Money.zero();
        // Primitive Obsession: discountCode strings; magic numbers for rates
        if (discountCode != null) {
            // Shotgun Surgery risk: each discount rule embedded inline
            if (discountCode.equalsIgnoreCase("LOYAL5")) {
                // Duplicated Logic: Money and BigDecimal manipulations scattered inline
                discount = Money.of(subtotal.amount().multiply(java.math.BigDecimal.valueOf(5)).divide(java.math.BigDecimal.valueOf(100)));
            } else if (discountCode.equalsIgnoreCase("COUPON1")) {
                discount = Money.of(1.00);
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            // Global/Static State: LAST_DISCOUNT_CODE is global
            LAST_DISCOUNT_CODE = discountCode;
        }
        // Duplicated Logic: Money and BigDecimal manipulations scattered inline
        Money discounted = Money.of(subtotal.amount().subtract(discount.amount()));
        if (discounted.amount().signum() < 0) discounted = Money.zero();
        // Shotgun Surgery risk: tax computation embedded inline
        var tax = Money.of(discounted.amount().multiply(java.math.BigDecimal.valueOf(TAX_PERCENT)).divide(java.math.BigDecimal.valueOf(100)));
        var total = discounted.add(tax);
        // Feature Envy: payment logic embedded inline
        if (paymentType != null) {
            // Primitive Obsession: paymentType strings
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }
        // Duplicated Logic: receipt formatting scattered inline
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.amount().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        // Primitive Obsession: TAX_PERCENT used directly in string
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}