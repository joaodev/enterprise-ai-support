package com.joaodev.aisupport.support.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AssistRequest(
        @NotBlank String customerId,
        @NotBlank String orderId,
        @NotBlank String message
) {
}
