package dev.flamenbaum.assessment.application.usecase;

import dev.flamenbaum.assessment.application.Gateway.AccountGateway;
import dev.flamenbaum.assessment.core.domain.Account;
import dev.flamenbaum.assessment.core.exception.AccountException;

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
