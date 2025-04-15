package ch.bbw.api_mirror.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class FullTime {
    private Integer home;
    private Integer away;
}
