package system.sales.system_sales.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import system.sales.system_sales.Entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}
