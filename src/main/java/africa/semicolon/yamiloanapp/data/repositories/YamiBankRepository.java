package africa.semicolon.yamiloanapp.data.repositories;

import africa.semicolon.yamiloanapp.data.models.YamiBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YamiBankRepository extends JpaRepository<YamiBank, Long> {
}
