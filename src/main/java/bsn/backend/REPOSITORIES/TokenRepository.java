package bsn.backend.REPOSITORIES;

import bsn.backend.USER.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token> getByToken(String token);
}
