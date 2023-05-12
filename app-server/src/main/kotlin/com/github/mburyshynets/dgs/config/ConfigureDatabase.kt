package com.github.mburyshynets.dgs.config

import com.github.mburyshynets.dgs.data.SettingsReadingConverter
import com.github.mburyshynets.dgs.data.SettingsWritingConverter
import com.github.mburyshynets.dgs.data.model.ExtraDataEntity
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID
import java.util.function.Supplier


@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories("com.github.mburyshynets.dgs.data.repository")
@EnableJdbcAuditing(modifyOnCreate = false, dateTimeProviderRef = "currentDateTimeProvider")
@EnableTransactionManagement
class ConfigureDatabase : AbstractJdbcConfiguration() {

    override fun userConverters() = listOf(
        SettingsReadingConverter,
        SettingsWritingConverter,
    )

    @Bean
    fun currentDateTimeProvider(currentDateTimeSupplier: Supplier<LocalDateTime>) = DateTimeProvider {
        Optional.of(currentDateTimeSupplier.get())
    }

    @Bean
    fun flywayConfigurationCustomizer() = FlywayConfigurationCustomizer {
        it.loggers("slf4j")
    }

    @Bean
    fun beforeDataExtensionEntityConvertCallback() = BeforeConvertCallback<ExtraDataEntity> {
        if (it.id == null) {
            it.copy(id = UUID.randomUUID())
        } else {
            it
        }
    }
}
