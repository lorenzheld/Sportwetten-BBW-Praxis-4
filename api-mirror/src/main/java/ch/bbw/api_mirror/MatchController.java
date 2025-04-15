package ch.bbw.api_mirror;

import ch.bbw.api_mirror.MatchService;
import ch.bbw.api_mirror.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/sync")
    public String syncMatches() {
        matchService.syncMatches();
        return "Matches synced successfully!";
    }

    @GetMapping("/bundesliga")
    public List<Match> getBundesligaMatches() {
        return matchService.getMatches("Bundesliga", "SCHEDULED");
    }

    @GetMapping("/bundesliga/live")
    public List<Match> getLiveMatches() {
        return matchService.getMatches("Bundesliga", "LIVE");
    }
}
