package com.joaodev.aisupport.support.adapter.out.memory;

import com.joaodev.aisupport.support.application.port.out.TicketCommandPort;
import com.joaodev.aisupport.support.domain.Ticket;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public class InMemoryTicketAdapter implements TicketCommandPort {

    @Override
    public Ticket create(String customerId, String orderId, String reason) {
        String ticketId = "ticket-" + UUID.randomUUID().toString().substring(0, 8);
        return new Ticket(ticketId, customerId, orderId, reason, Instant.now());
    }
}
