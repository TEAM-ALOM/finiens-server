package com.example.alom1.finiensserver.config

import com.example.alom1.finiensserver.logger
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.client.RestTemplate
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.stream.Collectors

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(5))
            .setReadTimeout(Duration.ofSeconds(5))
            .additionalInterceptors(clientHttpRequestInterceptor(), LoggingInterceptor)
            .requestFactory {s -> BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()) }
            .build()
    }

    private fun clientHttpRequestInterceptor(): ClientHttpRequestInterceptor {
        return ClientHttpRequestInterceptor {
                request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution ->
            val retryTemplate: RetryTemplate = RetryTemplate()
            retryTemplate.setRetryPolicy(SimpleRetryPolicy(3))
            try {
                retryTemplate.execute<ClientHttpResponse, Throwable> { execution.execute(request, body) }
            } catch (t: Throwable) {
                throw RuntimeException()
            }
        }
    }

    companion object LoggingInterceptor: ClientHttpRequestInterceptor {
        private val log = logger()

        override fun intercept(
            request: HttpRequest,
            body: ByteArray,
            execution: ClientHttpRequestExecution
        ): ClientHttpResponse {
            val sessionNumber: String = makeSessionNumber()
            printRequest(sessionNumber=sessionNumber, req=request, body=body)
            val response: ClientHttpResponse = execution.execute(request, body)
            printResponse(sessionNumber = sessionNumber, res = response)
            return response
        }
        private fun makeSessionNumber(): String = (Math.random() * 1000000).toString()

        private fun printRequest(sessionNumber: String, req: HttpRequest, body: ByteArray) {
            log.info("[{}] URI: {}, Method: {}, Headers:{}, Body:{} ",
                sessionNumber, req.uri, req.method, req.headers, String(body, StandardCharsets.UTF_8)
                )
        }
        private fun printResponse(sessionNumber: String, res: ClientHttpResponse) {
            val body: String = BufferedReader(InputStreamReader(res.getBody(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"))

            log.info("[{}] Status: {}, Headers:{}, Body:{} ",
                sessionNumber, res.statusCode, res.headers, body)
        }
    }
}