package dev.flamenbaum.application.usecase;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.core.domain.Account;

import java.util.Optional;

public class UpdateAccountUseCase {

    private final AccountGateway accountGateway;

    public UpdateAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account update(Long id, Account account) {

        Optional<Account> acc =
                Optional.ofNullable(accountGateway.getById(id).orElseThrow(RuntimeException::new));

        Account accountToUpdated = acc.get();

        accountToUpdated.setAvailableCreditLimit(account.getAvailableCreditLimit());

        return accountGateway.updated(accountToUpdated);
    }
}
