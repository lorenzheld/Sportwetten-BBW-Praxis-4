package ch.bbw.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameResult {
    private String status;

    @JsonProperty("home_score")
    private int homeScore;

    @JsonProperty("away_score")
    private int awayScore;
}