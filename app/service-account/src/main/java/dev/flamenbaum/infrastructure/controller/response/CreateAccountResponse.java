package dev.flamenbaum.infrastructure.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountResponse {

    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("document_number")
    private String documentNumber;
}
