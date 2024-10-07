package bsn.backend.AUTH;

import bsn.backend.EMAIL.EmailService;
import bsn.backend.EMAIL.EmailTemplateName;
import bsn.backend.REPOSITORIES.RoleRepository;
import bsn.backend.REPOSITORIES.TokenRepository;
import bsn.backend.REPOSITORIES.UserRepository;
import bsn.backend.SECURITY.JwtService;
import bsn.backend.USER.Token;
import bsn.backend.USER.User;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService {

private final UserRepository userRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;
private final TokenRepository tokenRepository;
private final AuthenticationManager authenticationManager;
private final EmailService emailService;
private final JwtService jwtService;
@Value("${spring.application.mailing.frontend.activationUrl}")
private String confirmationUrl;
    public void register(RegistrationRequest registrationRequest) throws MessagingException {
        var userRole = roleRepository.findRoleByName("USER").orElseThrow(()-> new IllegalStateException("User Role is not initiated"));
        var user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .gender(registrationRequest.getGender())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(List.of(userRole))
                .enabled(false)
                .accountLocked(false)
                .build();
        userRepository.save(user);
        System.out.println(generateAndSaveActivationToken(user));


    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        //send email
        emailService.sendEmail(user.getEmail(), user.getName(), EmailTemplateName.ACTIVATE_ACCOUNT,confirmationUrl,newToken,"Account Activation");


    }

    private String generateAndSaveActivationToken(User user) {
        var generatedToken = generateActivationCode(6);
        var token = Token.builder().
                token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "1234567890";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i=0 ; i< length; i++){
            int charIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(charIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

       var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));

        var  claims = new HashMap<String, Object>();
    var user = ((User)auth.getPrincipal());
    claims.put("fullName",user.getName());
    var token = jwtService.generateToken(claims,user);
    log.info(token);
    return
            AuthenticationResponse.builder()
                    .token(token)
                    .build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException{
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Invalid Token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            throw new RuntimeException("Token has been Expired. A new Token Sent to your Email Address");
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(()->new RuntimeException("User Not Found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
