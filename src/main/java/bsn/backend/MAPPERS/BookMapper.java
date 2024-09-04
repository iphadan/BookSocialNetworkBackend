package bsn.backend.MAPPERS;

import bsn.backend.ENTITIES.Book;
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
}
