package io.github.projektalmanac.amoxtli.backend.config;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {

    static class IndexFallbackResourceResolver extends PathResourceResolver {
        @Override
        protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, @NotNull String requestPath,
                                                   @NotNull List<? extends Resource> locations, @NotNull ResourceResolverChain chain) {
            log.info("Resolving resource for path: {}", requestPath);
            Resource resource = super.resolveResourceInternal(request, requestPath, locations, chain);
            if (resource == null)
                resource = super.resolveResourceInternal(request, "index.html", locations, chain);
            return resource;
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .setOrder(Ordered.LOWEST_PRECEDENCE)
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                //first time resolved, that route will always be used from cache
                .resourceChain(true)
                .addResolver(new IndexFallbackResourceResolver());
    }
}