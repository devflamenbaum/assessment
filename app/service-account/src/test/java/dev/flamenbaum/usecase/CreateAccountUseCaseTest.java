package dev.flamenbaum.usecase;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.application.usecase.CreateAccountUseCase;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.AccountException;
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
public class CreateAccountUseCaseTest {

    @InjectMocks
    CreateAccountUseCase createAccountUseCase;

    @Mock
    AccountGateway accountGateway;

    Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account(null, "123");
    }

    @Test
    void testCreateAccountUseCaseSuccessfully() {

        Account newAccount = new Account(1L, testAccount.getDocumentNumber());

        when(accountGateway.getByDocumentNumber(testAccount.getDocumentNumber())).thenReturn(Optional.empty());
        when(accountGateway.create(testAccount)).thenReturn(newAccount);

        Account savedAccount = createAccountUseCase.createAccount(testAccount);

        Assertions.assertEquals(newAccount.getAccountId(), savedAccount.getAccountId());
    }

    @Test
    void testCreateAccountUseCaseAccountException() {

        Account savedAccount = new Account(1L, testAccount.getDocumentNumber());

        when(accountGateway.getByDocumentNumber(testAccount.getDocumentNumber())).thenReturn(Optional.of(savedAccount));

        Assertions.assertThrows(AccountException.class,
                () -> createAccountUseCase.createAccount(testAccount),
                String.format("Document Number: %s - has already an account!", testAccount.getDocumentNumber()));

    }
}
