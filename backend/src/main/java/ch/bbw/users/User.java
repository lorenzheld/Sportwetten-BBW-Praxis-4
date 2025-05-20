package ch.bbw.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private int balance;
    private User(long id, String username, String hashCode, int balance) {
        this.id = id;
        this.username = username;
        this.password = hashCode;
        this.balance = balance;
    }
}