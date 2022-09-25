package africa.semicolon.yamiloanapp.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepayCustomerLoanRequest {
    private String customerId;
    private BigDecimal amount;
}
