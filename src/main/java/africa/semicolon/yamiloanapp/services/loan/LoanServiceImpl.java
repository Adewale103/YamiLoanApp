package africa.semicolon.yamiloanapp.services.loan;

import africa.semicolon.dtos.requests.RequestLoanRequest;
import africa.semicolon.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Loan;
import africa.semicolon.yamiloanapp.data.models.enums.LoanType;
import africa.semicolon.yamiloanapp.data.repositories.LoanRepository;
import africa.semicolon.yamiloanapp.exceptions.LoanException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService{
    private final LoanRepository loanRepository;
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
        return buildRequestLoanResponseFrom(loan);
    }

    @Override
    public Loan findLoanById(long loanId) {
        return loanRepository.findById(loanId).orElseThrow(()->
                new LoanException("Loan not found. Please check loan Id again ",404));
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
                .dueDate(LocalDate.of(LocalDate.now().getYear(),
                        validateMonthInput(LocalDate.now().getMonth().getValue()+ requestLoanRequest.getDurationInMonth()),
                        LocalDate.now().getDayOfMonth()))
                .durationInMonth(requestLoanRequest.getDurationInMonth())
                .amountToReturn(amountToReturn)
                .monthlyEMI(monthlyEMI)
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
    private int validateMonthInput(int monthNumber){
        if(monthNumber > 12){
            return monthNumber % 12;
        }
        return monthNumber;
    }

}
