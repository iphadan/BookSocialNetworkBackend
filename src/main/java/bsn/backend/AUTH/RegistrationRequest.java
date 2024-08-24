package bsn.backend.AUTH;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Firstname is Mandatory")
    @NotBlank(message = "Firstname can not be Blank")
    private String firstName;
    @NotEmpty(message = "Lastname is Mandatory")
    @NotBlank(message = "Lastname can not be Blank")
    private String lastName;
    @NotEmpty(message = "Email is Mandatory")
    @NotBlank(message = "Email can not be Blank")
    @Email(message = "Email is not Well Formatted")
    private String email;
    @NotEmpty(message = "Password is Mandatory")
    @NotBlank(message = "Password can not be Blank")
    @Size(max = 8 ,message = "Password can not be less than 8 Characters")
    private String password;
    @NotEmpty(message = "Firstname is Mandatory")
    @NotBlank(message = "Firstname can not be Blank")
    private String gender;
}
