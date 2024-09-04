package dev.flamenbaum.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flamenbaum.application.usecase.CreateTransactionUseCase;
import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.domain.Transaction;
import dev.flamenbaum.core.exception.AccountApiException;
import dev.flamenbaum.core.exception.OperationTypeException;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
import dev.flamenbaum.core.exception.TransactionException;
import dev.flamenbaum.infrastructure.controller.TransactionController;
import dev.flamenbaum.infrastructure.controller.request.CreateTransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CreateTransactionUseCase createTransactionUseCase;

    CreateTransactionRequest testCreateTransactionRequest;

    @BeforeEach
    void setUp() {
        testCreateTransactionRequest = new CreateTransactionRequest(1L, 1L, "123.23");
    }

    @Test
    void testCreateTransactionSuccessfully() throws Exception {

        OperationType operationType =
                new OperationType(testCreateTransactionRequest.getOperationTypeId(), null, null);
        Transaction testTransaction =
                new Transaction(testCreateTransactionRequest.getAccountId(), operationType, new BigDecimal(testCreateTransactionRequest.getAmount()));

        when(createTransactionUseCase.createTransaction(any()))
                .thenReturn(new Transaction(
                        1L,
                        testCreateTransactionRequest.getAccountId(),
                        operationType,
                        testTransaction.getAmount(),
                        LocalDateTime.now()
                ));

        mockMvc.perform(post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
        )
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateTransactionException() throws Exception {

        when(createTransactionUseCase.createTransaction(any())).thenThrow(new TransactionException(""));

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTransactionNumberFormatException() throws Exception {

        testCreateTransactionRequest.setAmount("12345das");

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTransactionOperationTypeException() throws Exception {

        when(createTransactionUseCase.createTransaction(any())).thenThrow(new OperationTypeException());

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTransactionAccountApiException() throws Exception {

        when(createTransactionUseCase.createTransaction(any())).thenThrow(new AccountApiException(""));

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isBadGateway());
    }

    @Test
    void testCreateTransactionResourceNotFoundException() throws Exception {

        when(createTransactionUseCase.createTransaction(any())).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateTransactionArgumentNotValidException() throws Exception {

        testCreateTransactionRequest.setAccountId(null);

        mockMvc.perform(post("/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateTransactionRequest))
                )
                .andExpect(status().isBadRequest());
    }

}
