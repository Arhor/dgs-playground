package com.github.mburyshynets.dgs.aop

import com.github.mburyshynets.dgs.aop.MethodExecutionLoggingAspect.Companion.PROP_NAME
import com.github.mburyshynets.dgs.aop.MethodExecutionLoggingAspect.Companion.PROP_VALUE
import jakarta.annotation.PostConstruct
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Aspect
@Component
@ConditionalOnProperty(name = [PROP_NAME], havingValue = PROP_VALUE)
class MethodExecutionLoggingAspect {

    @PostConstruct
    fun init() {
        if (LoggerFactory.getLogger(ROOT).isDebugEnabled) {
            logger.info("Method execution aspect is configured to use")
        } else {
            logger.warn(
                "Property '{}' has value '{}' but DEBUG level is disabled for the '{}' logger",
                PROP_NAME,
                PROP_VALUE,
                ROOT,
            )
        }
    }

    @Around("webLayer() || serviceLayer() || dataLayer()")
    fun logMethodExecution(joinPoint: ProceedingJoinPoint): Any? {
        val method = joinPoint.signature as MethodSignature
        val logger = LoggerFactory.getLogger(method.declaringTypeName)

        return if (logger.isDebugEnabled) {
            val methodName = method.name
            val methodArgs = joinPoint.args.contentToString()

            logger.debug(FUN_EXECUTION_START, methodName, methodArgs)

            with(Timer()) {
                when (val result = joinPoint.proceed()) {
                    is CompletableFuture<*> -> result.thenApply {
                        logger.debug(FUN_EXECUTION_CLOSE, methodName, method.format(it), elapsedTime)
                        it
                    }

                    else -> result.let {
                        logger.debug(FUN_EXECUTION_CLOSE, methodName, method.format(it), elapsedTime)
                        it
                    }
                }
            }
        } else {
            joinPoint.proceed()
        }
    }

    @Pointcut("execution(* $ROOT.web..*(..)) && (within($REST_CONTROLLER *) || within($DGS_COMPONENT *) || within($DGS_DATA_LOADER *))")
    private fun webLayer() {
        /* no-op */
    }

    @Pointcut("execution(* $ROOT.service..*(..))")
    private fun serviceLayer() {
        /* no-op */
    }

    @Pointcut("execution(* $ROOT.data..*(..))")
    private fun dataLayer() {
        /* no-op */
    }

    @JvmInline
    value class Timer(private val start: Long = System.currentTimeMillis()) {

        val elapsedTime: Duration
            get() = (System.currentTimeMillis() - start).toDuration(DurationUnit.MILLISECONDS)
    }

    companion object {
        private const val FUN_EXECUTION_START = "Method: {}() >>> args: {}"
        private const val FUN_EXECUTION_CLOSE = "Method: {}() <<< exit: {}, time: {}"

        private const val ROOT = "com.github.mburyshynets.dgs"
        private const val VOID = "void"

        private const val DGS_COMPONENT = "@com.netflix.graphql.dgs.DgsComponent"
        private const val DGS_DATA_LOADER = "@com.netflix.graphql.dgs.DgsDataLoader"
        private const val REST_CONTROLLER = "@org.springframework.web.bind.annotation.RestController"

        internal const val PROP_NAME = "log-method-execution"
        internal const val PROP_VALUE = "true"

        private val logger = LoggerFactory.getLogger(MethodExecutionLoggingAspect::class.java)

        @Suppress("NOTHING_TO_INLINE")
        private inline fun MethodSignature.format(result: Any?): String = when (returnType.name) {
            VOID -> VOID
            else -> result.toString()
        }
    }
}

