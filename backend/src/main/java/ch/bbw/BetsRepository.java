package ch.bbw;

import ch.bbw.bets.Bets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetsRepository extends JpaRepository<Bets, UUID> {
    List<Bets> findByUserId(UUID userId);
}