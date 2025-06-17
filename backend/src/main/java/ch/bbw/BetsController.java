package ch.bbw;

import ch.bbw.bets.Bets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/bets")
@CrossOrigin("*")
public class BetsController {

    @Autowired
    private BetService betService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBets(@PathVariable UUID userId) {
        return betService.getBetsByUser(userId);
    }

    @PostMapping("/save")
    public Bets saveBet(@RequestBody Bets bet) {
        return betService.saveBet(bet);
    }
}