package bsn.backend.AUTH;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthenticationRequest {
    @NotEmpty(message = "Email is Mandatory")
    @NotBlank(message = "Email can not be Blank")
    @Email(message = "Email is not Well Formatted")
    private String email;
    @NotEmpty(message = "Password is Mandatory")
    @NotBlank(message = "Password can not be Blank")
    private String password;
}
