package ch.bbw;

import ch.bbw.bets.Bets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BetsRepository extends JpaRepository<Bets, UUID> { }