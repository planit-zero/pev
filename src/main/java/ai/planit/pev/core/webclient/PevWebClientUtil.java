package ai.planit.pev.core.webclient;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import com.google.gson.Gson;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PevWebClientUtil {
    public static Mono<? extends Throwable> throwServerError(ClientResponse response, ErrorType errorType) {
        return response.createException()
                .flatMap(error -> {
                    String body = error.getResponseBodyAsString(StandardCharsets.UTF_8);
                    Map<String, String> errorData = new Gson().fromJson(body, HashMap.class);
                    System.out.println(errorData.get("message"));
                    return Mono.error(
                            new BaseException(
                                    errorType,
                                    errorType.getMessage(),
//                                    String.format("%s\r\n%s", errorData.get("message"), errorData.get("stackTrace"))
                                    errorData.get("message")
                            )
                    );
                });
    }
}
