package dev.flamenbaum.application.gateway;

import dev.flamenbaum.infrastructure.service.ApiRequest.GetByIdAccountRequest;

public interface AccountGateway {

    GetByIdAccountRequest hasAccountById(Long accountId);
}
