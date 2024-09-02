package dev.flamenbaum.infrastructure.service;

import dev.flamenbaum.application.gateway.TransactionGateway;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.infrastructure.persistence.TransactionRepository;
import dev.flamenbaum.infrastructure.persistence.entity.OperationTypeEntity;
import dev.flamenbaum.infrastructure.persistence.entity.TransactionEntity;

public class TransactionRepositoryService implements TransactionGateway {

    private final TransactionRepository transactionRepository;

    public TransactionRepositoryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        OperationType operationType = transaction.getOperationType();
        OperationTypeEntity operationTypeEntity =
                new OperationTypeEntity(operationType.getOperationTypeId(), operationType.getDescription(), operationType.getTransactionType());
        TransactionEntity entity = new TransactionEntity(transaction.getAccountId(), operationTypeEntity, transaction.getAmount());

        TransactionEntity savedTransaction = transactionRepository.save(entity);
        return new Transaction(
                savedTransaction.getTransactionId(),
                savedTransaction.getAccountId(),
                new OperationType(
                        savedTransaction.getOperationTypeEntity().getOperationTypeId(),
                        savedTransaction.getOperationTypeEntity().getDescription(),
                        savedTransaction.getOperationTypeEntity().getTransactionType()
                ),
                savedTransaction.getAmount(),
                savedTransaction.getCreatedAt());
    }
}
