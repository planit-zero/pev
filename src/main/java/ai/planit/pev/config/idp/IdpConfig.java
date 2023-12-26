package ai.planit.pev.config.idp;

import ai.planit.idp.sdk.handler.IdpRequestHandler;
import ai.planit.idp.sdk.model.IdpLoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdpConfig {
    @Value("${idp.url}")
    private String idpUrl;

    @Value("${idp.service-provider}")
    private String serviceProvider;

    @Bean
    public IdpRequestHandler<IdpLoginUser> idpRequestHandler() {
        return new IdpRequestHandler<>(idpUrl, serviceProvider);
    }
}
