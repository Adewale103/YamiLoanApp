package africa.semicolon.yamiloanapp.services.account;

import africa.semicolon.yamiloanapp.data.models.Account;

public interface AccountService {
 Account save(Account account);
 Account findAccountById(Account account);
}
