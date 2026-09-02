package com.joaodev.aisupport.support.adapter.in.web;

import com.joaodev.aisupport.support.adapter.in.web.dto.AssistRequest;
import com.joaodev.aisupport.support.application.AssistCustomerCommand;
import com.joaodev.aisupport.support.application.AssistCustomerResult;
import com.joaodev.aisupport.support.application.port.in.AssistCustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/support")
@CrossOrigin(origins = "http://localhost:4200")
public class SupportController {

    private final AssistCustomerUseCase assistCustomerUseCase;

    public SupportController(AssistCustomerUseCase assistCustomerUseCase) {
        this.assistCustomerUseCase = assistCustomerUseCase;
    }

    @PostMapping("/assist")
    public AssistCustomerResult assist(@Valid @RequestBody AssistRequest request) {
        AssistCustomerCommand command = new AssistCustomerCommand(
                request.customerId(),
                request.orderId(),
                request.message()
        );
        return assistCustomerUseCase.assist(command);
    }
}
