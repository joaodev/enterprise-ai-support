package com.joaodev.aisupport.support.application;

import com.joaodev.aisupport.support.application.port.in.AssistCustomerUseCase;
import com.joaodev.aisupport.support.application.port.out.AgentDecisionPort;
import com.joaodev.aisupport.support.application.port.out.OrderQueryPort;
import com.joaodev.aisupport.support.application.port.out.TicketCommandPort;
import com.joaodev.aisupport.support.domain.Order;
import com.joaodev.aisupport.support.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportAgentService implements AssistCustomerUseCase {

    private final OrderQueryPort orderQueryPort;
    private final AgentDecisionPort agentDecisionPort;
    private final TicketCommandPort ticketCommandPort;


    public SupportAgentService(OrderQueryPort orderQueryPort,
                               AgentDecisionPort agentDecisionPort,
                               TicketCommandPort ticketCommandPort) {
        this.orderQueryPort = orderQueryPort;
        this.agentDecisionPort = agentDecisionPort;
        this.ticketCommandPort = ticketCommandPort;
    }


    @Override
    public AssistCustomerResult assist(AssistCustomerCommand command) {
        List<String> executedTools = new ArrayList<>();
        Order order = orderQueryPort.findByIdAndCustomerId(command.orderId(), command.customerId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));
        executedTools.add("getOrder");

        AgentDecision decision = agentDecisionPort.decide(order, command.message());
        executedTools.add("evaluateSupportPolicy");

        Ticket ticket = null;
        if (decision.shouldOpenTicket()) {
            ticket = ticketCommandPort.create(
                    command.customerId(), command.orderId(), decision.reason()
            );
            executedTools.add("createTicket");
        }

        String answer = decision.explanation();
        if (ticket != null) {
            answer += " Ticket " + ticket.id() + " opened successfully";
        }
        var ticketId = ticket != null ? ticket.id() : null;

        return new AssistCustomerResult(
                answer, order.id(), order.status(), ticketId, List.copyOf(executedTools)
        );
    }
}
