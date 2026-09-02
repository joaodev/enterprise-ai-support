package com.joaodev.aisupport.support.domain;

import java.time.Instant;

public record Ticket(
        String id,
        String customerId,
        String orderId,
        String reason,
        Instant createdAt
) {
}
