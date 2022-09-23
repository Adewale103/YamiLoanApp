package africa.semicolon.yamiloanapp.data.models;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @SequenceGenerator(name = "user_id_sequence",
            allocationSize = 1,
            sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private long customerId;
    private String firstName;
    private String lastName;
    private String email;
    @CreationTimestamp
    @Column(name = "dateJoined", updatable = false)
    private LocalDate dateJoined;
    private String password;
    private BigDecimal salary;
    private String phoneNumber;
    private String NIN;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "yamiBank",updatable = false,insertable = false)
    private YamiBank yamiBank;
}
