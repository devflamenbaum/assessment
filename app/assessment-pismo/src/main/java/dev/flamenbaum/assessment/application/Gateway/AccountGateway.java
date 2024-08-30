package dev.flamenbaum.assessment.application.Gateway;

import dev.flamenbaum.assessment.core.domain.Account;

import java.util.Optional;

public interface AccountGateway {

    Account create(Account account);

    Optional<Account> getById(Long id);

    Optional<Account> getByDocumentNumber(String documentNumber);
}
