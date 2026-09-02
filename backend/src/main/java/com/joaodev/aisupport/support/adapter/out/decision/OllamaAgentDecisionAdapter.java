package com.joaodev.aisupport.support.adapter.out.decision;

import com.joaodev.aisupport.support.application.AgentDecision;
import com.joaodev.aisupport.support.application.port.out.AgentDecisionPort;
import com.joaodev.aisupport.support.domain.Order;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("ollama")
public class OllamaAgentDecisionAdapter implements AgentDecisionPort {

    private static final String SYSTEM_PROMPT = """
            You are an enterprise customer-support decision engine.
            
            Analyze the customer message and the order data.
            
            Business rules:
            - Open a ticket when the order is delayed.
            - Do not open a ticket when the order is delivered.
            - Do not open a ticket when the order is cancelled.
            - Never invent order information.
            - The reason must be a short uppercase business code.
            - The explanation must be concise and written in the
              same language used by the customer.
            """;

    private final ChatClient chatClient;

    public OllamaAgentDecisionAdapter(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public AgentDecision decide(Order order, String customerMessage) {
        AgentDecision decision = chatClient
                .prompt()
                .system(SYSTEM_PROMPT)
                .user(user -> user
                        .text("""
                                Customer message:
                                {customerMessage}

                                Order data:
                                - ID: {orderId}
                                - Customer ID: {customerId}
                                - Status: {status}
                                - Expected delivery date: {deliveryDate}
                                - Delayed according to domain rules: {delayed}
                                """)
                        .param(
                                "customerMessage",
                                customerMessage
                        )
                        .param("orderId", order.id())
                        .param("customerId", order.customerId())
                        .param("status", order.status().name())
                        .param(
                                "deliveryDate",
                                order.expectedDeliveryDate().toString()
                        )
                        .param(
                                "delayed",
                                String.valueOf(
                                        order.isDelayed(LocalDate.now())
                                )
                        ))
                .call()
                .entity(AgentDecision.class);

        if (decision == null) {
            throw new IllegalStateException("Ollama returned an empty decision");
        }

        return decision;
    }
}
