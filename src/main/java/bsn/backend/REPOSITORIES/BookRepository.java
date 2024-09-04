package bsn.backend.REPOSITORIES;

import bsn.backend.ENTITIES.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
