package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.config.ConfigureGraphQLScalars
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.ContextConfiguration
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(
    classes = [
        ConfigureGraphQLScalars::class,
        DgsAutoConfiguration::class,
        DgsExtendedScalarsAutoConfiguration::class,
    ]
)
@ContextConfiguration
annotation class DgsTest(
    @get:AliasFor(annotation = ContextConfiguration::class, attribute = "classes")
    val classes: Array<KClass<*>>
)
