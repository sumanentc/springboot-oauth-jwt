package ai.auth.jwt.repository;

import ai.auth.jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by suman.das on 11/28/18.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
