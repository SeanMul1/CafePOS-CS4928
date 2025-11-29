package com.cafepos.pricing;

import com.cafepos.common.Money;

public class DiscountPolicyFactory {
    public static DiscountPolicy create(String discountCode) {
        if (discountCode == null) {
            return new NoDiscount();
        }

        switch (discountCode.toUpperCase()) {
            case "LOYAL5":
                return new LoyaltyPercentDiscount(5);
            case "COUPON1":
                return new FixedCouponDiscount(Money.of(1.00));
            case "NONE":
                return new NoDiscount();
            default:
                return new NoDiscount();
        }
    }
}
