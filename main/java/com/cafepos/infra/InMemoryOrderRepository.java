// File: src/main/java/com/cafepos/infra/InMemoryOrderRepository.java
package com.cafepos.infra;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderRepository;
import java.util.*;

public final class InMemoryOrderRepository implements OrderRepository {
    private final Map<Long, Order> store = new HashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.id(), order);
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}