package bsn.backend.EXCEPTION.HANDLERS;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter

public enum BusinessErrorCodes {
    NO_CODE(0,"NO CODE",HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_CURRENT_PASSWORD(300,"CURRENT PASSWORD IS INCORRECT",HttpStatus.BAD_REQUEST),
    PASSWORD_DOES_NOT_MATCH(301,"THE NEW PASSWORD DOES NOT MATCH",HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(303,"USE ACCOUNT IS LOCKED",HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(304,"USER ACCOUNT IS DISABLED",HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(305,"USERNAME / OR PASSWORD IS INCORRECT ",HttpStatus.FORBIDDEN),
    LOCKED_ACCOUNT(302,"USER ACCOUNT IS LOCKED",HttpStatus.FORBIDDEN);


    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
