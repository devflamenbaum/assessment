package dev.flamenbaum.application.usecase;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.AccountException;

public class CreateAccountUseCase {

    private final AccountGateway accountGateway;

    public CreateAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account createAccount(Account account) {

        var hasAccount = accountGateway.getByDocumentNumber(account.getDocumentNumber());

        if(hasAccount.isPresent()) {
            throw new AccountException(
                    String.format("Document Number: %s - has already an account!", account.getDocumentNumber()));
        }

        return accountGateway.create(account);
    }
}
