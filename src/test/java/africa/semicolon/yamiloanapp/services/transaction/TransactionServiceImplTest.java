package africa.semicolon.yamiloanapp.services.transaction;

import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Loan;
import africa.semicolon.yamiloanapp.data.models.Transaction;
import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import africa.semicolon.yamiloanapp.data.models.enums.TransactionType;
import africa.semicolon.yamiloanapp.data.repositories.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;
    private Account account;
    private Loan loan;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        loan = Loan.builder()
                .loanType(LoanType.BUSINESS_LOAN)
                .amountToReturn(BigDecimal.valueOf(250))
                .build();
        transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .transactionAmount(new BigDecimal("3455.00"))
                .transactionType(TransactionType.CREDIT)
                .build();
    }

    @AfterEach
    void tearDown() {
        transactionService.deleteAll();
    }

    @Test
    public void transactionCanBeSavedTest(){
        transactionService.save(transaction);
        assertEquals(1L,transactionService.size());
    }
    @Test
    public void transactionCanBeFoundByIdTest(){
        transactionService.save(transaction);
        Transaction foundTransaction = transactionService.findTransactionById(transaction.getTransactionId());
        assertEquals(foundTransaction.getTransactionAmount(),transaction.getTransactionAmount());
    }
}