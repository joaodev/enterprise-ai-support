package com.joaodev.aisupport.support.application.port.in;

import com.joaodev.aisupport.support.application.AssistCustomerCommand;
import com.joaodev.aisupport.support.application.AssistCustomerResult;

public interface AssistCustomerUseCase {

    AssistCustomerResult assist(AssistCustomerCommand command);
}
