package com.example.alom1.finiensserver.data.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

abstract class RestTemplateClient(
    private val key: String? = null,
) {
    val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getHeader(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        if (key != null) {
            headers.set("Authorization", "KakaoAK $key")
        }
        return headers
    }

    inline fun <reified R> get(url: String): R {

        val httpEntity = HttpEntity<Any>(this.getHeader())
        val res: ResponseEntity<JSONArray>  = restTemplate.exchange(
            url,
            HttpMethod.GET,
            httpEntity,
            JSONArray::class.java
        )

        val body = res.body

        return objectMapper.readValue<R>(res.body.toString(), R::class.java)
    }

}