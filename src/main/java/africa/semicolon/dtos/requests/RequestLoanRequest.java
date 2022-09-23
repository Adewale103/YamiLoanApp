package africa.semicolon.dtos.requests;

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
}
