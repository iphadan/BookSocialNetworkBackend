package bsn.backend.AUTH;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private  final AuthenticationService authenticationService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) throws MessagingException {
        System.out.println("here");
        authenticationService.register(registrationRequest);
        return ResponseEntity.accepted().build();
    }
    @PostMapping(value = "authenticate",produces = "application/json")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
    @GetMapping("/activate-account")
    public  void confirm(@RequestParam("token") String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }

}
