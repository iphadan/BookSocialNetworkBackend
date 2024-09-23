package bsn.backend.CONTROLLERS;

import bsn.backend.RECORDS.BookRequest;
import bsn.backend.SERVICES.BookService;
import bsn.backend.USER.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable("bookId") Integer bookId){
return ResponseEntity.ok(service.getBookById(bookId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getAllBooks(
            @RequestParam(name = "page",defaultValue = "0", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser){

        return ResponseEntity.ok(service.getAllBooks(page,size,connectedUser));
    }
    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> getAllBooksOfOwner(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser){

        return ResponseEntity.ok(service.getAllBooksOfOwner(page,size,connectedUser));
    }
    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> getAllBorrowedBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser){

        return ResponseEntity.ok(service.getAllBorrowedBooks(page,size,connectedUser));
    }
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> getAllReturnedBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser){

        return ResponseEntity.ok(service.getAllReturnedBooks(page,size,connectedUser));
    }
    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<Integer> updateShareableStatus(@PathVariable("bookId") Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok((service.updateShareableStatus(bookId,connectedUser)));
    }
    @PatchMapping("/archive/{bookId}")
    public ResponseEntity<Integer> updateArchiveStatus(@PathVariable("bookId") Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok((service.updateArchiveStatus(bookId,connectedUser)));
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Integer> borrowBook(@PathVariable("bookId") Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok((service.borrowBook(bookId,connectedUser)));
    }
}

