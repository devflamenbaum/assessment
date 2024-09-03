package dev.flamenbaum.infrastructure.controller;

import dev.flamenbaum.application.usecase.CreateTransactionUseCase;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.infrastructure.controller.request.CreateTransactionRequest;
import dev.flamenbaum.infrastructure.controller.response.CreateTransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @Operation(summary = "Create a new transaction")
    @PostMapping
    public ResponseEntity<CreateTransactionResponse> create(@RequestBody CreateTransactionRequest request) {
        OperationType operationType = new OperationType();
        operationType.setOperationTypeId(request.getOperationTypeId());
        Transaction transaction = new Transaction(request.getAccountId(), operationType, request.getAmount());
        Transaction savedTransaction = createTransactionUseCase.createTransaction(transaction);
        return ResponseEntity.ok(new CreateTransactionResponse
                (savedTransaction.getTransactionId(), savedTransaction.getAccountId(),
                        savedTransaction.getOperationType().getOperationTypeId(), savedTransaction.getAmount()));
    }
}
