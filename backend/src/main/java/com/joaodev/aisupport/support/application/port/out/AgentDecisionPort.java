package com.joaodev.aisupport.support.application.port.out;

import com.joaodev.aisupport.support.application.AgentDecision;
import com.joaodev.aisupport.support.domain.Order;

public interface AgentDecisionPort {

    AgentDecision decide(Order order, String customerMessage);
}
