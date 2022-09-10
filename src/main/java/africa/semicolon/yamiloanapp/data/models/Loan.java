package africa.semicolon.yamiloanapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loanId;
    private double loanAmount;
    private String loanType;
    private int durationInMonth;
    private double monthlyEMI;

    @ManyToOne
    @JoinColumn(name= "id" ,insertable = false,updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Transaction> transactions = new ArrayList<>();



}
