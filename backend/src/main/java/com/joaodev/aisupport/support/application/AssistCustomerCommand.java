package com.joaodev.aisupport.support.application;

public record AssistCustomerCommand(
        String customerId,
        String orderId,
        String message
) {
}
