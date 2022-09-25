package africa.semicolon.yamiloanapp.data.models;

import africa.semicolon.yamiloanapp.data.models.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal transactionAmount;
    private BigDecimal balanceAfterTransaction;
    private String message;
    @CreationTimestamp
    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name = "account",updatable = false, insertable = false)
    private Account account;
}
