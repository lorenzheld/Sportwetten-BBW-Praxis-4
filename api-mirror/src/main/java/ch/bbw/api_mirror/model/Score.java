package ch.bbw.api_mirror.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Score {
    private String winner;
    private String duration;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "home", column = @Column(name = "full_time_home")),
            @AttributeOverride(name = "away", column = @Column(name = "full_time_away"))
    })
    private FullTime fullTime;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "home", column = @Column(name = "half_time_home")),
            @AttributeOverride(name = "away", column = @Column(name = "half_time_away"))
    })
    private HalfTime halfTime;
}
