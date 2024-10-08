package bsn.backend.ENTITIES;

import bsn.backend.USER.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Book extends BaseEntity{
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> transactionHistories;

    @Transient
    public Double calculateRate(){
        if (this.feedbacks.isEmpty()){
            return 0.0;
        }
       var rate =  this.feedbacks.stream().mapToDouble(Feedback::getNote).average().orElse(0.0);
        return Math.round(rate * 10.0) / 10.0;
    }

}
