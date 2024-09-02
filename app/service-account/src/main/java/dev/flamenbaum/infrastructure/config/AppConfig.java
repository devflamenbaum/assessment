package dev.flamenbaum.infrastructure.config;

import dev.flamenbaum.application.usecase.GetAccountUseCase;
import dev.flamenbaum.application.Gateway.AccountGateway;
import dev.flamenbaum.application.usecase.CreateAccountUseCase;
import dev.flamenbaum.infrastructure.persistence.AccountRepository;
import dev.flamenbaum.infrastructure.service.AccountRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountGateway accountGateway) {
        return new CreateAccountUseCase(accountGateway);
    }

    @Bean
    public GetAccountUseCase getAccountUseCase(AccountGateway accountGateway) {
        return new GetAccountUseCase(accountGateway);
    }

    @Bean
    public AccountGateway accountGateway(AccountRepository accountRepository) {
        return new AccountRepositoryService(accountRepository);
    }
}
