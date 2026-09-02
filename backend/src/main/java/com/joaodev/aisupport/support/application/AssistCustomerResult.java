package com.joaodev.aisupport.support.application;

import com.joaodev.aisupport.support.domain.OrderStatus;

import java.util.List;

public record AssistCustomerResult(
        String answer,
        String orderId,
        OrderStatus orderStatus,
        String ticketId,
        List<String> executedTools
) {
}
