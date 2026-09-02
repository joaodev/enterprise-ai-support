package com.joaodev.aisupport.support.application;

public record AgentDecision(
        boolean shouldOpenTicket,
        String reason,
        String explanation
) {
}
