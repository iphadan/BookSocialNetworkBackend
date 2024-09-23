package bsn.backend.SERVICES;

import bsn.backend.CONTROLLERS.BookResponse;
import bsn.backend.CONTROLLERS.BorrowedBookResponse;
import bsn.backend.CONTROLLERS.PageResponse;
import bsn.backend.ENTITIES.Book;
import bsn.backend.ENTITIES.BookTransactionHistory;
import bsn.backend.EXCEPTIONS.OperationNotPermittedException;
import bsn.backend.MAPPERS.BookMapper;
import bsn.backend.RECORDS.BookRequest;
import bsn.backend.REPOSITORIES.BookRepository;
import bsn.backend.REPOSITORIES.BookTransactionHistoryRepository;
import bsn.backend.SPECIFICATIONS.BookSpecification;
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
    private final BookTransactionHistoryRepository historyRepository;
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

    public PageResponse<BookResponse> getAllBooksOfOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()),pageable);

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

    public PageResponse<BorrowedBookResponse> getAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<BookTransactionHistory> books = historyRepository.findAllBorrowedBooks(pageable,user.getId());
        List<BorrowedBookResponse> borrowedBookResponses=books.stream().map(BookMapper::toBorrowedBookResponse).toList();
        return new PageResponse<>(
                borrowedBookResponses,
                books.getNumber(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.getSize(),
                books.isFirst(),
                books.isLast()
        );

    }

    public PageResponse<BorrowedBookResponse> getAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<BookTransactionHistory> books = historyRepository.findAllReturnedBooks(pageable,user.getId());
        List<BorrowedBookResponse> borrowedBookResponses=books.stream().map(BookMapper::toBorrowedBookResponse).toList();
        return new PageResponse<>(
                borrowedBookResponses,
                books.getNumber(),
                books.getTotalPages(),
                books.getTotalElements(),
                books.getSize(),
                books.isFirst(),
                books.isLast()
        );

    }

    public Integer updateShareableStatus(Integer bookId,Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new  EntityNotFoundException("Entity Not Found"));
        if(!user.getId().equals(book.getOwner().getId()) ){
            throw new OperationNotPermittedException("You can not update the book");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;

    }

    public Integer updateArchiveStatus(Integer bookId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new  EntityNotFoundException("Entity Not Found"));
        if(!user.getId().equals(book.getOwner().getId()) ){
            throw new OperationNotPermittedException("You can not update other's book archived");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;

    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new  EntityNotFoundException("Entity Not Found"));
if(user.getId().equals(book.getOwner().getId()) )
{
    throw new OperationNotPermittedException("You can not borrow your own book");

}
else if (book.isArchived() || !book.isShareable()){
    throw new OperationNotPermittedException("You can not borrow the book because it is either not shareable or archived");

}
boolean isBorrowed = historyRepository.isBookBorrowedByUser(bookId,user.getId());
if(isBorrowed){
    throw new OperationNotPermittedException("The Requested Book is already borrowed");

}

BookTransactionHistory transactionHistory = BookTransactionHistory.builder()
        .book(book)
        .user(user)
        .isReturned(false)
        .isReturnedApproved(false)
        .build();
return historyRepository.save(transactionHistory).getId();
    }
}
