package io.samtech.configuration.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.samtech.exception.LogicException;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static io.samtech.configuration.message.Translator.eval;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Response<T> implements Serializable {
    private static final long serialVersionUID =3807699489176814824L;
    private Metadata metadata;
    private T payload;

    public static Response ok() {
        return Response.builder()
                .metadata(Metadata.successBlock())
                .payload(Map.of("message", eval("app.common.message.success"))).build();
    }

    public static <T> Response ok(T payload) {
        return Response.builder()
                .metadata(Metadata.successBlock())
                .payload(payload).build();
    }

    public static Response failed(LogicException e) {
        return Response.builder()
                .metadata(Metadata.errorBlock(e))
                .payload(ErrorContent.build(e.getMessage()))
                .build();
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final static class ErrorContent implements Serializable {

        private String message;

        private Object details;

        public static ErrorContent build(String message, MethodArgumentNotValidException ex) {
            return new ErrorContent(message, getFailedValidationFields(ex));
        }

        private static Map<String, String> getFailedValidationFields(
                MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }

        public static ErrorContent build(String message) {
            return new ErrorContent(message, null);
        }

        public static ErrorContent build(String message, Object details) {
            return new ErrorContent(message, details);
        }
    }

}
