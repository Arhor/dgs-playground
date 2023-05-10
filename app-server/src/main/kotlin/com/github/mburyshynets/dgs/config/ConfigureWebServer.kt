package com.github.mburyshynets.dgs.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate.forAnnotation
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration(proxyBeanMethods = false)
class ConfigureWebServer : WebMvcConfigurer {


    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix(API_PATH_PREFIX, forAnnotation(RestController::class.java))
        logger.info("Global API path prefix: {}", API_PATH_PREFIX)
    }

    companion object {
        const val API_PATH_PREFIX = "/api"
        private val logger = LoggerFactory.getLogger(ConfigureWebServer::class.java)
    }
}

