package io.samtech.configuration.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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

}
