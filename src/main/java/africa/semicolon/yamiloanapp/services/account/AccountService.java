package africa.semicolon.yamiloanapp.services.account;

import africa.semicolon.yamiloanapp.dtos.requests.WithdrawFromWalletRequest;
import africa.semicolon.yamiloanapp.dtos.responses.WithdrawFromWalletResponse;
import africa.semicolon.yamiloanapp.data.models.Account;

public interface AccountService {
 Account save(Account account);
 Account findAccountById(long accountId);
 WithdrawFromWalletResponse withdrawProcessedLoanFromWallet(WithdrawFromWalletRequest withdrawFromWalletRequest);
 void deleteAll();
 long size();
}
