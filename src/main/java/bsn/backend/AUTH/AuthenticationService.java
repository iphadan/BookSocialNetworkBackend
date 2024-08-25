package bsn.backend.AUTH;

import bsn.backend.EMAIL.EmailService;
import bsn.backend.EMAIL.EmailTemplateName;
import bsn.backend.REPOSITORIES.RoleRepository;
import bsn.backend.REPOSITORIES.TokenRepository;
import bsn.backend.REPOSITORIES.UserRepository;
import bsn.backend.USER.Token;
import bsn.backend.USER.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

private final UserRepository userRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;
private final TokenRepository tokenRepository;
private final EmailService emailService;
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
}
