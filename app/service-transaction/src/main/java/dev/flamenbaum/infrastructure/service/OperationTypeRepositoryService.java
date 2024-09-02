package dev.flamenbaum.infrastructure.service;

import dev.flamenbaum.application.gateway.OperationTypeGateway;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.infrastructure.persistence.OperationTypeRepository;
import dev.flamenbaum.infrastructure.persistence.entity.OperationTypeEntity;

import java.util.Optional;

public class OperationTypeRepositoryService implements OperationTypeGateway {

    private final OperationTypeRepository operationTypeRepository;

    public OperationTypeRepositoryService(OperationTypeRepository operationTypeRepository) {
        this.operationTypeRepository = operationTypeRepository;
    }


    @Override
    public Optional<OperationType> getOperationTypeById(Long operationTypeId) {
        Optional<OperationTypeEntity> opt = operationTypeRepository.findById(operationTypeId);

        return opt.map(operation ->
                new OperationType(operation.getOperationTypeId(), operation.getDescription(), operation.getTransactionType()));
    }
}
