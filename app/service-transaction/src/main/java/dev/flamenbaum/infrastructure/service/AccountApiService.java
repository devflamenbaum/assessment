package dev.flamenbaum.infrastructure.service;

import dev.flamenbaum.application.gateway.AccountGateway;
import dev.flamenbaum.core.exception.AccountApiException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class AccountApiService implements AccountGateway {

    private final String accountApiUrl;
    private final RestTemplate restTemplate;

    public AccountApiService(RestTemplate restTemplate, String accountApiUrl) {
        this.restTemplate = restTemplate;
        this.accountApiUrl = accountApiUrl;
    }

    @Override
    public boolean hasAccountById(Long accountId) {

        try {
            ResponseEntity<Object> response = restTemplate
                    .exchange(accountApiUrl, HttpMethod.GET, httpEntity(), Object.class, uriVariables(accountId));

            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException ex) {
            return false;
        } catch(RestClientException ex) {
            throw new AccountApiException("Account API is unavailable, try the operation later!");
        }
    }

    public HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content", "application/json");

        return new HttpEntity<>(headers);
    }

    public Map<String, Long> uriVariables(Long accountId) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("accountId", accountId);
        return uriVariables;
    }
}
