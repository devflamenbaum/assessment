package dev.flamenbaum.usecase;

import dev.flamenbaum.application.gateway.AccountGateway;
import dev.flamenbaum.application.gateway.OperationTypeGateway;
import dev.flamenbaum.application.gateway.TransactionGateway;
import dev.flamenbaum.application.usecase.CreateTransactionUseCase;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.core.enumeration.TransactionType;
import dev.flamenbaum.core.exception.OperationTypeException;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
import dev.flamenbaum.core.exception.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionUseCaseTest {

    @InjectMocks
    CreateTransactionUseCase createTransactionUseCase;

    @Mock
    TransactionGateway transactionGateway;

    @Mock
    AccountGateway accountGateway;

    @Mock
    OperationTypeGateway operationTypeGateway;

    private Transaction testTransaction;

    private OperationType operationType;

    @Test
    void testCreateTransactionSuccessfullyDebit() {

        operationType = new OperationType(1L, "", TransactionType.DEBIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(-10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));
        when(accountGateway.hasAccountById(testTransaction.getAccountId())).thenReturn(true);
        when(transactionGateway.create(testTransaction))
                .thenReturn(new Transaction(1L, 1L, operationType, testTransaction.getAmount(), LocalDateTime.now()));

        Transaction transaction = createTransactionUseCase.createTransaction(testTransaction);

        Assertions.assertNotNull(transaction);

    }

    @Test
    void testCreateTransactionSuccessfullyCredit() {

        operationType = new OperationType(1L, "", TransactionType.CREDIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));
        when(accountGateway.hasAccountById(testTransaction.getAccountId())).thenReturn(true);
        when(transactionGateway.create(testTransaction))
                .thenReturn(new Transaction(1L, 1L, operationType, testTransaction.getAmount(), LocalDateTime.now()));

        Transaction transaction = createTransactionUseCase.createTransaction(testTransaction);

        Assertions.assertNotNull(transaction);

    }

    @Test
    void testCreateTransactionOperationTypeException() {

        operationType = new OperationType(1L, "", TransactionType.DEBIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(-10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.empty());

        Assertions.assertThrows(OperationTypeException.class, () -> createTransactionUseCase.createTransaction(testTransaction));

    }

    @Test
    void testCreateTransactionExceptionDebitWithPositiveAmount() {

        operationType = new OperationType(1L, "", TransactionType.DEBIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));

        Assertions.assertThrows(TransactionException.class, () -> createTransactionUseCase.createTransaction(testTransaction));

    }

    @Test
    void testCreateTransactionExceptionCreditWithNegativeAmount() {

        operationType = new OperationType(1L, "", TransactionType.CREDIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(-10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));

        Assertions.assertThrows(TransactionException.class, () -> createTransactionUseCase.createTransaction(testTransaction));

    }

    @Test
    void testCreateTransactionExceptionWithAmountOfZero() {

        operationType = new OperationType(1L, "", TransactionType.CREDIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(0));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));

        Assertions.assertThrows(TransactionException.class, () -> createTransactionUseCase.createTransaction(testTransaction));

    }

    @Test
    void testCreateTransactionResourceNotFoundException() {

        operationType = new OperationType(1L, "", TransactionType.CREDIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal(10));

        when(operationTypeGateway.getOperationTypeById(operationType.getOperationTypeId())).thenReturn(Optional.of(operationType));
        when(accountGateway.hasAccountById(testTransaction.getAccountId())).thenReturn(false);


        Assertions.assertThrows(ResourceNotFoundException.class, () -> createTransactionUseCase.createTransaction(testTransaction));

    }
}
