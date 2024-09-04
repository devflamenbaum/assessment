package dev.flamenbaum.service;

import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.enumeration.TransactionType;
import dev.flamenbaum.infrastructure.persistence.OperationTypeRepository;
import dev.flamenbaum.infrastructure.persistence.entity.OperationTypeEntity;
import dev.flamenbaum.infrastructure.service.OperationTypeRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationTypeRepositoryServiceTest {

    @InjectMocks
    OperationTypeRepositoryService service;

    @Mock
    OperationTypeRepository repository;

    private final Long operationId = 1L;

    @Test
    void testOperationTypeRepositoryServiceReturningOptionalOfOperationType() {

        when(repository.findById(operationId)).thenReturn(Optional.of(new OperationTypeEntity(operationId, "", TransactionType.DEBIT)));

        Optional<OperationType> opt = service.getOperationTypeById(operationId);

        Assertions.assertTrue(opt.isPresent());
        Assertions.assertInstanceOf(OperationType.class, opt.get());
    }

    @Test
    void testOperationTypeRepositoryServiceReturningOptionalOfEmpty() {

        when(repository.findById(operationId)).thenReturn(Optional.empty());

        Optional<OperationType> opt = service.getOperationTypeById(operationId);

        Assertions.assertFalse(opt.isPresent());
    }
}
