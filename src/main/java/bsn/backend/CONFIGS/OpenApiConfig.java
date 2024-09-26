package bsn.backend.CONFIGS;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Setter;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ephrem",
                        email = "ephadaniel177@gmial.com",
                        url = "https://myportfolio-wq3k.onrender.com/"

                ),
                description = "OpenApi documentation for spring security",
                title = "OpenApi specification - ephrem",
                license = @License(
                        name = "yenetech",
                        url = "https://gymrender.onrender.com/"
                ),
                termsOfService = "free"


        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:3000/api/v1"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://myportfolio-wq3k.onrender.com/"
                )
        },
       security = @SecurityRequirement(
                name = "bearerAuth"
        )

)
// @SecurityRequirement name and @SecurityScheme name should be the same
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
