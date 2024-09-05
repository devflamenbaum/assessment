package dev.flamenbaum.automated.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flamenbaum.automated.AutomatedApplicationTests;
import dev.flamenbaum.automated.account.model.AccountEntity;
import dev.flamenbaum.automated.request.CreateTransactionRequest;
import dev.flamenbaum.automated.response.CreateTransactionResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class StepTransactionTest extends AutomatedApplicationTests<CreateTransactionResponse> {

    private final String URL_TRANSACTION_POST = "http://localhost:8082/transactions";
    private Long accountId;
    private Long operationId;
    private String amount;

    @Given("^Account Id: (\\d+)$")
    public void an_account_id(Long accountId) {
        this.accountId = accountId;
    }

    @And("^Operation Type Id: (\\d+)$")
    public void an_operation_type_id(Long operationId) {
        this.operationId = operationId;
    }

    @And("^Amount: \"([^\"]*)\"$")
    public void an_amount(String amount) {
        this.amount = amount;
    }

    @And("Remove this account of testing transaction")
    public void removeThisAccountOfTestingTransaction() {
        Optional<AccountEntity> opt = accountRepository.findById(accountId);

        opt.ifPresent(accountEntity -> accountRepository.deleteById(accountEntity.getAccountId()));

        accountRepository.flush();
    }

    @Then("^Insert a new account with (\\d+)$")
    public void insert_a_new_account_with_document_number(String documentNumber) {
        accountRepository.saveAccount(accountId, documentNumber);
    }


    @When("^the client calls /transactions$")
    public void theClientCallsTransactions() {
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(accountId, operationId, amount);
        executePost(URL_TRANSACTION_POST, createTransactionRequest, CreateTransactionResponse.class);
    }

    @Then("^service transaction send status (\\d+)$")
    public void service_transaction_send_status(int status) {
        Assertions.assertEquals(this.status, status);
    }

    @And("Remove test transaction")
    public void removeTestTransaction() {
        transactionRepository.deleteByIdNatively(respBody.getTransactionId());
    }
}
