package ch.bbw.api_mirror.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Competition {
    private Integer id;
    private String name;
    private String code;
    private String type;
    private String emblem;
}
