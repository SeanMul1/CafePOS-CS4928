package com.cafepos;

import com.cafepos.command.AddItemCommand;
import com.cafepos.command.OrderService;
import com.cafepos.command.PayOrderCommand;
import com.cafepos.command.PosRemote;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CashPayment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void addItemCommand_executeAndUndo_worksCorrectly() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        AddItemCommand command = new AddItemCommand(service, "ESP", 1);

        // Execute command
        command.execute();
        assertEquals(1, order.items().size());

        // Undo command
        command.undo();
        assertEquals(0, order.items().size());
    }

    @Test
    void posRemote_undo_removesLastAction() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(2);

        remote.setSlot(0, new AddItemCommand(service, "ESP", 1));
        remote.setSlot(1, new AddItemCommand(service, "LAT", 1));

        remote.press(0);
        remote.press(1);
        assertEquals(2, order.items().size());

        remote.undo();
        assertEquals(1, order.items().size());
    }

    @Test
    void payOrderCommand_executesPayment() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        service.addItem("ESP", 1); // Add an item first

        PayOrderCommand command = new PayOrderCommand(service, new CashPayment(), 10);

        // This should not throw an exception
        assertDoesNotThrow(() -> command.execute());
    }
}