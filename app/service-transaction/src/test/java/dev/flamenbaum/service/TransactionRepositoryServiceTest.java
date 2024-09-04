package dev.flamenbaum.service;

import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.core.enumeration.TransactionType;
import dev.flamenbaum.infrastructure.persistence.TransactionRepository;
import dev.flamenbaum.infrastructure.persistence.entity.OperationTypeEntity;
import dev.flamenbaum.infrastructure.persistence.entity.TransactionEntity;
import dev.flamenbaum.infrastructure.service.TransactionRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryServiceTest {

    @InjectMocks
    TransactionRepositoryService service;

    @Mock
    TransactionRepository repository;

    private Transaction testTransaction;
    private OperationType operationType;

    @BeforeEach
    void setUp() {
        operationType = new OperationType(1L, "", TransactionType.DEBIT);
        testTransaction = new Transaction(1L, operationType, new BigDecimal("123.00"));
    }

    @Test
    void testCreateTransactionSuccessfully() {

        OperationTypeEntity operationTypeEntity =
                new OperationTypeEntity(operationType.getOperationTypeId(), operationType.getDescription(), operationType.getTransactionType());

        TransactionEntity transactionEntity
                = new TransactionEntity(1L, 1L, operationTypeEntity, testTransaction.getAmount(), LocalDateTime.now());

        when(repository.save(new TransactionEntity(testTransaction.getAccountId(),
                operationTypeEntity, testTransaction.getAmount()))).thenReturn(transactionEntity);

        Transaction transaction = service.create(testTransaction);

        Assertions.assertNotNull(transaction);
    }
}
