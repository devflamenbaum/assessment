package dev.flamenbaum.infrastructure.persistence;

import dev.flamenbaum.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByDocumentNumber(String documentNumber);
}
