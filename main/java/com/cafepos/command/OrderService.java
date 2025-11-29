
package com.cafepos.command;

import com.cafepos.catalog.Product;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.payment.PaymentStrategy;

public final class OrderService {
    private final ProductFactory factory = new ProductFactory();
    private final Order order;

    public OrderService(Order order) {
        this.order = order;
    }

    public void addItem(String recipe, int qty) {
        Product p = factory.create(recipe);
        order.addItem(new LineItem(p, qty));
        System.out.println("[Service] Added " + p.name() + " x" + qty);
    }

    public void removeLastItem() {
        var items = order.items();
        if (!items.isEmpty()) {
            var last = items.get(items.size()-1);
            // Create a new list without the last item
            var newItems = new java.util.ArrayList<>(items);
            newItems.remove(newItems.size()-1);

            // Use reflection to replace items (since Order.items is immutable)
            try {
                var field = Order.class.getDeclaredField("items");
                field.setAccessible(true);
                field.set(order, newItems);
            } catch (Exception e) {
                throw new IllegalStateException("Could not remove item reflectively; adapt your Order API.");
            }
            System.out.println("[Service] Removed last item: " + last.product().name());
        }
    }

    public Money totalWithTax(int percent) {
        return order.totalWithTax(percent);
    }

    public void pay(PaymentStrategy strategy, int taxPercent) {
        var total = order.totalWithTax(taxPercent);
        strategy.pay(order);
        System.out.println("[Service] Payment processed for total " + total);
    }

    public Order order() {
        return order;
    }
}