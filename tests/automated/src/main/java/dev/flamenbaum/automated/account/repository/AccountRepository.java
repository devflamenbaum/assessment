package dev.flamenbaum.automated.account.repository;


import dev.flamenbaum.automated.account.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByDocumentNumber(String documentNumber);

    void deleteByDocumentNumber(String documentNumber);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_accounts(account_id, document_number) VALUES (:id,:number)", nativeQuery = true)
    void saveAccount(@Param("id") Long accountId, @Param("number") String documentNumber);
}
