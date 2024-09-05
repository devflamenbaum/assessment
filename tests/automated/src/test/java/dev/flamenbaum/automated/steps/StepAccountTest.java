package dev.flamenbaum.automated.steps;

import dev.flamenbaum.automated.AutomatedApplicationTests;
import dev.flamenbaum.automated.account.model.AccountEntity;
import dev.flamenbaum.automated.request.CreateAccountRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class StepAccountTest extends AutomatedApplicationTests<Object> {

    private final String URL_ACOUNT_POST = "http://localhost:8081/accounts";

    private final String URL_ACCOUNT_GET_BY_ID = "http://localhost:8081/accounts/{id}";

    private Long accountId;

    private String documentNumber;

    @Given("^a Account id (\\d+)$")
    public void a_account_id(Long accountId) {
        this.accountId = accountId;
    }

    @Given("^a document number (\\d+)$")
    public void a_document_number(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @And("Remove this account")
    @Transactional
    public void remove_this_account() {

        Optional<AccountEntity> opt = accountRepository.findById(accountId);

        if (opt.isPresent()) {
            accountRepository.delete(new AccountEntity(accountId,documentNumber));
        }

        accountRepository.flush();
    }

    @And("^Remove this (\\d+) if exists$")
    @Transactional
    public void remove_this_document_number_if_exists(String documentNumber) {
        Optional<AccountEntity> opt = accountRepository.findByDocumentNumber(documentNumber);

        if(opt.isPresent()) {
            accountRepository.deleteByDocumentNumber(documentNumber);
        }

        accountRepository.flush();
    }

    @And("^Insert this (\\d+) in AccountDB$")
    public void insert_this_document_number_in_accountDB(String documentNumber) {

        accountRepository.save(new AccountEntity(documentNumber));
    }

    @And("^Insert this account$")
    public void insert_this_account() {
        accountRepository.saveAccount(accountId,documentNumber);
    }

    @When("^the client calls /accounts$")
    public void the_client_issues_POST_accounts() {
        CreateAccountRequest request = new CreateAccountRequest(documentNumber);
        executePost(URL_ACOUNT_POST, request, Object.class);
    }

    @When("^the client calls /accounts/id$")
    public void the_client_calls_accounts_by_id() {
        executeGet(URL_ACCOUNT_GET_BY_ID, accountId);
    }

    @Then("^the client receives status (\\d+)$")
    public void the_client_receives_status(int status) {
        Assertions.assertEquals(status, this.status);
    }


}
