package bsn.backend.SERVICES;

import bsn.backend.ENTITIES.Book;
import bsn.backend.MAPPERS.BookMapper;
import bsn.backend.RECORDS.BookRequest;
import bsn.backend.REPOSITORIES.BookRepository;
import bsn.backend.USER.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        return bookRepository.save(book).getId();


    }
}
