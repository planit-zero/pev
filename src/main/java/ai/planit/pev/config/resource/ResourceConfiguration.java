package ai.planit.pev.config.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/masked_images/**")
                .addResourceLocations("file:///data/masked_images/")
                .setCacheControl(CacheControl.noCache());

        registry.addResourceHandler("/manual/**")
                .addResourceLocations("file:///deview/pev/manual/")
                .setCacheControl(CacheControl.noCache());
    }
}
