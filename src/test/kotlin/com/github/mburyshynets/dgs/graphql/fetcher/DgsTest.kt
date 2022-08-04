package com.github.mburyshynets.dgs.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.scalar.SettingsScalar
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(
    classes = [
        DgsAutoConfiguration::class,
        DgsExtendedScalarsAutoConfiguration::class,
        SettingsScalar::class,
    ]
)
annotation class DgsTest
