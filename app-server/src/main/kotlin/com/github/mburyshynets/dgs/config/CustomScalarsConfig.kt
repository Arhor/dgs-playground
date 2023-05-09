package com.github.mburyshynets.dgs.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@ComponentScan(
    basePackages = [
        "com.github.mburyshynets.dgs.graphql.scalar"
    ]
)
class CustomScalarsConfig
