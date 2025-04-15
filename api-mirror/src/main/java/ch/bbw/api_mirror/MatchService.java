package ch.bbw.api_mirror;

import ch.bbw.api_mirror.MatchRepository;
import ch.bbw.api_mirror.model.Match;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    private final String API_URL = "https://api.football-data.org/v4/competitions/BL1/matches";
    private final String API_KEY = "";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void syncMatches() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", API_KEY);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, null, String.class);
        String jsonResponse = response.getBody();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode matchesNode = rootNode.path("matches");

            List<Match> matches = new ArrayList<>();
            Iterator<JsonNode> elements = matchesNode.elements();
            while (elements.hasNext()) {
                JsonNode matchNode = elements.next();

                Match match = new Match();
                match.setStatus(matchNode.path("status").asText());
                match.setHomeTeam(matchNode.path("homeTeam").path("name").asText());
                match.setAwayTeam(matchNode.path("awayTeam").path("name").asText());
                match.setCompetition(matchNode.path("competition").path("name").asText());
                match.setSeason(matchNode.path("season").path("startDate").asText());
                match.setDate(matchNode.path("utcDate").asText());

                matches.add(match);
            }

            matchRepository.saveAll(matches);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Match> getMatches(String competition, String status) {
        return matchRepository.findByCompetitionAndStatus(competition, status);
    }
}
