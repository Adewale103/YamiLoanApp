package africa.semicolon.yamiloanapp.dtos.requests;

import africa.semicolon.yamiloanapp.data.models.Account;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepayLoanRequest {
    private BigDecimal amount;
    private Account account;
    private long loanId;
}
