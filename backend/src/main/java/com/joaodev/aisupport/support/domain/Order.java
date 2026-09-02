package com.joaodev.aisupport.support.domain;

import java.time.LocalDate;
import java.util.Objects;

public record Order(
        String id,
        String customerId,
        OrderStatus status,
        LocalDate expectedDeliveryDate
) {
    public Order {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id is required");
        }

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer id is required");
        }

        Objects.requireNonNull(status, "Order status is required");
        Objects.requireNonNull(
                expectedDeliveryDate,
                "Expected delivery data is required"
        );
    }

    public boolean isDelayed(LocalDate referenceDate) {
        Objects.requireNonNull(referenceDate, "Reference date is required");

        boolean isFinished = status == OrderStatus.DELIVERED
                || status == OrderStatus.CANCELLED;

        return !isFinished && expectedDeliveryDate.isBefore(referenceDate);
    }
}
