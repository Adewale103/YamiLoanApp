package africa.semicolon.yamiloanapp.dtos.requests;

import africa.semicolon.yamiloanapp.data.models.Account;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLoanRequest {
    private BigDecimal loanRequestAmount;
    private String loanType;
    private int durationInMonth;
    private Account account;
}
