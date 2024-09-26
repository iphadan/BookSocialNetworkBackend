package bsn.backend.MAPPERS;

import bsn.backend.CONTROLLERS.FeedBackRequest;
import bsn.backend.CONTROLLERS.FeedbackResponse;
import bsn.backend.ENTITIES.Book;
import bsn.backend.ENTITIES.Feedback;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedBackRequest request) {
        return  Feedback.builder()
                .book(Book.builder()
                        .id(request.bookId())
                        .archived(false)
                        .shareable(false)
                .build())
                .note(request.note())
                .comment(request.comment())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback f, Integer userId) {
        return  FeedbackResponse.builder()
                .note(f.getNote())
                .comment(f.getComment())
                .ownFeedback(Objects.equals(f.getCreatedBy(),userId))
                .build();
    }
}
