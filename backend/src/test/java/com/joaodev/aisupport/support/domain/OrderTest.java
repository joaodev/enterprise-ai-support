package com.joaodev.aisupport.support.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private final LocalDate today = LocalDate.of(2026, 9, 2);

    @Test
    void shouldBeDelayedWhenExpectedDeliveryDateHasPassed() {
        Order order = new Order(
                "order-1",
                "customer-1",
                OrderStatus.SHIPPED,
                today.minusDays(2)
        );

        assertTrue(order.isDelayed(today));
    }

    @Test
    void shouldNotBeDelayedWhenOrderWasDelivered() {
        Order order = new Order(
                "order-1",
                "customer-1",
                OrderStatus.DELIVERED,
                today.minusDays(2)
        );

        assertFalse(order.isDelayed(today));
    }

    @Test
    void shouldNotBeDelayedWhenOrderWasCancelled() {
        Order order = new Order(
                "order-1",
                "customer-1",
                OrderStatus.CANCELLED,
                today.minusDays(2)
        );

        assertFalse(order.isDelayed(today));
    }
}
