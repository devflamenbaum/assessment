package dev.flamenbaum.automated.transaction.repository;

import dev.flamenbaum.automated.transaction.model.OperationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationTypeEntity, Long> {
}
