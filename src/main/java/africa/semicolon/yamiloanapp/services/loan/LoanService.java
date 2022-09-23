package africa.semicolon.yamiloanapp.services.loan;

import africa.semicolon.dtos.requests.RequestLoanRequest;
import africa.semicolon.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Loan;

public interface LoanService {
 RequestLoanResponse requestLoan(RequestLoanRequest requestLoanRequest);
 Loan findLoanById(long loanId);
}
