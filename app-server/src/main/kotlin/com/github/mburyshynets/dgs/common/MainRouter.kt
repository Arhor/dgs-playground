package com.github.mburyshynets.dgs.common

import com.github.mburyshynets.dgs.GlobalExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Component
class MainRouter(private val errorHandler: GlobalExceptionHandler) : RouterFunction<ServerResponse> by router({

    GET(pattern = "/api/health") {
        ServerResponse
            .status(HttpStatus.OK)
            .body("UP")
    }

    onError<Throwable> { e, _ ->
        errorHandler.handleDefault(e).let {
            ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(it)
        }
    }
})
