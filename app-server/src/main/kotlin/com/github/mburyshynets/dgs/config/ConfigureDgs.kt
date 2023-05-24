package com.github.mburyshynets.dgs.config

import com.netflix.graphql.dgs.internal.method.ArgumentResolver
import com.netflix.graphql.dgs.mvc.internal.method.HandlerMethodArgumentResolverAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.web.bind.support.WebDataBinderFactory

@Configuration(proxyBeanMethods = false)
class ConfigureDgs {

    @Bean
    fun authenticationPrincipalResolver(dgsDataBinderFactory: WebDataBinderFactory): ArgumentResolver =
        HandlerMethodArgumentResolverAdapter(
            delegate = AuthenticationPrincipalArgumentResolver(),
            webDataBinderFactory = dgsDataBinderFactory,
        )
}
