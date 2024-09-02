package dev.flamenbaum.infrastructure.persistence;

import dev.flamenbaum.infrastructure.persistence.entity.OperationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationTypeEntity, Long> {
}
