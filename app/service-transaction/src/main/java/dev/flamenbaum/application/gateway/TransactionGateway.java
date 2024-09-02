package dev.flamenbaum.application.gateway;

import dev.flamenbaum.core.domain.Transaction;

public interface TransactionGateway {

    Transaction create(Transaction transaction);

}
