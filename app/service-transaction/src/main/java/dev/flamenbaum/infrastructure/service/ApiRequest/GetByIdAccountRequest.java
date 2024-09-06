package dev.flamenbaum.infrastructure.service.ApiRequest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdAccountRequest {

    private Long accountId;
    private String documentNumber;
    private BigDecimal availableCreditLimit;
}
