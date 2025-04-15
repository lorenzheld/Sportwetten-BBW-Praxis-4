package ch.bbw.api_mirror.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`match`")
public class Match {

    @Id
    private Long id;

    @Column(name = "utc_date_column")
    private String utcDate;

    private String status;
    private Integer matchday;
    private String stage;

    @Column(name = "`group`")
    private String groupName;

    @Column(name = "last_updated")
    private String lastUpdated;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "area_id")),
            @AttributeOverride(name = "name", column = @Column(name = "area_name")),
            @AttributeOverride(name = "code", column = @Column(name = "area_code")),
            @AttributeOverride(name = "flag", column = @Column(name = "area_flag"))
    })
    private Area area;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "competition_id")),
            @AttributeOverride(name = "name", column = @Column(name = "competition_name")),
            @AttributeOverride(name = "code", column = @Column(name = "competition_code")),
            @AttributeOverride(name = "type", column = @Column(name = "competition_type")),
            @AttributeOverride(name = "emblem", column = @Column(name = "competition_emblem"))
    })
    private Competition competition;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "season_id")),
            @AttributeOverride(name = "startDate", column = @Column(name = "season_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "season_end_date")),
            @AttributeOverride(name = "currentMatchday", column = @Column(name = "season_current_matchday")),
            @AttributeOverride(name = "winner", column = @Column(name = "season_winner"))
    })
    private Season season;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "home_team_id")),
            @AttributeOverride(name = "name", column = @Column(name = "home_team_name")),
            @AttributeOverride(name = "shortName", column = @Column(name = "home_team_short_name")),
            @AttributeOverride(name = "tla", column = @Column(name = "home_team_tla")),
            @AttributeOverride(name = "crest", column = @Column(name = "home_team_crest"))
    })
    private Team homeTeam;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "away_team_id")),
            @AttributeOverride(name = "name", column = @Column(name = "away_team_name")),
            @AttributeOverride(name = "shortName", column = @Column(name = "away_team_short_name")),
            @AttributeOverride(name = "tla", column = @Column(name = "away_team_tla")),
            @AttributeOverride(name = "crest", column = @Column(name = "away_team_crest"))
    })
    private Team awayTeam;

    @Embedded
    private Score score;

    @Embedded
    private Odds odds;
}
