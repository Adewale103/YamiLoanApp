package africa.semicolon.yamiloanapp.services.customer;

import africa.semicolon.dtos.requests.AddCustomerRequest;
import africa.semicolon.dtos.responses.CustomerResponseDto;

public interface CustomerService {
  CustomerResponseDto addCustomer(AddCustomerRequest addCustomerRequest);
}
