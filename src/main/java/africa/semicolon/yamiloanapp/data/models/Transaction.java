package africa.semicolon.yamiloanapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transaction")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String transactionType;
    private String message;
    private LocalDateTime transactionTime;
}
