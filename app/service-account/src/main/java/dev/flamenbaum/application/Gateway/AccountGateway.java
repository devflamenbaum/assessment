package dev.flamenbaum.application.Gateway;

import dev.flamenbaum.core.domain.Account;

import java.util.Optional;

public interface AccountGateway {

    Account create(Account account);

    Optional<Account> getById(Long id);

    Optional<Account> getByDocumentNumber(String documentNumber);
}
