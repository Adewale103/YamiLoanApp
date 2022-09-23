package africa.semicolon.yamiloanapp.services.account;

import africa.semicolon.yamiloanapp.data.models.Account;
import africa.semicolon.yamiloanapp.data.repositories.AccountRepository;
import africa.semicolon.yamiloanapp.exceptions.AccountAlreadyExistException;
import africa.semicolon.yamiloanapp.exceptions.AccountDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
     validateThatAccountDoesNotExist(account);
     return accountRepository.save(account);
    }

    @Override
    public Account findAccountById(Account account) {
        return accountRepository.findById(account.getAccountId()).
                orElseThrow(()-> new AccountDoesNotExistException("account does not exist",404));
    }

    private void validateThatAccountDoesNotExist(Account account){
        if(accountRepository.existsById(account.getAccountId())){
            throw new AccountAlreadyExistException("Account already exist",400);
        }
    }
}
