package bsn.backend.CONTROLLERS;

import bsn.backend.ENTITIES.BookTransactionHistory;
import bsn.backend.ENTITIES.Feedback;
import bsn.backend.USER.User;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private byte[] bookCover;
    private boolean archived;
    private boolean shareable;
    private User owner;

    private double rate;

    private List<BookTransactionHistory> transactionHistories;
}
