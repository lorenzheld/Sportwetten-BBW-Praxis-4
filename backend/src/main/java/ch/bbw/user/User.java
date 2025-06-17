package ch.bbw.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    private UUID id;
    private String username;
    private String password;
    private int balance;
    private List<UUID> bets;
    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}