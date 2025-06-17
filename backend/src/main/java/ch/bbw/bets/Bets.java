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
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private Long gameId;
    private UUID userId;
    private int betAmount;
    private boolean homeBet;

    public Bets(Long gameId, UUID userId, int betAmount, boolean homeBet) {
        this.gameId = gameId;
        this.userId = userId;
        this.betAmount = betAmount;
        this.homeBet = homeBet;
    }
}