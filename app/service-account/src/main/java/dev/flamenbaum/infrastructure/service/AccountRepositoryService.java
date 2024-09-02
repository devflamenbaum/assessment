package dev.flamenbaum.infrastructure.service;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.infrastructure.persistence.AccountRepository;
import dev.flamenbaum.infrastructure.persistence.entity.AccountEntity;

import java.util.Optional;

public class AccountRepositoryService implements AccountGateway {

    private final AccountRepository repository;

    public AccountRepositoryService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = new AccountEntity(account.getDocumentNumber());
        AccountEntity savedEntity = repository.save(accountEntity);
        return new Account(savedEntity.getAccountId(), savedEntity.getDocumentNumber());
    }

    @Override
    public Optional<Account> getById(Long id) {
        Optional<AccountEntity> optionalAccountEntity = repository.findById(id);
        return optionalAccountEntity
                .map(accountEntity -> new Account(accountEntity.getAccountId(), accountEntity.getDocumentNumber()));
    }

    @Override
    public Optional<Account> getByDocumentNumber(String documentNumber) {
        Optional<AccountEntity> optionalAccountEntity = repository.findByDocumentNumber(documentNumber);

        return optionalAccountEntity
                .map(accountEntity -> new Account(accountEntity.getAccountId(), accountEntity.getDocumentNumber()));
    }
}
