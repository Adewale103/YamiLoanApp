package africa.semicolon.yamiloanapp.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyForLoanRequest {
    private String email;
    private String accountPassword;
    private BigDecimal loanRequestAmount;
    private String loanType;
    private int durationInMonth;
}
