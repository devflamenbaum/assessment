package dev.flamenbaum.usecase;

import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.application.usecase.GetAccountUseCase;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAccountUseCaseTest {

    @InjectMocks
    GetAccountUseCase getAccountUseCase;

    @Mock
    AccountGateway accountGateway;

    private final Long testAccountId = 1L;
    private final String testAccountDocumentNumber = "123";

    @Test
    void testGetAccountUseCaseSuccessfully() {

        when(accountGateway.getById(testAccountId)).thenReturn(Optional.of(new Account(testAccountId, testAccountDocumentNumber)));

        Account account = getAccountUseCase.getAccountById(testAccountId);

        Assertions.assertNotNull(account);
    }

    @Test
    void testGetAccountUseCaseResourceNotFoundException() {

        when(accountGateway.getById(testAccountId)).thenThrow(new ResourceNotFoundException("test"));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> getAccountUseCase.getAccountById(testAccountId));
    }
}
