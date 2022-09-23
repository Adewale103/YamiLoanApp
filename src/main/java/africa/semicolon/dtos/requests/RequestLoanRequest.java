package africa.semicolon.dtos.requests;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
