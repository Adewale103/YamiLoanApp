package africa.semicolon.yamiloanapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
