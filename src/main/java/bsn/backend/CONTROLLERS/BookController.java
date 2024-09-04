package bsn.backend.CONTROLLERS;

import bsn.backend.RECORDS.BookRequest;
import bsn.backend.SERVICES.BookService;
import bsn.backend.USER.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="books")
@Tag(name = "book")
public class BookController {

    private BookService service;

    @PostMapping
    public ResponseEntity<Integer> save(
            @Valid @RequestBody BookRequest request, Authentication connectedUser){

        return ResponseEntity.ok(service.save(request,connectedUser));

    }
}
