package dev.flamenbaum.automated.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", columnDefinition = "SERIAL")
    private Long transactionId;
    @Column(name = "account_id")
    private Long accountId;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationTypeEntity operationTypeEntity;
    private BigDecimal amount;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TransactionEntity(Long accountId, OperationTypeEntity operationTypeEntity, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeEntity = operationTypeEntity;
        this.amount = amount;
    }
}
