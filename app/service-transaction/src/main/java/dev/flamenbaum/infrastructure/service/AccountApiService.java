package dev.flamenbaum.infrastructure.service;

import dev.flamenbaum.application.gateway.AccountGateway;
import dev.flamenbaum.core.exception.AccountApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class AccountApiService implements AccountGateway {

    @Value("${account.api.url}")
    private String accountApiUrl;
    private final RestTemplate restTemplate;

    public AccountApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean hasAccountById(Long accountId) {

        try{
            ResponseEntity<Object> response = restTemplate
                    .exchange(accountApiUrl, HttpMethod.GET, httpEntity(), Object.class, uriVariables(accountId));

            return response.getStatusCode().is2xxSuccessful();
        } catch(RestClientException ex) {
            throw new AccountApiException("Account API is unavailable, try the operation later!");
        }
    }

    private HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content", "application/json");

        return new HttpEntity<>(headers);
    }

    private Map<String, Long> uriVariables(Long accountId) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("accountId", accountId);
        return uriVariables;
    }
}
