package com.github.mburyshynets.dgs.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration(proxyBeanMethods = false)
class ConfigureCaching
