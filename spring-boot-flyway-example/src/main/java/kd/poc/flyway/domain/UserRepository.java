package kd.poc.flyway.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
}
