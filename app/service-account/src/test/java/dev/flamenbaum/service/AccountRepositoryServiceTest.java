package dev.flamenbaum.service;

import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.infrastructure.persistence.AccountRepository;
import dev.flamenbaum.infrastructure.persistence.entity.AccountEntity;
import dev.flamenbaum.infrastructure.service.AccountRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryServiceTest {

    @InjectMocks
    AccountRepositoryService service;

    @Mock
    AccountRepository repository;

    Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account(1L, "123");
    }

    @Test
    void testCreateAccount() {

        AccountEntity entity = new AccountEntity(testAccount.getDocumentNumber());

        when(repository.save(entity)).thenReturn(new AccountEntity(testAccount.getAccountId(), testAccount.getDocumentNumber()));

        Account savedAccount = service.create(testAccount);

        Assertions.assertEquals(savedAccount.getAccountId(), testAccount.getAccountId());
        Assertions.assertEquals(savedAccount.getDocumentNumber(), testAccount.getDocumentNumber());
    }

    @Test
    void testGetAccountById() {


        when(repository.findById(testAccount.getAccountId()))
                .thenReturn(Optional.of(new AccountEntity(testAccount.getAccountId(), testAccount.getDocumentNumber())));

        Optional<Account> opt = service.getById(testAccount.getAccountId());

        Assertions.assertTrue(opt.isPresent());
    }

    @Test
    void testGetAccountByDocumentNumber() {


        when(repository.findByDocumentNumber(testAccount.getDocumentNumber()))
                .thenReturn(Optional.of(new AccountEntity(testAccount.getAccountId(), testAccount.getDocumentNumber())));

        Optional<Account> opt = service.getByDocumentNumber(testAccount.getDocumentNumber());

        Assertions.assertTrue(opt.isPresent());
    }


}
