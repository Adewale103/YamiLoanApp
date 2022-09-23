package africa.semicolon.yamiloanapp.services.transaction;

import africa.semicolon.yamiloanapp.data.models.Transaction;
import africa.semicolon.yamiloanapp.data.repositories.TransactionRepository;
import africa.semicolon.yamiloanapp.exceptions.TransactionAlreadyExistException;
import africa.semicolon.yamiloanapp.exceptions.TransactionDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    @Override
    public Transaction save(Transaction transaction) {
        validateThatTransactionDoesNotExist(transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(()-> new TransactionDoesNotExistException("Transaction does not exist", 404) );
    }

    private void validateThatTransactionDoesNotExist(Transaction transaction){
        if(transactionRepository.existsById(transaction.getTransactionId())){
            throw new TransactionAlreadyExistException("Transaction already saved.",400);
        }
    }
}
