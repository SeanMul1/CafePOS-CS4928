package com.cafepos.demo;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CardPayment;
import com.cafepos.command.*;

public final class Week8Demo {
    public static void main(String[] args) {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(3);

        remote.setSlot(0, new AddItemCommand(service, "ESP+SHOT+OAT", 1));
        remote.setSlot(1, new AddItemCommand(service, "LAT+L", 2));
        remote.setSlot(2, new PayOrderCommand(service, new CardPayment("1234567890123456"), 10));

        System.out.println("=== Command Pattern Demo ===");
        remote.press(0);
        remote.press(1);
        System.out.println("--- Undoing last action ---");
        remote.undo(); // remove last add
        remote.press(1); // add again
        remote.press(2); // pay

        System.out.println("Final order total: " + order.totalWithTax(10));
    }
}