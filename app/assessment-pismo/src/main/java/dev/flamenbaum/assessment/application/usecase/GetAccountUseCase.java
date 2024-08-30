package dev.flamenbaum.assessment.application.usecase;

import dev.flamenbaum.assessment.application.Gateway.AccountGateway;
import dev.flamenbaum.assessment.core.domain.Account;
import dev.flamenbaum.assessment.core.exception.ResourceNotFoundException;

public class GetAccountUseCase {

    private final AccountGateway accountGateway;

    public GetAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account getAccountById(Long accountId) {
        return accountGateway.getById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account Id: %s is not found"));
    }
}
