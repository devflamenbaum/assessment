package dev.flamenbaum.service;

import dev.flamenbaum.core.exception.AccountApiException;
import dev.flamenbaum.infrastructure.service.AccountApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountApiServiceTest {

    AccountApiService service;

    @Mock
    RestTemplate restTemplate;

    private final String fakeUrl = "http://fake:1000/accounts/{accountId}";

    private final Long accountId = 1L;

    @BeforeEach
    void setUp() {
        service = new AccountApiService(restTemplate, fakeUrl);
    }

    @Test
    void testHasAccountByIdTrue() {


        when(restTemplate.exchange(fakeUrl, HttpMethod.GET, service.httpEntity(), Object.class, service.uriVariables(accountId)))
                .thenReturn(ResponseEntity.ok().body(null));
        boolean resp = service.hasAccountById(accountId);

        Assertions.assertTrue(resp);
    }

    @Test
    void testHasAccountByIdFalse() {


        when(restTemplate.exchange(fakeUrl, HttpMethod.GET, service.httpEntity(), Object.class, service.uriVariables(accountId)))
                .thenThrow(HttpClientErrorException.class);
        boolean resp = service.hasAccountById(accountId);

        Assertions.assertFalse(resp);
    }

    @Test
    void testHasAccountByIdAccountApiException() {

        when(restTemplate.exchange(fakeUrl, HttpMethod.GET, service.httpEntity(), Object.class, service.uriVariables(accountId)))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(AccountApiException.class,
                () -> service.hasAccountById(accountId));
    }
}
