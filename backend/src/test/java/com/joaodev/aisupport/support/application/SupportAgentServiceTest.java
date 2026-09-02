package com.joaodev.aisupport.support.application;

import com.joaodev.aisupport.support.application.port.out.AgentDecisionPort;
import com.joaodev.aisupport.support.application.port.out.OrderQueryPort;
import com.joaodev.aisupport.support.application.port.out.TicketCommandPort;
import com.joaodev.aisupport.support.domain.Order;
import com.joaodev.aisupport.support.domain.OrderStatus;
import com.joaodev.aisupport.support.domain.Ticket;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupportAgentServiceTest {

    @Test
    void shouldCreateTicketWhenAgentDecidesItIsNecessary() {
        Order order = new Order(
                "order-1",
                "customer-1",
                OrderStatus.SHIPPED,
                LocalDate.now().minusDays(3)
        );

        AssistCustomerResult result = getResult(order);

        assertEquals("ticket-123", result.ticketId());
        assertEquals(OrderStatus.SHIPPED, result.orderStatus());
        assertNotNull(result.answer());

        assertEquals(
                List.of(
                        "getOrder",
                        "evaluateSupportPolicy",
                        "createTicket"
                ),
                result.executedTools()
        );
    }

    private static AssistCustomerResult getResult(Order order) {
        OrderQueryPort orderQueryPort = (orderId, customerId) -> Optional.of(order);
        AgentDecisionPort agentDecisionPort = (foundOrder, message) -> new AgentDecision(
                true,
                "ORDER_DELAYED",
                "The order is delayed");

        TicketCommandPort ticketCommandPort = (customerId, orderId, reason) -> new Ticket(
                "ticket-123",
                customerId,
                orderId,
                reason,
                Instant.now()
        );

        SupportAgentService service = new SupportAgentService(orderQueryPort, agentDecisionPort, ticketCommandPort);
        return service.assist(new AssistCustomerCommand(
                "customer-1",
                "order-18273",
                "Where is my order?"));
    }
}
