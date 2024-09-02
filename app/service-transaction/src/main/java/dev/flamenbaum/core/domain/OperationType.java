package dev.flamenbaum.core.domain;

import dev.flamenbaum.core.enumeration.TransactionType;

public class OperationType {

    private Long operationTypeId;
    private String Description;
    private TransactionType transactionType;

    public OperationType() {
    }

    public OperationType(Long operationTypeId, String description, TransactionType transactionType) {
        this.operationTypeId = operationTypeId;
        Description = description;
        this.transactionType = transactionType;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
