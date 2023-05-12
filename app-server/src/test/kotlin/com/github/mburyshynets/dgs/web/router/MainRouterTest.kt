package com.github.mburyshynets.dgs.web.router

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.from
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.servlet.function.ServerRequest
import java.net.URI

internal class MainRouterTest {

    private val mainRouter: MainRouter = MainRouter()

    @Test
    fun `should have handler function returning expected status for the given HTTP request method and URI`() {
        // Given
        val requestURI = URI.create("/api/health")
        val httpMethod = HttpMethod.GET
        val httpStatus = HttpStatus.OK

        val serverRequest = createMockServerRequest(httpMethod, requestURI)

        // When
        val handlerFunction = mainRouter.route(serverRequest)

        // Then
        assertThat(handlerFunction)
            .isNotNull
            .isNotEmpty
            .get()
            .returns(httpStatus, from { it.handle(serverRequest).statusCode() })
    }

    private fun createMockServerRequest(httpMethod: HttpMethod, requestURI: URI): ServerRequest {
        val servletRequest = MockHttpServletRequest(httpMethod.name(), requestURI.toString())
        val messageReaders = emptyList<HttpMessageConverter<*>>()

        return ServerRequest.create(servletRequest, messageReaders)
    }
}
