package dev.flamenbaum.automated.transaction.repository;

import dev.flamenbaum.automated.transaction.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_transaction WHERE transaction_id = :id", nativeQuery = true)
    void deleteByIdNatively(@Param("id") Long transactionId);
}
