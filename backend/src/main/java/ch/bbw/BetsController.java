package ch.bbw;


import ch.bbw.bets.Bets;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("api/bets")
public class BetsController {
    BetService betService = new BetService();

    @GetMapping("/bets/{userId}")
    public ResponseEntity<?> getBets(@PathVariable UUID userId) {
        return betService.getBetsByUser(userId);
    }

    @PostMapping("/bets/save")
    public Bets saveBet(@RequestBody Bets bet) {
        return betService.saveBet(bet);
    }
}
