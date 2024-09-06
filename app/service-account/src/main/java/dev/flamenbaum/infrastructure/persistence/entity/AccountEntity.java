package dev.flamenbaum.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_accounts")
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", columnDefinition = "SERIAL")
    private Long accountId;
    @Column(name = "document_number", columnDefinition = "VARCHAR(255)")
    private String documentNumber;
    @Column(name = "available_credit_limit")
    private BigDecimal availableCreditLimit;

    public AccountEntity() {
    }

    public AccountEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public AccountEntity(String documentNumber, BigDecimal availableCreditLimit) {
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
    }

    public AccountEntity(Long accountId, String documentNumber, BigDecimal availableCreditLimit) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
    }

}
