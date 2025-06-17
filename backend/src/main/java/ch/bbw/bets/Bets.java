package ch.bbw.bets;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name="bets")
public class Bets {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    private UUID id;
    long gameId;
    UUID userId;

    public Bets(UUID id, long gameId, UUID userId) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
    }
}