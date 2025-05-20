package ch.bbw.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.bbw.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}