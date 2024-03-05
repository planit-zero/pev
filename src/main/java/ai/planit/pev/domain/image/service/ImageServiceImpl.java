package ai.planit.pev.domain.image.service;

import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.core.webclient.PevWebClientUtil;
import ai.planit.pev.domain.image.dto.ImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public ImageDTO getMaskedImage(ImageDTO imageDTO) {
        String url = "http://172.26.33.23:28092";
        String uri = "/api/emr/img-single";

        WebClient webClient = PevWebClient.getWebClient(url, ErrorType.RID_CONNECTION_TIMEOUT);

        return webClient.post()
                .uri(uri)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(imageDTO))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> PevWebClientUtil.throwServerError(response, ErrorType.ANN_PROCESS_FAILED))
                .onStatus(HttpStatus::is4xxClientError, response -> PevWebClientUtil.throwServerError(response, ErrorType.ANN_PROCESS_FAILED))
                .bodyToMono(ImageDTO.class)
                .block();
    }
}
