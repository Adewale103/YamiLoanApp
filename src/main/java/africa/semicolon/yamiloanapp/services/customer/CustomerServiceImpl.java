package africa.semicolon.yamiloanapp.services.customer;

import africa.semicolon.dtos.requests.AddCustomerRequest;
import africa.semicolon.dtos.requests.ApplyForLoanRequest;
import africa.semicolon.dtos.responses.CustomerResponseDto;
import africa.semicolon.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Customer;
import africa.semicolon.yamiloanapp.data.repositories.CustomerRepository;
import africa.semicolon.yamiloanapp.exceptions.CustomerAlreadyExistException;
import africa.semicolon.yamiloanapp.services.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @Override
    public CustomerResponseDto addCustomer(AddCustomerRequest addCustomerRequest) {
        validateThatCustomerDoesNotExist(addCustomerRequest);
        Account account = Account.builder()
                .eligibleForLoan(addCustomerRequest.getSalary().compareTo(BigDecimal.valueOf(5000)) >= 0)
                .build();
        accountService.save(account);

        Customer customer = buildCustomer(addCustomerRequest, account);
        customerRepository.save(customer);
        return buildCustomerResponseDto(account, customer);
    }

    @Override
    public RequestLoanResponse applyForLoan(ApplyForLoanRequest applyForLoanRequest) {
        return null;
    }

    private CustomerResponseDto buildCustomerResponseDto(Account account, Customer customer) {
        return CustomerResponseDto.builder()
                .walletAmount(account.getWalletAmount())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .customerId(customer.getCustomerId())
                .build();
    }

    private Customer buildCustomer(AddCustomerRequest addCustomerRequest, Account account) {
        return Customer.builder()
                .dateJoined(LocalDate.now())
                .email(addCustomerRequest.getEmail())
                .firstName(addCustomerRequest.getFirstName())
                .lastName(addCustomerRequest.getLastName())
                .password(addCustomerRequest.getPassword())
                .phoneNumber(addCustomerRequest.getPhoneNumber())
                .NIN(addCustomerRequest.getNIN())
                .salary(addCustomerRequest.getSalary())
                .account(account)
                .build();
    }

    private void validateThatCustomerDoesNotExist(AddCustomerRequest addCustomerRequest){
        if(customerRepository.existsByEmail(addCustomerRequest.getEmail())){
            throw new CustomerAlreadyExistException("customer already registered",400);
        }
    }
}
