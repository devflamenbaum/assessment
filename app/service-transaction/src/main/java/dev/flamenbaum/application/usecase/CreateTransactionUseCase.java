package dev.flamenbaum.application.usecase;

import dev.flamenbaum.application.gateway.AccountGateway;
import dev.flamenbaum.application.gateway.OperationTypeGateway;
import dev.flamenbaum.application.gateway.TransactionGateway;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.core.enumeration.TransactionType;
import dev.flamenbaum.core.exception.TransactionException;

import java.math.BigDecimal;
import java.util.Optional;

public class CreateTransactionUseCase {

    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;
    private final OperationTypeGateway operationTypeGateway;

    public CreateTransactionUseCase(TransactionGateway transactionGateway, AccountGateway accountGateway, OperationTypeGateway operationTypeGateway) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
        this.operationTypeGateway = operationTypeGateway;
    }

    public Transaction createTransaction(Transaction transaction) {

        Optional<OperationType> opt = operationTypeGateway.getOperationTypeById(transaction.getOperationType().getOperationTypeId());

        OperationType operationType = opt
                .orElseThrow(RuntimeException::new);

        TransactionType transactionType = operationType.getTransactionType();

        if(transactionType == TransactionType.CREDIT && transaction.getAmount().signum() == -1) {
            throw new TransactionException(String.format("Invalid Transaction Amount: %s should be positive", transaction.getAmount()));
        }

        if (transactionType == TransactionType.DEBIT && transaction.getAmount().signum() == 1) {
            throw new TransactionException(String.format("Invalid Transaction Amount: %s should be negative", transaction.getAmount()));
        }

        if (transaction.getAmount().signum() == 0) {
            throw new TransactionException(String.format("Invalid Transaction Amount: %s shouldn`t be 0.00", transaction.getAmount()));
        }

        if(!accountGateway.hasAccountById(transaction.getAccountId())) {
            throw new TransactionException(String.format("Invalid Account, There is no account with id: %s", transaction.getAccountId()));
        }
        return transactionGateway.create(transaction);
    }
}
