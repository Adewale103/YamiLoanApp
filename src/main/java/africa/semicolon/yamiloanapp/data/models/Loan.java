package africa.semicolon.yamiloanapp.data.models;

import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loanId;
    private BigDecimal loanRequestAmount;
    private BigDecimal amountToReturn;
    private double interestRate;
    @Enumerated(value = EnumType.STRING)
    private LoanType loanType;
    private int durationInMonth;
    private LocalDate collectionDate;
    private LocalDate dueDate;
    private boolean fullyPaid;
    private double monthlyEMI;



}
