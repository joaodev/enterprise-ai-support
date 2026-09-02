package com.joaodev.aisupport.support.adapter.out.memory;

import com.joaodev.aisupport.support.application.port.out.OrderQueryPort;
import com.joaodev.aisupport.support.domain.Order;
import com.joaodev.aisupport.support.domain.OrderStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryOrderAdapter implements OrderQueryPort {

    private final Map<String, Order> orders = Map.of(
            "order-18273",
            new Order(
                    "order-18273",
                    "customer-1",
                    OrderStatus.SHIPPED,
                    LocalDate.now().minusDays(3)
            ),
            "order-10001",
            new Order(
                    "order-10001",
                    "customer-1",
                    OrderStatus.DELIVERED,
                    LocalDate.now().minusDays(1)
            )
    );

    @Override
    public Optional<Order> findByIdAndCustomerId(String orderId, String customerId) {
        return Optional.ofNullable(orders.get(orderId))
                .filter(order -> order.customerId().equals(customerId));
    }
}
