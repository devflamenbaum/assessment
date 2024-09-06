package dev.flamenbaum.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    private Long accountId;
    private String documentNumber;
    private BigDecimal availableCreditLimit;
}
