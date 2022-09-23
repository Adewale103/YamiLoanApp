package africa.semicolon.dtos.responses;

import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLoanResponse {
    private String message;
    private long loanId;
    private BigDecimal amountToReturn;
    private double interestRate;
    private LoanType loanType;
    private int durationInMonth;
    private LocalDate collectionDate;
    private LocalDate dueDate;
    private double monthlyEMI;
}
