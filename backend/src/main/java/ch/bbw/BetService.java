package ch.bbw;

import ch.bbw.bets.Bets;
import ch.bbw.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BetService {
    @Autowired
    private BetsRepository betsRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<Map<String, Object>>> getBetsByUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID muss angegeben werden");
        }
        List<Bets> bets = betsRepository.findByUserId(userId);
        if (bets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Map<String, Object>> result = bets.stream()
                .map(b -> Map.<String, Object>of(
                        "gameId",    b.getGameId(),
                        "betAmount", b.getBetAmount()
                ))
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
        if (bet.getGameId() == null) {
            throw new IllegalArgumentException("Spiel ID muss angegeben werden");
        }

        User user = userRepository.findById(bet.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User nicht gefunden"));

        if (user.getBalance() < bet.getBetAmount()) {
            throw new IllegalArgumentException("Nicht genügend Guthaben");
        }

        Bets saved = betsRepository.save(bet);

        user.setBalance(user.getBalance() - saved.getBetAmount());
        user.getBets().add(saved.getId());
        userRepository.save(user);

        return saved;
    }
}