package africa.semicolon.yamiloanapp.data.repositories;

import africa.semicolon.yamiloanapp.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
