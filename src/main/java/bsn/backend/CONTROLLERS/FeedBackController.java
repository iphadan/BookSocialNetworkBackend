package bsn.backend.CONTROLLERS;

import bsn.backend.SERVICES.FeedbackService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks/")
@RequiredArgsConstructor
@Tag(name="feedbacks")
public class FeedBackController {
    private  FeedbackService service;
    @PostMapping("book/")
    public ResponseEntity<Integer> giveFeedBack(@Valid @RequestBody FeedBackRequest request,
                                                Authentication connectedUser){
        return ResponseEntity.ok(service.saveFeedBack(request,connectedUser));


    }
    @GetMapping("book/{bookId}")
    public ResponseEntity<PageResponse<FeedbackResponse>> getAllFeedbacks(@PathVariable("bookId")Integer bookId,
    @RequestParam(name = "page",defaultValue = "0", required = false) int page,
                                                                          @RequestParam(name = "size",defaultValue = "10",required = false) int size,
                                                                          Authentication connectedUser
    ){
return service.getAllFeedbacksByBook(bookId,page,size,connectedUser);
    }
}
