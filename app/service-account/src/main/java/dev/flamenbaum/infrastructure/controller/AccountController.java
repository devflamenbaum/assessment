package dev.flamenbaum.infrastructure.controller;

import dev.flamenbaum.application.usecase.GetAccountUseCase;
import dev.flamenbaum.application.usecase.CreateAccountUseCase;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.AccountException;
import dev.flamenbaum.infrastructure.controller.request.CreateAccountRequest;
import dev.flamenbaum.infrastructure.controller.response.CreateAccountResponse;
import dev.flamenbaum.infrastructure.controller.response.GetAccountByIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase, GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    @Operation(summary = "Create a new account")
    @PostMapping
    public ResponseEntity<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {

        Account account = new Account();
        account.setDocumentNumber(createAccountRequest.getDocumentNumber());
        Account savedAccount = createAccountUseCase.createAccount(account);
        CreateAccountResponse createAccountResponse = new CreateAccountResponse(savedAccount.getAccountId(), savedAccount.getDocumentNumber());
        return ResponseEntity.status(201).body(createAccountResponse);
    }

    @Operation(summary = "Get account by ID")
    @GetMapping("/{accountId}")
    public ResponseEntity<GetAccountByIdResponse> getAccoundById(@PathVariable("accountId") String accountId) {
        try {
            Long id = Long.parseLong(accountId);

            Account account = getAccountUseCase.getAccountById(id);
            GetAccountByIdResponse getAccountByIdResponse = new GetAccountByIdResponse(account.getAccountId(), account.getDocumentNumber());
            return ResponseEntity.status(200).body(getAccountByIdResponse);
        } catch (NumberFormatException ex) {
            throw new AccountException(String.format("AccountId: %s is not a number!", accountId));
        }

    }
}
