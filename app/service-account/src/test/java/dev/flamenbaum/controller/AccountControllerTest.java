package dev.flamenbaum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flamenbaum.application.usecase.CreateAccountUseCase;
import dev.flamenbaum.application.usecase.GetAccountUseCase;
import dev.flamenbaum.core.domain.Account;
import dev.flamenbaum.core.exception.AccountException;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
import dev.flamenbaum.infrastructure.controller.AccountController;
import dev.flamenbaum.infrastructure.controller.request.CreateAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CreateAccountUseCase createAccountUseCase;

    @MockBean
    GetAccountUseCase getAccountUseCase;

    @Test
    void testCreateAccountSuccessFully() throws Exception {

        String documentNumber = "12345678901";

        CreateAccountRequest request = new CreateAccountRequest(documentNumber);
        Account account = new Account(null, documentNumber);

        when(createAccountUseCase.createAccount(account)).thenReturn(new Account(1L, documentNumber));

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void testCreateAccountMethodArgumentNotValid() throws Exception {

        String documentNumber = "";

        CreateAccountRequest request = new CreateAccountRequest(documentNumber);

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateAccountException() throws Exception {

        String documentNumber = "12345678901";

        CreateAccountRequest request = new CreateAccountRequest(documentNumber);
        Account account = new Account(null, documentNumber);

        when(createAccountUseCase.createAccount(account)).thenThrow(new AccountException("test"));

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetByAccountIdSuccessfully() throws Exception {

        Account testAccount = new Account(1L, "123");

        when(getAccountUseCase.getAccountById(1L)).thenReturn(testAccount);

        mockMvc.perform(
                get("/accounts/{accountId}", testAccount.getAccountId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByAccountIdResourceNotFoundException() throws Exception {

        Long testAccountId = 1L;

        when(getAccountUseCase.getAccountById(testAccountId)).thenThrow(new ResourceNotFoundException("message test"));

        mockMvc.perform(
                        get("/accounts/{accountId}", testAccountId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByAccountIdNumberFormatException() throws Exception {

        String testAccountId = "qweqwe";

        mockMvc.perform(
                        get("/accounts/{accountId}", testAccountId))
                .andExpect(status().isBadRequest());
    }
}
