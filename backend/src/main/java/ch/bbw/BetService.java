package ch.bbw;

import ch.bbw.bets.Bets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BetService {
    @Autowired
    private BetsRepository betsRepository;

    public ResponseEntity<List<Map<String, Object>>> getBetsByUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID muss angegeben werden");
        }

        List<Bets> bets = betsRepository.findByUserId(userId);
        if (bets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Map<String, Object>> result = bets.stream()
                .map(b -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("gameId",   b.getGameId());
                    m.put("betAmount", b.getBetAmount());
                    return m;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
    public Bets saveBet(Bets bet) {
        if (bet.getUserId() == null) {
            throw new IllegalArgumentException("User ID muss angegeben werden");
        }
        if (bet.getBetAmount() <= 0) {
            throw new IllegalArgumentException("Betrag muss grösser als 0 sein");
        }
        if (bet.getGameId() == null){
            throw new IllegalArgumentException("Spiel ID muss angegeben werden");
        }
        return betsRepository.save(bet);
    }
}
