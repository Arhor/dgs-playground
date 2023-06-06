package com.github.mburyshynets.dgs.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mburyshynets.dgs.features.extradata.entity.converter.JsonReadingConverter
import com.github.mburyshynets.dgs.features.extradata.entity.converter.JsonWritingConverter
import com.github.mburyshynets.dgs.features.user.entity.converter.SettingsReadingConverter
import com.github.mburyshynets.dgs.features.user.entity.converter.SettingsWritingConverter
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDateTime
import java.util.Optional
import java.util.function.Supplier

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(
    basePackages = [
        "com.github.mburyshynets.dgs.features.extradata.repository",
        "com.github.mburyshynets.dgs.features.forum.repository",
        "com.github.mburyshynets.dgs.features.user.repository",
    ]
)
@EnableJdbcAuditing(modifyOnCreate = false, dateTimeProviderRef = "currentDateTimeProvider")
@EnableTransactionManagement
class ConfigureDatabase(private val objectMapper: ObjectMapper) : AbstractJdbcConfiguration() {

    override fun userConverters() = listOf(
        SettingsReadingConverter,
        SettingsWritingConverter,
        JsonReadingConverter(objectMapper),
        JsonWritingConverter(objectMapper),
    )

    @Bean
    fun currentDateTimeProvider(currentDateTimeSupplier: Supplier<LocalDateTime>) = DateTimeProvider {
        Optional.of(currentDateTimeSupplier.get())
    }

    @Bean
    fun flywayConfigurationCustomizer() = FlywayConfigurationCustomizer {
        it.loggers("slf4j")
    }
}
