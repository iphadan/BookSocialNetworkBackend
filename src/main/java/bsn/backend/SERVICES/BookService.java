package bsn.backend.SERVICES;

import bsn.backend.CONTROLLERS.BookResponse;
import bsn.backend.CONTROLLERS.PageResponse;
import bsn.backend.ENTITIES.Book;
import bsn.backend.MAPPERS.BookMapper;
import bsn.backend.RECORDS.BookRequest;
import bsn.backend.REPOSITORIES.BookRepository;
import bsn.backend.USER.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BookResponse getBookById(Integer bookId) {
        return
                bookRepository.findById(bookId).map(bookMapper::toBookResponse).orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
    }

    public PageResponse<BookResponse> getAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable,user.getId());

        List<BookResponse> bookResponses = books.stream().map(bookMapper::toBookResponse).toList();
        return  new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.getSize(),
                books.isFirst(),
                books.isLast()
        );


    }

    public PageResponse<BookResponse> getAllBOoksOfOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
Page<Book> books = bookRepository.findAllDisplayableBooksOfOwner(pageable, user.getId());

List<BookResponse> bookResponses = books.stream().map(bookMapper::toBookResponse).toList();

return new PageResponse<>(
        bookResponses,
        books.getNumber(),
        books.getTotalPages(),
        books.getTotalElements(),
        books.getSize(),
        books.isFirst(),
        books.isLast()
);

    }
}
