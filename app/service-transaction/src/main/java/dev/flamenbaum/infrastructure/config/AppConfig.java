package dev.flamenbaum.infrastructure.config;

import dev.flamenbaum.application.gateway.AccountGateway;
import dev.flamenbaum.application.gateway.OperationTypeGateway;
import dev.flamenbaum.application.gateway.TransactionGateway;
import dev.flamenbaum.application.usecase.CreateTransactionUseCase;
import dev.flamenbaum.infrastructure.persistence.OperationTypeRepository;
import dev.flamenbaum.infrastructure.persistence.TransactionRepository;
import dev.flamenbaum.infrastructure.service.AccountApiService;
import dev.flamenbaum.infrastructure.service.OperationTypeRepositoryService;
import dev.flamenbaum.infrastructure.service.TransactionRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public AccountGateway accountGateway(RestTemplate restTemplate) {
        return new AccountApiService(restTemplate);
    }

    @Bean
    public OperationTypeGateway operationTypeGateway(OperationTypeRepository operationTypeRepository) {
        return new OperationTypeRepositoryService(operationTypeRepository);
    }

    @Bean
    public TransactionGateway transactionGateway(TransactionRepository transactionRepository) {
        return new TransactionRepositoryService(transactionRepository);
    }

    @Bean
    public CreateTransactionUseCase createTransactionUseCase
            (AccountGateway accountGateway, OperationTypeGateway operationTypeGateway, TransactionGateway transactionGateway) {
        return new CreateTransactionUseCase(transactionGateway, accountGateway, operationTypeGateway);
    }

}
