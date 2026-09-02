package com.joaodev.aisupport.support.application.port.out;

import com.joaodev.aisupport.support.domain.Order;

import java.util.Optional;

public interface OrderQueryPort {

    Optional<Order> findByIdAndCustomerId(String orderId, String customerId);
}
