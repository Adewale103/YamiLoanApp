package africa.semicolon.yamiloanapp.data.repositories;

import africa.semicolon.yamiloanapp.data.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
