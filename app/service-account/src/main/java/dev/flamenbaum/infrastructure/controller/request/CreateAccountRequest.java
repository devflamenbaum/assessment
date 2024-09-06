package dev.flamenbaum.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "The document_number is required!")
    @Pattern(regexp = "^[0-9]*$", message = "The document_number only accepts digits!")
    @JsonProperty("document_number")
    private String documentNumber;
    @NotNull(message = "The available_credit_limit is required!")
    @Min(value = 0)
    @JsonProperty("available_credit_limit")
    private BigDecimal availableCreditLimit;
}
