package bsn.backend.EXCEPTION.HANDLERS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private  Integer businessErrorCode;
    private  String businessErrorDescription;
    private  String error;
    private Set<String> listValidationErrors;
    private Map<String,String> errors;
}
