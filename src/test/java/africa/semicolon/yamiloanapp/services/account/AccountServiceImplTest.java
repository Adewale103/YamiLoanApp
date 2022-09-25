package africa.semicolon.yamiloanapp.services.account;

import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Loan;
import africa.semicolon.yamiloanapp.data.models.Transaction;
import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import africa.semicolon.yamiloanapp.dtos.requests.WithdrawFromWalletRequest;
import africa.semicolon.yamiloanapp.dtos.responses.WithdrawFromWalletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    private  Account account;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = Loan.builder()
                .loanType(LoanType.BUSINESS_LOAN)
                .amountToReturn(BigDecimal.valueOf(250))
                .build();
         account = Account.builder()
             .walletAmount(BigDecimal.valueOf(235))
             .loan(loan)
             .build();
    }


    @AfterEach
    void tearDown(){
        accountService.deleteAll();
    }

    @Test
    public void accountCanBeCreated(){
        accountService.save(account);
        assertEquals(1L, accountService.size());
    }

    @Test
    public void findAccountByIdTest(){
        accountService.save(account);
        Account foundAccount = accountService.findAccountById(account.getAccountId());
        assertEquals(new BigDecimal("235.00"),foundAccount.getWalletAmount());
        assertEquals(1L, accountService.size());
    }

    @Test
    public void processedAmountCanBeWithdrawnFromAccountTest(){
       accountService.save(account);
        WithdrawFromWalletRequest withdrawFromWalletRequest = WithdrawFromWalletRequest.builder()
                .accountId(account.getAccountId())
                .amountToWithdraw(BigDecimal.valueOf(235))
                .build();
       WithdrawFromWalletResponse withdrawFromWalletResponse =
               accountService.withdrawProcessedLoanFromWallet(withdrawFromWalletRequest);
       assertTrue(withdrawFromWalletResponse.isSuccessful());
        Account foundAccount = accountService.findAccountById(account.getAccountId());
       assertEquals(new BigDecimal("-250.00"), foundAccount.getWalletAmount());
    }
}