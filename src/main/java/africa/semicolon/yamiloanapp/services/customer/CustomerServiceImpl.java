package africa.semicolon.yamiloanapp.services.customer;

import africa.semicolon.yamiloanapp.dtos.requests.*;
import africa.semicolon.yamiloanapp.dtos.responses.CustomerResponseDto;
import africa.semicolon.yamiloanapp.dtos.responses.RepayLoanResponse;
import africa.semicolon.yamiloanapp.dtos.responses.RequestLoanResponse;
import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.models.Customer;
import africa.semicolon.yamiloanapp.data.repositories.CustomerRepository;
import africa.semicolon.yamiloanapp.exceptions.CustomerAlreadyExistException;
import africa.semicolon.yamiloanapp.exceptions.CustomerDoesNotExistException;
import africa.semicolon.yamiloanapp.exceptions.LoanException;
import africa.semicolon.yamiloanapp.services.account.AccountService;
import africa.semicolon.yamiloanapp.services.loan.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final LoanService loanService;

    @Override
    public CustomerResponseDto addCustomer(AddCustomerRequest addCustomerRequest) {
        validateThatCustomerDoesNotExist(addCustomerRequest);
        boolean eligibleForLoan = addCustomerRequest.getSalary().compareTo(BigDecimal.valueOf(5000)) >= 0;
        BigDecimal maximumEligibleAmount = BigDecimal.ZERO;
        if(eligibleForLoan){
            maximumEligibleAmount = BigDecimal.valueOf(0.2).multiply(addCustomerRequest.getSalary());
        }
        Account account = Account.builder()
                .eligibleForLoan(eligibleForLoan)
                .stillOwing(false)
                .maximumEligibleAmount(maximumEligibleAmount)
                .build();

        accountService.save(account);

        Customer customer = buildCustomer(addCustomerRequest, account);
        customerRepository.save(customer);
        return buildCustomerResponseDto(account, customer);
    }

    @Override
    public RequestLoanResponse applyForLoan(ApplyForLoanRequest applyForLoanRequest) {
        Customer foundCustomer = customerRepository.findByEmail(applyForLoanRequest.getEmail()).
                orElseThrow(()-> new CustomerDoesNotExistException("customer "+applyForLoanRequest.getEmail()+" not found",404));

        Account foundCustomerAccount = foundCustomer.getAccount();
        if(foundCustomerAccount.isEligibleForLoan()){
            foundCustomerAccount.setMaximumEligibleAmount(BigDecimal.valueOf(0.2).multiply(foundCustomer.getSalary()));
        }
        if(foundCustomerAccount.isStillOwing()){
            throw new LoanException("Kindly pay off your previous loan to qualify for a new one",400);
        }

        else if(!foundCustomerAccount.isEligibleForLoan()){
            throw new LoanException("You are ineligible for a loan due to low salary amount",400);
        }

        if(applyForLoanRequest.getLoanRequestAmount().compareTo(foundCustomerAccount.getMaximumEligibleAmount()) > 0){
            applyForLoanRequest.setLoanRequestAmount(foundCustomerAccount.getMaximumEligibleAmount());
        }

        RequestLoanRequest requestLoanRequest = buildLoanRequest(applyForLoanRequest, foundCustomerAccount);

        return loanService.requestLoan(requestLoanRequest);
    }

    @Override
    public RepayLoanResponse repayLoan(RepayCustomerLoanRequest repayCustomerLoanRequest) {
        Customer foundCustomer = customerRepository.findById(Long.valueOf(repayCustomerLoanRequest.getCustomerId())).
                orElseThrow(()-> new CustomerDoesNotExistException("customer "+repayCustomerLoanRequest.getCustomerId()+" not found",404));

        Account foundCustomerAccount = foundCustomer.getAccount();
        if(foundCustomerAccount.getLoan().isFullyPaid()){
            throw new LoanException("You have fully paid your loan. You can request for a new one",400);
        }
        long loanId =foundCustomerAccount.getLoan().getLoanId();
        RepayLoanRequest repayLoanRequest = RepayLoanRequest.builder()
                .loanId(loanId)
                .account(foundCustomerAccount)
                .amount(repayCustomerLoanRequest.getAmount())
                .build();
        return loanService.repayLoan(repayLoanRequest);


    }

    private RequestLoanRequest buildLoanRequest(ApplyForLoanRequest applyForLoanRequest, Account account) {
        return RequestLoanRequest.builder()
                .loanRequestAmount(applyForLoanRequest.getLoanRequestAmount())
                .loanType(applyForLoanRequest.getLoanType())
                .account(account)
                .durationInMonth(applyForLoanRequest.getDurationInMonth())
                .build();
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
