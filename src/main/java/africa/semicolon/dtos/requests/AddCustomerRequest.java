package africa.semicolon.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddCustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private BigDecimal salary;
    private String phoneNumber;
    private String NIN;
}
