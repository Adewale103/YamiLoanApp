package africa.semicolon.yamiloanapp.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawFromWalletResponse {
    private String message;
    private boolean successful;
}
