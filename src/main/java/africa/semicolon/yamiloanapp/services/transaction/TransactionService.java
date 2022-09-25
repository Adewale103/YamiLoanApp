package africa.semicolon.yamiloanapp.services.transaction;

import africa.semicolon.yamiloanapp.data.models.Transaction;

public interface TransactionService {
    Transaction save(Transaction transaction);
    Transaction findTransactionById(long transactionId);
    void deleteAll();
    long size();
}
