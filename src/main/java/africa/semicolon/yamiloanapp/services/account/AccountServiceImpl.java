package africa.semicolon.yamiloanapp.services.account;

import africa.semicolon.yamiloanapp.dtos.requests.WithdrawFromWalletRequest;
import africa.semicolon.yamiloanapp.dtos.responses.WithdrawFromWalletResponse;
import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Transaction;
import africa.semicolon.yamiloanapp.data.models.enums.TransactionType;
import africa.semicolon.yamiloanapp.data.repositories.AccountRepository;
import africa.semicolon.yamiloanapp.exceptions.AccountDoesNotExistException;
import africa.semicolon.yamiloanapp.exceptions.InsufficientBalanceException;
import africa.semicolon.yamiloanapp.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    @Override
    public Account save(Account account) {
     return accountRepository.save(account);
    }

    @Override
    public Account findAccountById(long accountId) {
        return accountRepository.findById(accountId).
                orElseThrow(()-> new AccountDoesNotExistException("account does not exist",404));
    }

    @Override
    public WithdrawFromWalletResponse withdrawProcessedLoanFromWallet(WithdrawFromWalletRequest withdrawFromWalletRequest) {
        Account account = findAccountById(withdrawFromWalletRequest.getAccountId());
        if(account.getWalletAmount().compareTo(withdrawFromWalletRequest.getAmountToWithdraw()) < 0){
            throw new InsufficientBalanceException("Insufficient cash in your wallet", 400);
        }
        account.setWalletAmount(account.getWalletAmount().subtract(withdrawFromWalletRequest.getAmountToWithdraw()).subtract(account.getLoan().getAmountToReturn()));

        Transaction transaction = buildWithdrawalTransaction(withdrawFromWalletRequest, account);
        transactionService.save(transaction);
        account.getTransactionList().add(transaction);
        accountRepository.save(account);
        return WithdrawFromWalletResponse.builder()
                .successful(true)
                .message(withdrawFromWalletRequest.getAmountToWithdraw() +" has been successfully sent to "+ withdrawFromWalletRequest.getDestinationAccountName())
                .build();
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public long size() {
        return accountRepository.count();
    }

    private Transaction buildWithdrawalTransaction(WithdrawFromWalletRequest withdrawFromWalletRequest, Account account) {
        return Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .transactionType(TransactionType.DEBIT)
                .balanceAfterTransaction(account.getWalletAmount())
                .message(withdrawFromWalletRequest.getAmountToWithdraw() +" was sent to " + withdrawFromWalletRequest.getDestinationAccountName())
                .transactionAmount(withdrawFromWalletRequest.getAmountToWithdraw())
                .build();
    }

}
