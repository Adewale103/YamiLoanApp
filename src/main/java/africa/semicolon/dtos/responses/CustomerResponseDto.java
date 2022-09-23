package africa.semicolon.dtos.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {
    private long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal walletAmount;
}
