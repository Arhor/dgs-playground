package com.github.mburyshynets.dgs

import com.github.mburyshynets.dgs.common.CurrentRequestContext
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import java.util.Locale
import java.util.function.Supplier

@RestControllerAdvice
class GlobalExceptionHandler(
    private val currentDateTimeSupplier: Supplier<LocalDateTime>,
) {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleDefault(exception: Throwable): Map<String, Any?> {
        logger.error("Unhandled exception. Consider appropriate exception handler")
        return handleErrorCode(exception)
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(exception: NoHandlerFoundException): Any {
        val requestURL = exception.requestURL

        return when {
            requestURL == "/" -> {
                handleErrorCode(exception)
            }

            requestURL.startsWith("/api/") -> {
                handleErrorCode(exception)
            }

            else -> {
                ModelAndView("forward:/")
            }
        }
    }

    private fun handleErrorCode(exception: Throwable, details: List<String> = emptyList()): Map<String, Any?> {
        logger.error("An exception occurred:", exception)

        return mapOf(
            "requestId" to CurrentRequestContext.getRequestId(),
            "message" to exception.message,
            "timestamp" to currentDateTimeSupplier.get(),
            "details" to details,
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}
