package dev.flamenbaum.automated.account.model;

import jakarta.persistence.*;
import lombok.Data;

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

    public AccountEntity() {
    }

    public AccountEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public AccountEntity(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

}
