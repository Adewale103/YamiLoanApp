package africa.semicolon.yamiloanapp.data.repositories;

import africa.semicolon.yamiloanapp.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
