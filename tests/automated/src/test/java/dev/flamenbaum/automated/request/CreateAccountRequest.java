package dev.flamenbaum.automated.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountRequest {

    @JsonProperty("document_number")
    private String documentNumber;

    public CreateAccountRequest(String documentNumber) {
        this.documentNumber = documentNumber;
    }

}
