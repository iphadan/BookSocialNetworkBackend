package bsn.backend.MAPPERS;

import bsn.backend.CONTROLLERS.BookResponse;
import bsn.backend.CONTROLLERS.BorrowedBookResponse;
import bsn.backend.ENTITIES.Book;
import bsn.backend.ENTITIES.BookTransactionHistory;
import bsn.backend.FILESERVICES.FileUtils;
import bsn.backend.RECORDS.BookRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Service;

@Service


public class BookMapper {
    public Book toBook(BookRequest request){
return Book.builder()
        .id(request.getId())
        .title(request.getTitle())
        .authorName(request.getAuthorName())
        .synopsis(request.getSynopsis())
        .archived(false)
        .shareable(request.shareAble())
        .build();

    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.calculateRate())
                .archived(book.isArchived())
                .bookCover(FileUtils.readFileFromLocation(book.getBookCover()))
                .shareable(book.isShareable())
                .owner(book.getOwner())
                .build();
    }
    public static BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().calculateRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnedApproved())
                .owner(history.getBook().getOwner())
                .build();
    }

}
