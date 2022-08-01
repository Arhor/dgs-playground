package com.github.mburyshynets.dgs.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories("com.github.mburyshynets.dgs.data.repository")
@EnableTransactionManagement
class DatabaseConfig
