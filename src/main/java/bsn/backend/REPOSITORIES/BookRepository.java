package bsn.backend.REPOSITORIES;

import bsn.backend.ENTITIES.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("""
            SELECT book 
            FROM Book book 
            WHERE book.archived = false AND
            book.shareAble = false AND
            book.owner.id !=  :userId
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);
@Query("""
            SELECT book 
            FROM Book book 
            WHERE book.archived = false AND
            book.owner.id ==  :userId
        """)
    Page<Book> findAllDisplayableBooksOfOwner(Pageable pageable, Integer userId);
}
