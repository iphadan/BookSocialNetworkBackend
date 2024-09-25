package bsn.backend.SERVICES;

import bsn.backend.CONTROLLERS.FeedBackRequest;
import bsn.backend.CONTROLLERS.FeedbackResponse;
import bsn.backend.CONTROLLERS.PageResponse;
import bsn.backend.ENTITIES.Book;
import bsn.backend.ENTITIES.Feedback;
import bsn.backend.EXCEPTIONS.OperationNotPermittedException;
import bsn.backend.MAPPERS.FeedbackMapper;
import bsn.backend.REPOSITORIES.BookRepository;
import bsn.backend.REPOSITORIES.FeedbackRepository;
import bsn.backend.USER.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    public Integer saveFeedBack(FeedBackRequest request, Authentication connectedUser) {
         Book book= bookRepository.findById(request.bookId()).orElseThrow(()-> new EntityNotFoundException("Entity Not Found"));
          User user = (User) connectedUser.getPrincipal();
         if(book.getOwner().equals(user.getId())){
             throw new OperationNotPermittedException("You can not give feedback for your own Book");

         }
        if(!book.isShareable() || book.isArchived()){
            throw new OperationNotPermittedException("You can not give feedback for archived or not shareable Book");

        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();


  }
//
//    public ResponseEntity<PageResponse<FeedbackResponse>> getAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
//        Pageable pageable = PageRequest.of(page,size);
//        User user =(User) connectedUser.getPrincipal();
//        Page<Feedback> feedbacks =feedbackRepository.findAllByBookId(bookId,pageable);
//
//
//
//    }
}
