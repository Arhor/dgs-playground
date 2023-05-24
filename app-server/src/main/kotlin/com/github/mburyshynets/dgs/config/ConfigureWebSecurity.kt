package com.github.mburyshynets.dgs.config

import com.github.mburyshynets.dgs.config.security.CustomFailureHandler
import com.github.mburyshynets.dgs.config.security.CustomSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class ConfigureWebSecurity {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            .logout {
                it.logoutUrl("/api/sign-out")
                it.logoutSuccessHandler(SimpleUrlLogoutSuccessHandler().apply { setUseReferer(true) })
                it.logoutSuccessUrl("/")
            }
            .formLogin {
                it.loginPage("/sign-in")
                it.loginProcessingUrl("/api/sign-in")
                it.successHandler(CustomSuccessHandler())
                it.failureHandler(CustomFailureHandler())
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
