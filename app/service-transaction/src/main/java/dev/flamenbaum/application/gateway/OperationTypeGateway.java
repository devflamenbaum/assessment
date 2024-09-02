package dev.flamenbaum.application.gateway;

import dev.flamenbaum.core.domain.OperationType;

import java.util.Optional;

public interface OperationTypeGateway {

    Optional<OperationType> getOperationTypeById(Long operationTypeId);
}
