package bsn.backend.CONTROLLERS;

import bsn.backend.ENTITIES.BookTransactionHistory;
import bsn.backend.USER.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private boolean returnApproved;
    private boolean returned;
    private User owner;
    private double rate;

}
