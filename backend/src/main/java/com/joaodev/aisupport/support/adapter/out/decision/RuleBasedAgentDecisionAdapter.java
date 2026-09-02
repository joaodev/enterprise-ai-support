package com.joaodev.aisupport.support.adapter.out.decision;

import com.joaodev.aisupport.support.application.AgentDecision;
import com.joaodev.aisupport.support.application.port.out.AgentDecisionPort;
import com.joaodev.aisupport.support.domain.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class RuleBasedAgentDecisionAdapter implements AgentDecisionPort {

    @Override
    public AgentDecision decide(Order order, String customerMessage) {
        if (order.isDelayed(LocalDate.now())) {
            return new AgentDecision(
                    true,
                    "ORDER_DELAYED",
                    "The order is delayed and required logistics analysis."
            );
        }

        return new AgentDecision(
                false,
                "NO_ACTION_REQUIRED",
                "The order is not delayed. No support ticket was required."
        );
    }
}
