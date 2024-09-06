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
        AccountEntity accountEntity = new AccountEntity(account.getDocumentNumber(), account.getAvailableCreditLimit());
        AccountEntity savedEntity = repository.save(accountEntity);
        return new Account(savedEntity.getAccountId(), savedEntity.getDocumentNumber(), savedEntity.getAvailableCreditLimit());
    }

    @Override
    public Optional<Account> getById(Long id) {
        Optional<AccountEntity> optionalAccountEntity = repository.findById(id);
        return optionalAccountEntity
                .map(accountEntity -> new Account(accountEntity.getAccountId(), accountEntity.getDocumentNumber(), accountEntity.getAvailableCreditLimit()));
    }

    @Override
    public Optional<Account> getByDocumentNumber(String documentNumber) {
        Optional<AccountEntity> optionalAccountEntity = repository.findByDocumentNumber(documentNumber);

        return optionalAccountEntity
                .map(accountEntity -> new Account(accountEntity.getAccountId(), accountEntity.getDocumentNumber(), accountEntity.getAvailableCreditLimit()));
    }

    @Override
    public Account updated(Account accountToUpdated) {
        AccountEntity entity = new AccountEntity(accountToUpdated.getAccountId(), accountToUpdated.getDocumentNumber(), accountToUpdated.getAvailableCreditLimit());
        AccountEntity updated = repository.save(entity);
        return new Account(updated.getAccountId(), updated.getDocumentNumber(), updated.getAvailableCreditLimit());
    }
}
