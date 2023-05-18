package com.github.mburyshynets.dgs.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@Aspect
@Component
@ConditionalOnProperty(name = ["log-method-execution"], havingValue = "true")
class MethodExecutionLoggingAspect {

    @OptIn(ExperimentalTime::class)
    @Around("webLayer() || serviceLayer() || dataLayer()")
    fun logMethodExecution(joinPoint: ProceedingJoinPoint): Any? {
        val logger = joinPoint.componentLogger()
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.name
        val methodArgs = joinPoint.args.contentToString()

        logger.debug("Method: {}() >>> args: {}", methodName, methodArgs)
        val (result, duration) = measureTimedValue { joinPoint.proceed() }
        logger.debug("Method: {}() <<< exit: {}, time: {}", methodName, signature.format(result), duration)

        return result
    }

    @Pointcut("execution(* $ROOT.web..*(..))" +
        " && (within($REST_CONTROLLER *) || within($DGS_COMPONENT *) || within($DGS_DATA_LOADER *))")
    private fun webLayer() { /* no-op */ }

    @Pointcut("execution(* $ROOT.service..*(..))")
    private fun serviceLayer() { /* no-op */ }

    @Pointcut("execution(* $ROOT.data..*(..))")
    private fun dataLayer() { /* no-op */ }

    companion object {
        private const val ROOT = "com.github.mburyshynets.dgs"
        private const val VOID = "void"

        private const val REST_CONTROLLER = "@org.springframework.web.bind.annotation.RestController"
        private const val DGS_COMPONENT = "@com.netflix.graphql.dgs.DgsComponent"
        private const val DGS_DATA_LOADER = "@com.netflix.graphql.dgs.DgsDataLoader"

        private fun JoinPoint.componentLogger(): Logger {
            return LoggerFactory.getLogger(signature.declaringTypeName)
        }

        private fun MethodSignature.format(result: Any?): Any? {
            return if (returnType.name == VOID) {
                VOID
            } else {
                result
            }
        }
    }
}

