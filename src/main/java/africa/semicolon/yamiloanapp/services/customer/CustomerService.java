package africa.semicolon.yamiloanapp.services.customer;

import africa.semicolon.dtos.requests.AddCustomerRequest;
import africa.semicolon.dtos.requests.ApplyForLoanRequest;
import africa.semicolon.dtos.responses.CustomerResponseDto;
import africa.semicolon.dtos.responses.RequestLoanResponse;

public interface CustomerService {
  CustomerResponseDto addCustomer(AddCustomerRequest addCustomerRequest);
  RequestLoanResponse applyForLoan(ApplyForLoanRequest applyForLoanRequest);
}
