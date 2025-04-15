package ch.bbw.api_mirror;

import ch.bbw.api_mirror.model.Area;
import ch.bbw.api_mirror.model.Competition;
import ch.bbw.api_mirror.model.FullTime;
import ch.bbw.api_mirror.model.HalfTime;
import ch.bbw.api_mirror.model.Match;
import ch.bbw.api_mirror.model.Odds;
import ch.bbw.api_mirror.model.Season;
import ch.bbw.api_mirror.model.Score;
import ch.bbw.api_mirror.model.Team;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;

    public MatchService(RestTemplate restTemplate, MatchRepository matchRepository) {
        this.restTemplate = restTemplate;
        this.matchRepository = matchRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Cacheable(value = "matches", key = "'bl1_' + #status")
    public List<Match> getMatches(String competitionCode, String status) {
        List<Match> matches = matchRepository.findByCompetitionAndStatus("Bundesliga", status);
        if (!matches.isEmpty()) {
            return matches;
        }
        String url = String.format("https://api.football-data.org/v4/competitions/%s/matches?status=%s", competitionCode, status);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String jsonResponse = response.getBody();
            List<Match> fetchedMatches = parseMatches(jsonResponse);
            matchRepository.saveAll(fetchedMatches);
            return fetchedMatches;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Match> parseMatches(String response) {
        List<Match> matchList = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode matchesNode = root.path("matches");
            for (JsonNode node : matchesNode) {
                Match match = new Match();
                match.setId(node.path("id").asLong());
                match.setUtcDate(node.path("utcDate").asText());
                match.setStatus(node.path("status").asText());
                match.setMatchday(node.path("matchday").asInt());
                match.setStage(node.path("stage").asText(null));
                match.setGroupName(node.path("group").isNull() ? null : node.path("group").asText(null));
                match.setLastUpdated(node.path("lastUpdated").asText());

                Area area = new Area();
                JsonNode areaNode = node.path("area");
                area.setId(areaNode.path("id").asInt());
                area.setName(areaNode.path("name").asText());
                area.setCode(areaNode.path("code").asText());
                area.setFlag(areaNode.path("flag").asText(null));
                match.setArea(area);

                Competition comp = new Competition();
                JsonNode compNode = node.path("competition");
                comp.setId(compNode.path("id").asInt());
                comp.setName(compNode.path("name").asText());
                comp.setCode(compNode.path("code").asText());
                comp.setType(compNode.path("type").asText());
                comp.setEmblem(compNode.path("emblem").asText(null));
                match.setCompetition(comp);

                Season season = new Season();
                JsonNode seasonNode = node.path("season");
                season.setId(seasonNode.path("id").asInt());
                season.setStartDate(seasonNode.path("startDate").asText());
                season.setEndDate(seasonNode.path("endDate").asText());
                season.setCurrentMatchday(seasonNode.path("currentMatchday").asInt());
                season.setWinner(seasonNode.path("winner").isNull() ? null : seasonNode.path("winner").asText(null));
                match.setSeason(season);

                Team home = new Team();
                JsonNode homeNode = node.path("homeTeam");
                home.setId(homeNode.path("id").asInt());
                home.setName(homeNode.path("name").asText());
                home.setShortName(homeNode.path("shortName").asText());
                home.setTla(homeNode.path("tla").asText());
                home.setCrest(homeNode.path("crest").asText(null));
                match.setHomeTeam(home);

                Team away = new Team();
                JsonNode awayNode = node.path("awayTeam");
                away.setId(awayNode.path("id").asInt());
                away.setName(awayNode.path("name").asText());
                away.setShortName(awayNode.path("shortName").asText());
                away.setTla(awayNode.path("tla").asText());
                away.setCrest(awayNode.path("crest").asText(null));
                match.setAwayTeam(away);

                Score score = new Score();
                JsonNode scoreNode = node.path("score");
                score.setWinner(scoreNode.path("winner").isNull() ? null : scoreNode.path("winner").asText(null));
                score.setDuration(scoreNode.path("duration").asText(null));

                FullTime ft = new FullTime();
                JsonNode ftNode = scoreNode.path("fullTime");
                ft.setHome(ftNode.path("home").isNull() ? null : ftNode.path("home").asInt());
                ft.setAway(ftNode.path("away").isNull() ? null : ftNode.path("away").asInt());
                score.setFullTime(ft);

                HalfTime ht = new HalfTime();
                JsonNode htNode = scoreNode.path("halfTime");
                ht.setHome(htNode.path("home").isNull() ? null : htNode.path("home").asInt());
                ht.setAway(htNode.path("away").isNull() ? null : htNode.path("away").asInt());
                score.setHalfTime(ht);
                match.setScore(score);

                Odds odds = new Odds();
                JsonNode oddsNode = node.path("odds");
                odds.setMsg(oddsNode.path("msg").asText(null));
                match.setOdds(odds);

                matchList.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchList;
    }
}
