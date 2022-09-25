package africa.semicolon.yamiloanapp.services.loan;

import africa.semicolon.yamiloanapp.dtos.requests.RepayLoanRequest;
import africa.semicolon.yamiloanapp.dtos.requests.RequestLoanRequest;
import africa.semicolon.yamiloanapp.dtos.responses.RepayLoanResponse;
import africa.semicolon.yamiloanapp.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Loan;
import africa.semicolon.yamiloanapp.data.models.Transaction;
import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import africa.semicolon.yamiloanapp.data.models.enums.TransactionType;
import africa.semicolon.yamiloanapp.data.repositories.LoanRepository;
import africa.semicolon.yamiloanapp.exceptions.LoanException;
import africa.semicolon.yamiloanapp.services.account.AccountService;
import africa.semicolon.yamiloanapp.services.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService{
    private final LoanRepository loanRepository;
    private final TransactionService transactionService;
    private final AccountService accountService;
    @Override
    public RequestLoanResponse requestLoan(RequestLoanRequest requestLoanRequest) {
        if(requestLoanRequest.getDurationInMonth() > 5){
            throw new LoanException("Cannot process Loan. Duration of payment can only be within 5 months",400);
        }
        double interestRate = computeInterestRate(requestLoanRequest);
        BigDecimal amountToReturn = computeAmountToReturn(requestLoanRequest.getLoanRequestAmount(),interestRate);
        double monthlyEMI = computeMonthlyEMI(amountToReturn, requestLoanRequest.getDurationInMonth());

        Loan loan = buildLoanFrom(requestLoanRequest, interestRate, amountToReturn, monthlyEMI);

        loanRepository.save(loan);
        Transaction transaction = buildApplyForLoanTransaction(requestLoanRequest, amountToReturn);

        transactionService.save(transaction);
        requestLoanRequest.getAccount().setLoan(loan);
        requestLoanRequest.getAccount().setWalletAmount(requestLoanRequest.getAccount().getWalletAmount().add(requestLoanRequest.getLoanRequestAmount()));
        requestLoanRequest.getAccount().setEligibleForLoan(false);
        requestLoanRequest.getAccount().setStillOwing(true);
        requestLoanRequest.getAccount().setMaximumEligibleAmount(BigDecimal.ZERO);
        requestLoanRequest.getAccount().getTransactionList().add(transaction);
        accountService.save(requestLoanRequest.getAccount());
        return buildRequestLoanResponseFrom(loan);
    }

    @Override
    public Loan findLoanById(long loanId) {
        return loanRepository.findById(loanId).orElseThrow(()->
                new LoanException("Loan not found. Please check loan Id again ",404));
    }

    @Override
    public RepayLoanResponse repayLoan(RepayLoanRequest repayLoanRequest) {
        Loan loan = repayLoanRequest.getAccount().getLoan();
        Account account = repayLoanRequest.getAccount();
        if (repayLoanRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanException("Invalid amount, kindly check again", 400);
        }
        if (LocalDate.now().isBefore(loan.getDueDate()) || LocalDate.now().isEqual(loan.getDueDate())) {
            account.setWalletAmount(repayLoanRequest.getAmount().add(account.getWalletAmount()));
        } else if (DAYS.between(LocalDate.now(), loan.getDueDate()) == -1) {
                account.setWalletAmount(repayLoanRequest.getAmount().add(account.getWalletAmount()).
                        subtract(repayLoanRequest.getAmount().multiply(BigDecimal.valueOf(0.02))));
        }
        else if (DAYS.between(LocalDate.now(), loan.getDueDate()) == -2) {
            account.setWalletAmount(repayLoanRequest.getAmount().add(account.getWalletAmount()).
                    subtract(repayLoanRequest.getAmount().multiply(BigDecimal.valueOf(0.04))));
        }
        else if (DAYS.between(LocalDate.now(), loan.getDueDate()) == -3) {
            account.setWalletAmount(repayLoanRequest.getAmount().add(account.getWalletAmount()).
                    subtract(repayLoanRequest.getAmount().multiply(BigDecimal.valueOf(0.06))));
        }
        else if (DAYS.between(LocalDate.now(), loan.getDueDate()) < -3) {
            account.setWalletAmount(repayLoanRequest.getAmount().add(account.getWalletAmount()).
                    subtract(repayLoanRequest.getAmount().multiply(BigDecimal.valueOf(0.08))));
        }
        if (account.getWalletAmount().compareTo(BigDecimal.ZERO) >= 0) {
            account.setEligibleForLoan(true);
            account.setStillOwing(false);
            account.setLoan(null);
        }
        loan.setFullyPaid(true);
        loanRepository.save(loan);
        Transaction transaction = buildRepayLoanTransaction(repayLoanRequest,account);
        transactionService.save(transaction);
        account.getTransactionList().add(transaction);
        accountService.save(account);
        return RepayLoanResponse.builder()
                .message("You've successfully paid "+repayLoanRequest.getAmount())
                .successful(true)
                .build();
    }

    private Transaction buildRepayLoanTransaction(RepayLoanRequest repayLoanRequest, Account account) {
        return Transaction.builder()
                .transactionAmount(repayLoanRequest.getAmount())
                .balanceAfterTransaction(account.getWalletAmount())
                .transactionType(TransactionType.CREDIT)
                .transactionTime(LocalDateTime.now())
                .message("Successfully paid "+repayLoanRequest.getAmount())
                .build();
    }


    private Transaction buildApplyForLoanTransaction(RequestLoanRequest requestLoanRequest, BigDecimal amountToReturn) {
        return Transaction.builder()
                .transactionAmount(amountToReturn)
                .balanceAfterTransaction(requestLoanRequest.getAccount().getWalletAmount().add(amountToReturn))
                .transactionType(TransactionType.CREDIT)
                .transactionTime(LocalDateTime.now())
                .message("Congrats! Your wallet has been credited with "+ requestLoanRequest.getLoanRequestAmount())
                .build();
    }

    private RequestLoanResponse buildRequestLoanResponseFrom(Loan loan) {
        return RequestLoanResponse.builder()
                .loanId(loan.getLoanId())
                .loanType(loan.getLoanType())
                .amountToReturn(loan.getAmountToReturn())
                .dueDate(loan.getDueDate())
                .collectionDate(loan.getCollectionDate())
                .durationInMonth(loan.getDurationInMonth())
                .monthlyEMI(loan.getMonthlyEMI())
                .interestRate(loan.getInterestRate())
                .message("Your Loan have been successfully processed")
                .build();
    }

    private Loan buildLoanFrom(RequestLoanRequest requestLoanRequest, double interestRate, BigDecimal amountToReturn, double monthlyEMI) {
        return Loan.builder()
                .loanRequestAmount(requestLoanRequest.getLoanRequestAmount())
                .loanType(LoanType.valueOf(requestLoanRequest.getLoanType()))
                .collectionDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(30L * requestLoanRequest.getDurationInMonth()))
                .durationInMonth(requestLoanRequest.getDurationInMonth())
                .amountToReturn(amountToReturn)
                .monthlyEMI(monthlyEMI)
                .fullyPaid(false)
                .interestRate(interestRate)
                .build();
    }

    private double computeInterestRate(RequestLoanRequest requestLoanRequest){
        if(requestLoanRequest.getDurationInMonth() < 1){
            throw new LoanException("Invalid duration in month ", 400);
        }
        else return 0.05 * requestLoanRequest.getDurationInMonth();
    }

    private double computeMonthlyEMI(BigDecimal amountToReturn, int durationInMonth){
        return amountToReturn.divide(BigDecimal.valueOf(durationInMonth), RoundingMode.UP).doubleValue();
    }

    private BigDecimal computeAmountToReturn(BigDecimal loanRequestAmount, double interestRate){
        return loanRequestAmount.multiply(BigDecimal.valueOf(interestRate)).add(loanRequestAmount);
    }

}
