package ch.bbw;

import ch.bbw.bets.Bets;
import ch.bbw.external.GameResult;
import ch.bbw.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BetService {
    @Autowired
    private BetsRepository betsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String apiKey;
    public ResponseEntity<List<Map<String, Object>>> getBetsByUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID muss angegeben werden");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User nicht gefunden"));

        List<Bets> bets = betsRepository.findByUserId(userId);
        List<Bets> stillOpen = new ArrayList<>();

        for (Bets bet : bets) {
            String url = String.format(
                    "https://api.football-data.org/v4/matches/%d",
                    bet.getGameId()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Auth-Token", apiKey);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<GameResult> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, GameResult.class
            );
            GameResult result = response.getBody();
            if (result == null) {
                stillOpen.add(bet);
                continue;
            }

            if ("FINISHED".equalsIgnoreCase(result.getStatus())) {
                boolean homeWon = result.getHomeScore() > result.getAwayScore();
                boolean userGuessedHome = bet.isHomeBet();

                if ((homeWon && userGuessedHome) || (!homeWon && !userGuessedHome)) {
                    int payout = bet.getBetAmount() * 2;
                    user.setBalance(user.getBalance() + payout);
                }
                betsRepository.delete(bet);
            } else {
                stillOpen.add(bet);
            }
        }

        userRepository.save(user);

        List<Map<String, Object>> resultList = stillOpen.stream()
                .map(b -> Map.<String, Object>of(
                        "gameId",    b.getGameId(),
                        "betAmount", b.getBetAmount(),
                        "homeBet",   b.isHomeBet()
                ))
                .collect(Collectors.toList());

        if (resultList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultList);
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

        user.setBalance(user.getBalance() - bet.getBetAmount());
        userRepository.save(user);

        return betsRepository.save(bet);
    }
}