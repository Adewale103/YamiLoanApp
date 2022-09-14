package africa.semicolon.yamiloanapp.data.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
public class Wallet {
    @Id
    private long id;
    private String walletNumber;
    private BigDecimal walletBalance;
}
