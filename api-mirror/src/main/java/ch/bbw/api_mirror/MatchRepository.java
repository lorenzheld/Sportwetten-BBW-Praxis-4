package ch.bbw.api_mirror;

import ch.bbw.api_mirror.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByCompetitionAndStatus(String competition, String status);
}
