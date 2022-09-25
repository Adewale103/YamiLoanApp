package africa.semicolon.yamiloanapp.services.loan;

import africa.semicolon.yamiloanapp.dtos.requests.RepayLoanRequest;
import africa.semicolon.yamiloanapp.dtos.requests.RequestLoanRequest;
import africa.semicolon.yamiloanapp.dtos.responses.RepayLoanResponse;
import africa.semicolon.yamiloanapp.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Loan;

public interface LoanService {
 RequestLoanResponse requestLoan(RequestLoanRequest requestLoanRequest);
 Loan findLoanById(long loanId);
 RepayLoanResponse repayLoan(RepayLoanRequest repayLoanRequest);

}
