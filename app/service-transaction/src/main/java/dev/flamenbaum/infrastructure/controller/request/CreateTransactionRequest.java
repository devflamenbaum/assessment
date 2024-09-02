package dev.flamenbaum.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("operation_type_id")
    private Long operationTypeId;
    private BigDecimal amount;
}
