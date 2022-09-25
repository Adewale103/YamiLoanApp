package africa.semicolon.yamiloanapp.services.customer;

import africa.semicolon.yamiloanapp.dtos.requests.AddCustomerRequest;
import africa.semicolon.yamiloanapp.dtos.requests.ApplyForLoanRequest;
import africa.semicolon.yamiloanapp.dtos.requests.RepayCustomerLoanRequest;
import africa.semicolon.yamiloanapp.dtos.responses.CustomerResponseDto;
import africa.semicolon.yamiloanapp.dtos.responses.RepayLoanResponse;
import africa.semicolon.yamiloanapp.dtos.responses.RequestLoanResponse;

public interface CustomerService {
  CustomerResponseDto addCustomer(AddCustomerRequest addCustomerRequest);
  RequestLoanResponse applyForLoan(ApplyForLoanRequest applyForLoanRequest);
  RepayLoanResponse repayLoan(RepayCustomerLoanRequest repayCustomerLoanRequest);
}
