package com.github.mburyshynets.dgs.config

import com.github.mburyshynets.dgs.data.SettingsReadingConverter
import com.github.mburyshynets.dgs.data.SettingsWritingConverter
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories("com.github.mburyshynets.dgs.data.repository")
@EnableTransactionManagement
class DatabaseConfig : AbstractJdbcConfiguration() {

    override fun userConverters() = listOf(
        SettingsReadingConverter,
        SettingsWritingConverter,
    )
}
