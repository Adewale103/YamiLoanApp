package africa.semicolon.yamiloanapp.data.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private BigDecimal walletAmount;
    @OneToOne(cascade = CascadeType.ALL)
    private Loan loan;
    private boolean eligibleForLoan;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionList = new ArrayList<>();
}
