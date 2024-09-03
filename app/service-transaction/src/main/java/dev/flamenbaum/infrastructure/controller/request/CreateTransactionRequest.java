package dev.flamenbaum.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotNull(message = "The account_id is required")
    @Pattern(regexp = "^[0-9]*$", message = "The account_id only accepts digits!")
    @Min(value = 1, message = "The account_id needs to be greater than 1")
    @JsonProperty("account_id")
    private Long accountId;
    @NotNull(message = "The operation_type_id is required")
    @Pattern(regexp = "^[0-9]*$", message = "The operation_type_id only accepts digits!")
    @Min(value = 1, message = "The operation_type_id needs to be greater than 1")
    @JsonProperty("operation_type_id")
    private Long operationTypeId;
    private BigDecimal amount;
}
