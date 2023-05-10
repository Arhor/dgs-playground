package com.github.mburyshynets.dgs.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration(proxyBeanMethods = false)
class ConfigureWebServer : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler(
                "/index.html",
                "/favicon.svg",
                "/assets/**",
            )
            .addResourceLocations(
                "classpath:/static/",
                "classpath:/static/assets/",
            )
    }
}

