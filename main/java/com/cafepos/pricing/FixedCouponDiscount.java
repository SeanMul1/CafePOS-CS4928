package com.cafepos.pricing;

import com.cafepos.common.Money;

public final class FixedCouponDiscount implements DiscountPolicy {
    private final Money amount;

    public FixedCouponDiscount(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money discountOf(Money subtotal) {
        // cap at subtotal
        if (amount.amount().compareTo(subtotal.amount()) > 0)
            return subtotal;
        return amount;
    }
}
