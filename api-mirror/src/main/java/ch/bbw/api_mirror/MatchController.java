package ch.bbw.api_mirror;


import ch.bbw.api_mirror.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final ch.bbw.api_mirror.MatchService matchService;

    @Autowired
    public MatchController(ch.bbw.api_mirror.MatchService matchService) {
        this.matchService = matchService;
    }


    @GetMapping("/bundesliga")
    public List<Match> getBundesligaMatches() {
        return matchService.getMatches("BL1", "SCHEDULED");
    }

    @GetMapping("/bundesliga/live")
    public List<Match> getLiveMatches() {
        return matchService.getMatches("BL1", "LIVE");
    }

}

