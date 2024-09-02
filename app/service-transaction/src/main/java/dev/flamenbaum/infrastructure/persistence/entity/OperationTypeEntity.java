package dev.flamenbaum.infrastructure.persistence.entity;

import dev.flamenbaum.core.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_operation_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operation_id", columnDefinition = "SERIAL")
    private Long operationTypeId;
    @Column(name = "description")
    private String Description;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
