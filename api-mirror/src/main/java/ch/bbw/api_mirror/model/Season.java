package ch.bbw.api_mirror.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Season {
    private Integer id;
    private String startDate;
    private String endDate;
    private Integer currentMatchday;
    private String winner;
}
