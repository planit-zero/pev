package ai.planit.pev.core.webclient;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PevWebClient {
    private final static int TIME_OUT = 100000;
    private final static int RESPONSE_TIME_OUT = Integer.MAX_VALUE;

    public static WebClient getWebClient(String url, ErrorType errorType) {
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(getConnector(errorType))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build()
                )
                .build();
    }

    private static ClientHttpConnector getConnector(ErrorType errorType) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIME_OUT)
                .responseTimeout(Duration.ofMillis(RESPONSE_TIME_OUT))
                .doOnConnected(conn ->
                        conn.addHandlerFirst(new ReadTimeoutHandler(RESPONSE_TIME_OUT, TimeUnit.MILLISECONDS))
                                .addHandlerFirst(new WriteTimeoutHandler(RESPONSE_TIME_OUT, TimeUnit.MILLISECONDS))
                )
                .doOnError(
                        (httpClientRequest, throwable) -> {
                            throw new BaseException(errorType);
                        },
                        (httpClientResponse, throwable) -> {
                            throw new BaseException(errorType);
                        }
                );

        return new ReactorClientHttpConnector(httpClient);
    }
}
