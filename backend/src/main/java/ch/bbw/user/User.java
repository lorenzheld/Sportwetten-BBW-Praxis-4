package ch.bbw.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String username;
    private String password;
    private int balance;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_bets",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "bet_id", nullable = false)
    private List<UUID> bets = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 100;
    }
}