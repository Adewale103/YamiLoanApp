package africa.semicolon.yamiloanapp.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawFromWalletRequest {
    private Long accountId;
    private BigDecimal amountToWithdraw;
    private String destinationAccountName;
    private String destinationBankName;
    private String destinationAccountNumber;
}
