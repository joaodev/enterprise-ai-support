package com.joaodev.aisupport.support.application.port.out;

import com.joaodev.aisupport.support.domain.Ticket;

public interface TicketCommandPort {

    Ticket create(String customerId, String orderId, String reason);
}
