package africa.semicolon.yamiloanapp.services.loan;

import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Loan;
import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import africa.semicolon.yamiloanapp.data.repositories.LoanRepository;
import africa.semicolon.yamiloanapp.dtos.requests.RequestLoanRequest;
import africa.semicolon.yamiloanapp.services.account.AccountService;
import africa.semicolon.yamiloanapp.services.transaction.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    @Autowired
    private LoanService loanService;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private TransactionService transactionService;
    @Mock
    private AccountService accountService;
    private RequestLoanRequest requestLoanRequest;
    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .walletAmount(BigDecimal.valueOf(100))
                .build();
        requestLoanRequest = RequestLoanRequest.builder()
                .loanRequestAmount(BigDecimal.valueOf(2000))
                .loanType("HOUSE_LOAN")
                .durationInMonth(3)
                .account(account)
                .build();
    }

    @Test
    public void loanCanBeRequestedTest(){

    }


}