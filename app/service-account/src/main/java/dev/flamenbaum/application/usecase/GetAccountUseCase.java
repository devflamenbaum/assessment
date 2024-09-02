package dev.flamenbaum.application.usecase;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.ResourceNotFoundException;

public class GetAccountUseCase {

    private final AccountGateway accountGateway;

    public GetAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account getAccountById(Long accountId) {
        return accountGateway.getById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account Id: %s is not found", accountId)));
    }
}
