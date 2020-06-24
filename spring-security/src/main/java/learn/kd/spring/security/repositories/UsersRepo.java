package learn.kd.spring.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learn.kd.spring.security.entities.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {

    public Optional<Users> findByUsername(String username);
}
